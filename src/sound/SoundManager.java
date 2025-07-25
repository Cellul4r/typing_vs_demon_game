package sound;

/**
 *
 * @author cellul4r
 */
public class SoundManager {
    
    Sound[] sounds = new Sound[10];
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
        sounds[0] = new Sound("/resource/sound_res/main_menu.wav", -25.0f);
        sounds[1] = new Sound("/resource/sound_res/play_game.wav", -30.0f);
        sounds[2] = new Sound("/resource/sound_res/enemy_dead.wav", -25.0f);
        sounds[3] = new Sound("/resource/sound_res/gun_sound.wav", -10.0f);
        sounds[4] = new Sound("/resource/sound_res/enemy_sound.wav", -35.0f);
        sounds[5] = new Sound("/resource/sound_res/wrong_typed.wav", 6.0f);
        sounds[6] = new Sound("/resource/sound_res/player_lose_sound.wav", -20.0f);
        sounds[7] = new Sound("/resource/sound_res/healing_sound.wav", -22.0f);
        sounds[8] = new Sound("/resource/sound_res/freeze_sound.wav", -22.0f);
        sounds[9] = new Sound("/resource/sound_res/bomb_sound.wav", -28.0f);
    }   
    
    public void play(int i) {
        sounds[i].play();
    }

    public void loop(int i) {
        sounds[i].loop();
    }

    public void stop(int i) {
        sounds[i].stop();
    }
    
    public void stopAllSounds() {
        for (Sound value : sounds) {
            value.stop();
        }
    }
}
