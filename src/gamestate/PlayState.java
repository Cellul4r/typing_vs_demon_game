/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

/**
 *
 * @author HP
 */
public class PlayState implements GameState {
    private GameStateManager gsm;

    public PlayState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState(int commandNum) {
        gsm.setState(new PauseState(gsm));
        gsm.getGamePanel().getPlayer().resetFirstFrame();
    }

    @Override
    public GameState getGameState() {
        return this;
    }
}

