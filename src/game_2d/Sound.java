/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

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
    
    Clip clip;
    URL soundURL[] = new URL[5];
    
    public Sound(){
        
        //Wav file only
        soundURL[0] = getClass().getResource("/sound_res/Start Menu.wav");
        soundURL[1] = getClass().getResource("/sound_res/Wilhelm Scream.wav");
    }
    
    public void setFile(int i){
        try{
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            volumeControl.setValue(-10.0f);
            
        }catch(Exception e){
            
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
