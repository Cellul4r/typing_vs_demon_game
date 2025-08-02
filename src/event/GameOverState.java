/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author HP
 */
public class GameOverState implements GameState {
    private GameStateManager gsm;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState(int commandNum) {
        if (commandNum == 0) {
            gsm.getGamePanel().restartGame();
        }
    }

    @Override
    public GameState getGameState() {
        return this;
    }
}
