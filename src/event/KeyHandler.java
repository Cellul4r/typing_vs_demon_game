/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import game_2d.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
            if(code == KeyEvent.VK_UP){
                gp.commandNum--;
                if(gp.commandNum < 0)
                    gp.commandNum = 1;
            }
            if(code == KeyEvent.VK_DOWN){
                gp.commandNum++;
                if(gp.commandNum > 1)
                    gp.commandNum = 0;
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.commandNum == 0){
                    gp.playMusic(0);
                    gp.titleScreenState = 1;
                }
                if(gp.commandNum == 1){
                    System.exit(0);
                }
            }
        }
        else if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == 1){
            if(code == KeyEvent.VK_UP){
                gp.commandNum--;
                if(gp.commandNum < 0)
                    gp.commandNum = 1;
            }
            if(code == KeyEvent.VK_DOWN){
                gp.commandNum++;
                if(gp.commandNum > 1)
                    gp.commandNum = 0;
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.commandNum == 0){
                    gp.titleScreenState = 2;
                }
                if(gp.commandNum == 1){
                    gp.titleScreenState = 0;
                }
            }
        }
        else if(gp.gameState == GamePanel.TITLE_STATE && gp.titleScreenState == 2){
            if(code == KeyEvent.VK_UP){
                gp.commandNum--;
                if(gp.commandNum < 0)
                    gp.commandNum = 2;
            }
            if(code == KeyEvent.VK_DOWN){
                gp.commandNum++;
                if(gp.commandNum > 2)
                    gp.commandNum = 0;
            }
            if(code == KeyEvent.VK_ENTER){
                gp.stopMusic();
                gp.playSoundEffect(1);
              
                if(gp.commandNum == 0){
                    gp.gameState = GamePanel.PLAY_STATE; //Easy
                }
                if(gp.commandNum == 1){
                    gp.gameState = GamePanel.PLAY_STATE; //Medium
                }
                if(gp.commandNum == 2){
                    gp.gameState = GamePanel.PLAY_STATE; //Hard
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
                gp.stopMusic();
                gp.playSoundEffect(1);
              
                if(gp.commandNum == 0){
                    gp.gameState = GamePanel.TITLE_STATE;
                    gp.gameState = GamePanel.PLAY_STATE;//Easy
                }
                if(gp.commandNum == 1){
                    gp.gameState = GamePanel.TITLE_STATE; //Medium
                }
            }
        }
        else{
            if(code == KeyEvent.VK_UP){
                upPressed = true;
            } else if(code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            this.keyChar = e.getKeyChar();
            if(keyChar == KeyEvent.VK_ENTER || keyChar == KeyEvent.VK_SPACE){
                enterPressed = true;
            }
            if(keyChar == KeyEvent.VK_BACK_SPACE || keyChar == KeyEvent.VK_DELETE){
                deletePressed = true;
            }
        
            if(code == KeyEvent.VK_ESCAPE){
                if(gp.gameState == GamePanel.PLAY_STATE){
                    gp.gameState = GamePanel.PAUSE_STATE;
                }
                else if(gp.gameState == GamePanel.PAUSE_STATE){
                    gp.gameState = GamePanel.PLAY_STATE;
                }
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed = false;
        } else if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(keyChar == KeyEvent.VK_ENTER || keyChar == KeyEvent.VK_SPACE){
            enterPressed = false;
        }
        if(keyChar == KeyEvent.VK_BACK_SPACE || keyChar == KeyEvent.VK_DELETE){
            deletePressed = false;
        }
        
    }
    
    public boolean getUpPressed() {
        return upPressed;
    }
    
    public boolean getDownPressed() {
        return downPressed;
    }
    
    public boolean getEnterPressed() {
        return enterPressed;
    }
    
    public boolean getDeletePressed() {
        return deletePressed;
    }
    
    public char getKeyChar(){
        return keyChar;
    }
    
    public void resetKeyUpPressed() {
        upPressed = false;
    }
    
    public void resetKeyDownPressed() {
        upPressed = false;
    }
    
    public void resetKeyChar(){
        keyChar = '\0';
    }
    
}
