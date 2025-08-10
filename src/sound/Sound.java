package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

/**
 *
 * @author HP
 */
public class Sound {
    
    private Clip clip;
    private final URL soundURL;
    private float volume;
    private long clipPosition = 0; // Track where we paused
    
    public Sound(String url, float volume) {
        soundURL = getClass().getResource(url);
        this.volume = volume;
        setFile();
    }
    
    public void setFile(){
        if(clip != null) return;

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        }catch(Exception e){
            System.out.println("Set File Error: " + soundURL);
            e.printStackTrace();
        }
    }
    
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }
    
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(clipPosition);
            clip.start();
        }
    }
}
