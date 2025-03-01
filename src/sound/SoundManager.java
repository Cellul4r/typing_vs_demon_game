package sound;

/**
 *
 * @author cellul4r
 */
public class SoundManager {
    
    Sound sound[] = new Sound[10];
    public static final int TITLE_MUSIC = 0;
    public static final int PLAY_MUSIC = 1;
    public static final int ENEMY_DEAD = 2;
    public static final int GUN_SOUND = 3;
    public static final int ENEMY_SOUND = 4;
    public static final int WRONG_TYPED = 5;
    public static final int PLAYER_LOSE = 6;
    public static final int HEALING_SOUND = 7;
    public static final int FREEZE_SOUND = 8;
    public static final int BOMB_SOUND = 9;
    
    public SoundManager(){
        //Wav file only
        sound[0] = new Sound("/resource/sound_res/main_menu.wav", -18.0f);
        sound[1] = new Sound("/resource/sound_res/play_game.wav", -20.0f);
        sound[2] = new Sound("/resource/sound_res/enemy_dead.wav", -15.0f);
        sound[3] = new Sound("/resource/sound_res/gun_sound.wav", 3.0f);
        sound[4] = new Sound("/resource/sound_res/enemy_sound.wav", -18.0f);
        sound[5] = new Sound("/resource/sound_res/wrong_typed.wav", 6.0f);
        sound[6] = new Sound("/resource/sound_res/player_lose_sound.wav", -10.0f);
        sound[7] = new Sound("/resource/sound_res/healing_sound.wav", -10.0f);
        sound[8] = new Sound("/resource/sound_res/freeze_sound.wav", -8.0f);
        sound[9] = new Sound("/resource/sound_res/bomb_sound.wav", -15.0f);
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
