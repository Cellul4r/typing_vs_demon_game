/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

/**
 *
 * @author HP
 */
public interface GameState {
    
    void changeState();
    GameState getGameState();
    void moveUp();
    void moveDown();
    int getCommandNum(); // for UI
    
}
