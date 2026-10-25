import event.Wave;
import game_2d.GamePanel;
import gamestate.PlayState;
import gamestate.TitleDifficultyState;
import sound.Sound;
import sound.SoundManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import javax.sound.sampled.Clip;

/** Run against the built game JAR; no window or audio playback is required. */
public class WaveDifficultyTest {
    public static void main(String[] args) throws Exception {
        GamePanel panel = createSilentPanel();
        checkSelection(panel, GamePanel.EASY, new int[] {10, 12, 15});
        checkSelection(panel, GamePanel.MEDIUM, new int[] {10, 15, 20});
        checkSelection(panel, GamePanel.HARD, new int[] {10, 16, 23});
        // Repeat selections to catch difficulty leaking between sessions.
        checkSelection(panel, GamePanel.MEDIUM, new int[] {10, 15, 20});
        checkSelection(panel, GamePanel.EASY, new int[] {10, 12, 15});
        System.out.println("PASS: wave growth follows all difficulty selections, including repeat sessions.");
    }

    private static void checkSelection(GamePanel panel, int difficulty, int[] expectedCounts)
            throws Exception {
        // Reproduce the startup ordering: Wave exists before the difficulty menu is used.
        panel.difficulty = GamePanel.EASY;
        Wave wave = new Wave(panel);
        TitleDifficultyState selection = new TitleDifficultyState(panel.getGameStateManager());
        panel.getGameStateManager().setState(selection);
        if (difficulty == GamePanel.EASY) selection.moveUp();
        if (difficulty == GamePanel.HARD) selection.moveDown();
        selection.changeState();
        require(panel.difficulty == difficulty, "The menu did not select the requested difficulty");
        require(panel.getGameStateManager().getCurrentState() instanceof PlayState,
                "Difficulty selection did not enter gameplay");

        for (int index = 0; index < expectedCounts.length; index++) {
            // Simulate clearing the prior wave and completing the between-wave delay.
            // No enemy/item spawning is needed to inspect the initial wave budget.
            field(Wave.class, "enemySpawn").setInt(wave, field(Wave.class, "enemyAmount").getInt(wave));
            field(Wave.class, "waveTick").setInt(wave, 120);
            wave.update();
            int actual = field(Wave.class, "enemyAmount").getInt(wave);
            require(wave.getLevel() == index + 1, "The next wave did not start");
            require(actual == expectedCounts[index], "Difficulty " + difficulty + ", wave "
                    + (index + 1) + ": expected initial budget " + expectedCounts[index] + ", got " + actual);
        }
    }

    private static GamePanel createSilentPanel() throws Exception {
        // Desktop initialization may report unavailable audio devices on headless hosts.
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        ByteArrayOutputStream initializationLog = new ByteArrayOutputStream();
        GamePanel panel;
        try (PrintStream capture = new PrintStream(initializationLog)) {
            System.setOut(capture);
            System.setErr(capture);
            try {
                panel = new GamePanel();
            } catch (RuntimeException | Error failure) {
                originalErr.print(initializationLog);
                throw failure;
            } finally {
                System.setOut(originalOut);
                System.setErr(originalErr);
            }
        }
        // Close loaded clips so menu transitions cannot play sound or retain audio threads.
        Sound[] sounds = (Sound[]) field(SoundManager.class, "sounds").get(panel.getSoundM());
        Field clipField = field(Sound.class, "clip");
        for (Sound sound : sounds) {
            Clip clip = (Clip) clipField.get(sound);
            if (clip != null) clip.close();
            clipField.set(sound, null);
        }
        return panel;
    }

    private static Field field(Class<?> owner, String name) throws NoSuchFieldException {
        Field result = owner.getDeclaredField(name);
        result.setAccessible(true);
        return result;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
