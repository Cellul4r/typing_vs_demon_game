package event;

import game_2d.GamePanel;
import gamestate.GameOverState;
import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.PauseState;
import gamestate.PlayState;
import gamestate.TitleDifficultyState;
import gamestate.TitleMainState;
import gamestate.TitleTutorialState;
import sound.SoundManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author HP
 */
public class KeyHandler implements KeyListener {
    
    private boolean upPressed, downPressed, enterPressed, deletePressed, spacePressed;
    private char keyChar;
    GamePanel gp;
    
    public KeyHandler(GamePanel gp){
        this.gp=gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        GameStateManager gsm = gp.getGameStateManager();
        GameState current = gsm.getCurrentState();

        switch (code) {
            case KeyEvent.VK_UP -> {
                upPressed = true;
                gp.commandNum--;
                if (gp.commandNum < 0)
                    gp.commandNum = getMaxCommandNum();
            }
            case KeyEvent.VK_DOWN -> {
                downPressed = true;
                gp.commandNum++;
                if (gp.commandNum > getMaxCommandNum())
                    gp.commandNum = 0;
            }
            case KeyEvent.VK_LEFT -> {
                upPressed = true;
                if (current instanceof TitleTutorialState){
                    gp.commandNum++;
                    if (gp.commandNum > getMaxCommandNum())
                        gp.commandNum = 0;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                downPressed = true;
                if (current instanceof TitleTutorialState){
                    gp.commandNum--;
                    if (gp.commandNum < getMaxCommandNum())
                        gp.commandNum = 0;
                }
            }
            case KeyEvent.VK_ENTER -> {
                enterPressed = true;
                
                if(!(current instanceof PlayState)){
                    gsm.setCommandNum(gp.commandNum);
                    gsm.changeGameState();
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                if (current instanceof PlayState) {
                    gsm.setState(new PauseState(gsm));
                } else if (current instanceof PauseState) {
                    gsm.setState(new PlayState(gsm));
                }
            }
            case KeyEvent.VK_BACK_SPACE -> deletePressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            default -> keyChar = e.getKeyChar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP, KeyEvent.VK_LEFT -> upPressed = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT -> downPressed = false;
            case KeyEvent.VK_ENTER -> enterPressed = false;
            case KeyEvent.VK_BACK_SPACE -> deletePressed = false;
        }
    }
    
    // Returns the max valid commandNum for the current state 
    // like in the TitleMainState, there's only Start and Exit so we return Max=1
    private int getMaxCommandNum() {
        GameState state = gp.getGameStateManager().getCurrentState();
        
        if (state instanceof TitleMainState) return 1;
        if (state instanceof TitleTutorialState) return 1;
        if (state instanceof TitleDifficultyState) return 2;
        if (state instanceof GameOverState) return 0;
        return 0;
    }
    
    public void resetAllKeys() {
        upPressed = false;
        downPressed = false;
        enterPressed = false;
        deletePressed = false;
        spacePressed = false;
        keyChar = '\0';
    }
    
    public boolean getUpPressed() {return upPressed;}
    
    public boolean getDownPressed() {return downPressed;}
    
    public boolean getEnterPressed() {return enterPressed;}
    
    public boolean getDeletePressed() {return deletePressed;}
    
    public boolean getSpacePressed() { return spacePressed; }
    
    public char getKeyChar(){ return keyChar;}
    
    public void resetKeyUpPressed() { upPressed = false;}
    
    public void resetKeyDownPressed() { upPressed = false;}
    
    public void resetKeyDeletePressed() { deletePressed = false; }
    
    public void resetKeyEnterPressed() { enterPressed = false; }
    
    public void resetKeySpacePressed() { spacePressed = false; }
    
    public void resetKeyChar(){ keyChar = '\0';}
    
}

//int code = e.getKeyCode();
//        switch (code) {
//            case KeyEvent.VK_UP, KeyEvent.VK_LEFT -> upPressed = true;
//            case KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT -> downPressed = true;
//            case KeyEvent.VK_BACK_SPACE -> deletePressed = true;
//            case KeyEvent.VK_ENTER -> {
//                enterPressed = true;
//                // Tell GameStateManager to change state via current state's logic
//                if (gp.getGameStateManager().getCurrentState() instanceof PauseState) {
//                    gp.getGameStateManager().setState(new TitleMainState(gp.getGameStateManager()));
//                }
//                gp.getGameStateManager().setCommandNum(gp.commandNum);
//                gp.getGameStateManager().changeGameState();
//                
//            }
//            case KeyEvent.VK_ESCAPE -> {
//                if (gp.getGameStateManager().getCurrentState() instanceof PlayState) {
//                    gp.getGameStateManager().setState(new PauseState(gp.getGameStateManager()));
//                } else if (gp.getGameStateManager().getCurrentState() instanceof PauseState) {
//                    gp.getGameStateManager().setState(new PlayState(gp.getGameStateManager()));
//                }
//            }
//            default -> keyChar = e.getKeyChar();
//        }
//    }