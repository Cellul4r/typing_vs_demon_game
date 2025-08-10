package event;

import game_2d.GamePanel;
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
        if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == GamePanel.TITLE_MAIN){
            switch (code) {
                case KeyEvent.VK_UP -> {
                    gp.commandNum--;
                    if(gp.commandNum < 0)
                        gp.commandNum = 1;
                }
                case KeyEvent.VK_DOWN -> {
                    gp.commandNum++;
                    if(gp.commandNum > 1)
                        gp.commandNum = 0;
                }
                case KeyEvent.VK_ENTER -> {
                    if(gp.commandNum == 0){
                        gp.titleScreenState = GamePanel.TITLE_TUTORIAL;
                    } else if(gp.commandNum == 1){
                        System.exit(0);
                    }
                }
            }
        } else if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == GamePanel.TITLE_TUTORIAL){
            switch (code) {
                case KeyEvent.VK_UP -> {
                    gp.commandNum--;
                    if(gp.commandNum < 0)
                        gp.commandNum = 1;
                }
                case KeyEvent.VK_DOWN -> {
                    gp.commandNum++;
                    if(gp.commandNum > 1)
                        gp.commandNum = 0;
                }
                case KeyEvent.VK_ENTER -> {
                    if(gp.commandNum == 0){
                        gp.titleScreenState = GamePanel.TITLE_DIFFICULTY;
                    }   if(gp.commandNum == 1){
                        gp.titleScreenState = GamePanel.TITLE_MAIN;
                    }
                }
            }
        }
        else if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == GamePanel.TITLE_DIFFICULTY){
            switch (code) {
                case KeyEvent.VK_UP -> {
                    gp.commandNum--;
                    if(gp.commandNum < 0)
                        gp.commandNum = 0;
                }
                case KeyEvent.VK_DOWN -> {
                    gp.commandNum++;
                    if(gp.commandNum > 0)
                        gp.commandNum = 0;
                }
                case KeyEvent.VK_ENTER -> {
                    gp.getSoundM().stop(SoundManager.TITLE_MUSIC);
                    gp.getSoundM().play(SoundManager.PLAY_MUSIC);
                    if(gp.commandNum == 0) {
                        gp.gameState = GamePanel.PLAY_STATE; //Medium
                        gp.difficulty = GamePanel.MEDIUM;
                    }
                }
            }
        }
        else if(gp.gameState == GamePanel.GAME_OVER_STATE){
            if(code == KeyEvent.VK_UP || code == KeyEvent.VK_LEFT){
                gp.commandNum--;
                if(gp.commandNum < 0)
                    gp.commandNum = 1;
            }
            if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT){
                gp.commandNum++;
                if(gp.commandNum > 0)
                    gp.commandNum = 0;
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.commandNum == 0){
                    gp.restartGame();
                }
            }
        } else {
            code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_UP, KeyEvent.VK_LEFT -> upPressed = true;
                case KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT -> downPressed = true;
                case KeyEvent.VK_ENTER -> enterPressed = true;
                case KeyEvent.VK_BACK_SPACE -> deletePressed = true;
                case KeyEvent.VK_ESCAPE -> {
                    if(gp.gameState == GamePanel.PLAY_STATE){
                        gp.gameState = GamePanel.PAUSE_STATE;
                    } else if(gp.gameState == GamePanel.PAUSE_STATE){
                        gp.gameState = GamePanel.PLAY_STATE;
                    }
                }
                case KeyEvent.VK_SPACE -> spacePressed = true;
                default -> keyChar = e.getKeyChar();
            }
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
