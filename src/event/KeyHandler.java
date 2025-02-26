package event;

import game_2d.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import sound.SoundManager;

/**
 *
 * @author HP
 */
public class KeyHandler implements KeyListener {
    
    private boolean upPressed, downPressed, enterPressed, deletePressed;
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
        //System.out.println(KeyEvent.getKeyText(code));
        if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == 0){
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
                        gp.titleScreenState = 1;
                    } else if(gp.commandNum == 1){
                        System.exit(0);
                    }
                }
            }
        } else if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == 1){
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
                        gp.titleScreenState = 2;
                    }   if(gp.commandNum == 1){
                        gp.titleScreenState = 0;
                    }
                }
            }
        }
        else if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == 2){
            switch (code) {
                case KeyEvent.VK_UP -> {
                    gp.commandNum--;
                    if(gp.commandNum < 0)
                        gp.commandNum = 2;
                }
                case KeyEvent.VK_DOWN -> {
                    gp.commandNum++;
                    if(gp.commandNum > 2)
                        gp.commandNum = 0;
                }
                case KeyEvent.VK_ENTER -> {
                    gp.getSoundM().stopMusic(SoundManager.TITLE_MUSIC);
                    gp.getSoundM().playMusic(SoundManager.PLAY_MUSIC);
                    gp.restartGame();
                    if(gp.commandNum == 0){
                        gp.gameState = GamePanel.PLAY_STATE; //Easy
                        gp.difficulty = GamePanel.EASY;
                    }   if(gp.commandNum == 1){
                        gp.gameState = GamePanel.PLAY_STATE; //Medium
                        gp.difficulty = GamePanel.MEDIUM;
                    }   if(gp.commandNum == 2){
                        gp.gameState = GamePanel.PLAY_STATE; //Hard
                        gp.difficulty = GamePanel.HARD;
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
                if(gp.commandNum > 1)
                    gp.commandNum = 0;
            }
            if(code == KeyEvent.VK_ENTER){
                gp.getSoundM().stopMusic(SoundManager.PLAY_MUSIC);
                if(gp.commandNum == 0){
                    gp.gameState = GamePanel.TITLE_STATE;
                    gp.titleScreenState = 0;
                    gp.getSoundM().playMusic(SoundManager.TITLE_MUSIC);
                    gp.restartGame();
                }
            }
        } else {
            code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_UP -> upPressed = true;
                case KeyEvent.VK_DOWN -> downPressed = true;
                case KeyEvent.VK_ENTER -> enterPressed = true;
                case KeyEvent.VK_BACK_SPACE -> deletePressed = true;
                case KeyEvent.VK_ESCAPE -> {
                    if(gp.gameState == GamePanel.PLAY_STATE){
                        gp.gameState = GamePanel.PAUSE_STATE;
                    } else if(gp.gameState == GamePanel.PAUSE_STATE){
                        gp.gameState = GamePanel.PLAY_STATE;
                    }
                }
                default -> keyChar = e.getKeyChar();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_ENTER -> enterPressed = false;
            case KeyEvent.VK_BACK_SPACE -> deletePressed = false;
        }
    }
    
    public boolean getUpPressed() {return upPressed;}
    
    public boolean getDownPressed() {return downPressed;}
    
    public boolean getEnterPressed() {return enterPressed;}
    
    public boolean getDeletePressed() {return deletePressed;}
    
    public char getKeyChar(){ return keyChar;}
    
    public void resetKeyUpPressed() { upPressed = false;}
    
    public void resetKeyDownPressed() { upPressed = false;}
    
    public void resetKeyDeletePressed() { deletePressed = false; }
    
    public void resetKeyChar(){ keyChar = '\0';}
    
}
