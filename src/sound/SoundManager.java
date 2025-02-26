package sound;

/**
 *
 * @author cellul4r
 */
public class SoundManager {
    
    Sound sound[] = new Sound[5];
    public static final int TITLE_MUSIC = 2;
    public static final int PLAY_MUSIC = 3;
    public static final int ENEMY_SOUND = 4;
    
    public SoundManager(){
        //Wav file only
        sound[0] = new Sound("/resource/sound_res/Start Menu.wav", 0.0f);
        sound[1] = new Sound("/resource/sound_res/Wilhelm Scream.wav", -10.0f);
        sound[2] = new Sound("/resource/sound_res/main_menu.wav", -10.0f);
        sound[3] = new Sound("/resource/sound_res/play_game.wav", -15.0f);
        sound[4] = new Sound("/resource/sound_res/enemy_sound.wav", -10.0f);
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
