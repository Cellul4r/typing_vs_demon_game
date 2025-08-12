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
public class PauseState implements GameState {
    private GameStateManager gsm;
    private int commandNum = 0;

    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState() {
        gsm.getGamePanel().restartGame();
    }

    @Override
    public GameState getGameState() {
        return this;
    }

    @Override
    public void moveUp() {
        commandNum++;
        if(commandNum > 0) {
            commandNum = 0;
        }
    }

    @Override
    public void moveDown() {
        commandNum--;
        if(commandNum > 0) {
            commandNum = 0;
        }
    }

    @Override
    public int getCommandNum() {
        return this.commandNum;
    }
    
    @Override
    public void doSound(SoundManager soundM){
        soundM.pause(SoundManager.PLAY_MUSIC);
    }
}

