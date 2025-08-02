/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author HP
 */
public class TitleTutorialState implements GameState {
    private GameStateManager gsm;

    public TitleTutorialState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void changeState(int commandNum) {
        switch (commandNum) {
            case 0 -> gsm.setState(new TitleDifficultyState(gsm));
            case 1 -> gsm.setState(new TitleMainState(gsm));
        }
    }

    @Override
    public GameState getGameState() {
        return this;
    }
}

