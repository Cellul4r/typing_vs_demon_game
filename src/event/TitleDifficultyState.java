/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import game_2d.GamePanel;

/**
 *
 * @author HP
 */
public class TitleDifficultyState implements GameState {
    private GameStateManager gsm;

    public TitleDifficultyState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState(int commandNum) {
        switch (commandNum) {
            case 0 -> {
                gsm.getGamePanel().difficulty = GamePanel.EASY;
                gsm.setState(new PlayState(gsm));
            }
            case 1 -> {
                gsm.getGamePanel().difficulty = GamePanel.MEDIUM;
                gsm.setState(new PlayState(gsm));
            }
            case 2 -> {
                gsm.getGamePanel().difficulty = GamePanel.HARD;
                gsm.setState(new PlayState(gsm));
            }
        }
    }

    @Override
    public GameState getGameState() {
        return this;
    }
}

