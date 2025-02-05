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
    
    private boolean upPressed, downPressed;

    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.println(KeyEvent.getKeyText(code));
        if(code == KeyEvent.VK_UP){ //If you press W
            upPressed = true;
        } else if(code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){ //If you press W
            upPressed = false;
        } else if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
    }
    
    public boolean getUpPressed() {
        return upPressed;
    }
    
    public boolean getDownPressed() {
        return downPressed;
    }
    
}
