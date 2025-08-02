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
public class GameStateManager {
    private int commandNum;
    private GameState currentState;
    private GamePanel gp;

    public GameStateManager(GamePanel gp) {
        this.gp = gp;
        this.commandNum = 0;
        this.currentState = new TitleMainState(this);
    }

    public void changeGameState() {
        currentState.changeState(commandNum);
        commandNum = 0;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCommandNum(int num) {
        this.commandNum = num;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public GamePanel getGamePanel() {
        return gp;
    }
}

