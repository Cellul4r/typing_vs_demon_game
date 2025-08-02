/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author HP
 */
public class TitleMainState implements GameState {
    private GameStateManager gsm;
    
    public TitleMainState(GameStateManager gsm){
        this.gsm = gsm;
    }
    
    @Override
    public void changeState(int commandNum){
        switch (commandNum){
            case 0 -> gsm.setState(new TitleTutorialState(gsm));
            case 1 -> System.exit(0);
        }
    }
    @Override
    public GameState getGameState(){
        return this;
    }
}
