/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author HP
 */
public class KeyHandler implements KeyListener {
    
    private boolean upPressed, downPressed, enterPressed, deletePressed;
    private char keyChar;

    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.println(KeyEvent.getKeyText(code));
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
    
    public void resetKeyChar(){
        keyChar = '\0';
    }
    
}
