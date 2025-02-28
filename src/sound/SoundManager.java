package sound;

/**
 *
 * @author cellul4r
 */
public class SoundManager {
    
    Sound sound[] = new Sound[6];
    public static final int TITLE_MUSIC = 0;
    public static final int PLAY_MUSIC = 1;
    public static final int ENEMY_DEAD = 2;
    public static final int GUN_SOUND = 3;
    public static final int ENEMY_SOUND = 4;
    public static final int WRONG_TYPED = 5;
    
    public SoundManager(){
        //Wav file only
        sound[0] = new Sound("/resource/sound_res/main_menu.wav", -15.0f);
        sound[1] = new Sound("/resource/sound_res/play_game.wav", -15.0f);
        sound[2] = new Sound("/resource/sound_res/enemy_dead.wav", 0.0f);
        sound[3] = new Sound("/resource/sound_res/gun_sound.wav", 0.0f);
        sound[4] = new Sound("/resource/sound_res/enemy_sound.wav", -15.0f);
        sound[5] = new Sound("/resource/sound_res/wrong_typed.wav", 0.0f);
    }
    
    public void playSoundEffect(int i) {
        sound[i].play();
    }
    
    public void stopSoundEffect(int i) {
        sound[i].stopSoundEffect();
    }
    
    public void stopMusic(int i) {
        sound[i].stopMusic();
    }

    public void playMusic(int i) {
        sound[i].play();
        sound[i].loop();
    }
}
