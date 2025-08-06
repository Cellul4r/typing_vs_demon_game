/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

import game_2d.GamePanel;
import sound.SoundManager;

/**
 *
 * @author HP
 */
public class TitleDifficultyState implements GameState {
    private GameStateManager gsm;
    private int commandNum = 0;

    public TitleDifficultyState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState() {
        switch (commandNum) {
            case 0 -> {
                gsm.getGamePanel().difficulty = GamePanel.EASY;
                gsm.setState(new PlayState(gsm, false));
            }
            case 1 -> {
                gsm.getGamePanel().difficulty = GamePanel.MEDIUM;
                gsm.setState(new PlayState(gsm, false));
            }
            case 2 -> {
                gsm.getGamePanel().difficulty = GamePanel.HARD;
                gsm.setState(new PlayState(gsm, false));
            }
        }
    }

    @Override
    public GameState getGameState() {
        return this;
    }
    
    @Override
    public void moveUp() {
        commandNum--;
        if (commandNum < 0) 
            commandNum = 2;
    }

    @Override
    public void moveDown() {
        commandNum++;
        if (commandNum > 2) 
            commandNum = 0;
    }

    @Override
    public int getCommandNum() {
        return commandNum;
    }
    
    @Override
    public void doSound(SoundManager soundM){
        
    }
}

