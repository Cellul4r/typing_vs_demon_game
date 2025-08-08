/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

import sound.SoundManager;

/**
 *
 * @author HP
 */
public class PlayState implements GameState {
    private GameStateManager gsm;
    private int commandNum = 0;
    private boolean resumeMusic;

    public PlayState(GameStateManager gsm, boolean resumeMusic) {
        this.gsm = gsm;
        this.resumeMusic = resumeMusic;
    }

    @Override
    public void changeState() {
        gsm.setState(new PauseState(gsm));
        gsm.getGamePanel().getPlayer().resetFirstFrame();
    }

    @Override
    public GameState getGameState() {
        return this;
    }

    @Override
    public void moveUp() {
    }

    @Override
    public void moveDown() {
    }

    @Override
    public int getCommandNum() {
        return this.commandNum;
    }
    
    @Override
    public void doSound(SoundManager soundM) {
        soundM.stop(SoundManager.TITLE_MUSIC);
        if (resumeMusic) {
            soundM.resume(SoundManager.PLAY_MUSIC);
        } else {
            soundM.play(SoundManager.PLAY_MUSIC); // Start from beginning
        }
    }
}

