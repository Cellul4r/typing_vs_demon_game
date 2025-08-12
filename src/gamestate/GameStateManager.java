/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

import game_2d.GamePanel;

/**
 *
 * @author HP
 */
public class GameStateManager {
    private GameState currentState;
    private GamePanel gp;

    public GameStateManager(GamePanel gp) {
        this.gp = gp;
        this.currentState = new TitleMainState(this);
    }

    public void changeGameState() {
        currentState.changeState();
    }

    public void setState(GameState newState) {
        this.currentState = newState;
        currentState.doSound(gp.getSoundM());
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public GamePanel getGamePanel() {
        return gp;
    }
}

