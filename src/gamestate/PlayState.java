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
    private int commandNum = 0;

    public PlayState(GameStateManager gsm) {
        this.gsm = gsm;
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
}

