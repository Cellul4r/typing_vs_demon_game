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
    private final int browserSoundId;
    private float volume;
    private long clipPosition = 0; // Track where we paused
    
    public Sound(String url, float volume) {
        soundURL = getClass().getResource(url);
        this.volume = volume;
        browserSoundId = Boolean.getBoolean("typingvsdemon.browser") ? browserCreate(url, volume) : -1;
        setFile();
    }
    
    public void setFile(){
        if(browserSoundId >= 0 || clip != null) return;

        Clip loadedClip = null;
        try(AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL)){
            loadedClip = AudioSystem.getClip();
            loadedClip.open(ais);
            if (loadedClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) loadedClip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(Math.max(volumeControl.getMinimum(), Math.min(volumeControl.getMaximum(), volume)));
            }
            clip = loadedClip;
        }catch(Exception e){
            if (loadedClip != null) loadedClip.close();
            clip = null;
            System.out.println("Set File Error: " + soundURL);
            e.printStackTrace();
        }
    }
    
    public void play(){
        if (browserSoundId >= 0) { browserPlay(browserSoundId, false, true); return; }
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void loop(){
        if (browserSoundId >= 0) { browserPlay(browserSoundId, true, false); return; }
        if (clip == null) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        if (browserSoundId >= 0) { browserStop(browserSoundId); return; }
        if (clip != null) clip.stop();
    }

    public void pause() {
        if (browserSoundId >= 0) { browserPause(browserSoundId); return; }
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }
    
    public void resume() {
        if (browserSoundId >= 0) { browserResume(browserSoundId); return; }
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(clipPosition);
            clip.start();
        }
    }

    // Implemented by the web launcher through CheerpJ's native-method bridge.
    private static native int browserCreate(String resource, float volume);
    private static native void browserPlay(int id, boolean loop, boolean restart);
    private static native void browserStop(int id);
    private static native void browserPause(int id);
    private static native void browserResume(int id);
}
