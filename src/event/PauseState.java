/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import sound.SoundManager;

/**
 *
 * @author HP
 */
public class PauseState implements GameState {
    private GameStateManager gsm;

    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState(int commandNum) {
        gsm.getGamePanel().restartGame();
    }

    @Override
    public GameState getGameState() {
        return this;
    }
}

