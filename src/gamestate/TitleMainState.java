/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

/**
 *
 * @author HP
 */
public class TitleMainState implements GameState {
    private GameStateManager gsm;
    private int commandNum = 0;
    
    public TitleMainState(GameStateManager gsm){
        this.gsm = gsm;
    }
    
    @Override
    public void changeState(){
        switch (commandNum){
            case 0 -> gsm.setState(new TitleTutorialState(gsm));
            case 1 -> System.exit(0);
        }
    }
    @Override
    public GameState getGameState(){
        return this;
    }

    @Override
    public void moveUp() {
        commandNum--;
        if (commandNum < 0) 
            commandNum = 1;
    }

    @Override
    public void moveDown() {
        commandNum++;
        if (commandNum > 1) 
            commandNum = 0;
    }

    @Override
    public int getCommandNum() {
        return commandNum;
    }
}
