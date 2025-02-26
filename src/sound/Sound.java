package sound;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author HP
 */
public class Sound {
    
    private Clip clip;
    private final URL soundURL;
    private float volume;
    
    public Sound(String url, float volume) {
        soundURL = getClass().getResource(url);
        this.volume = volume;
    }
    
    public void setFile(){
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
        if(clip != null && clip.isRunning()) return;
        setFile();
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stopSoundEffect(){
        if(clip == null) return;
        clip.stop();
    }
    
    public void stopMusic() {
        if(clip == null) return;
        clip.stop();
        clip.close();
    }
}
