/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author HP
 */
public interface GameState {
    
    void changeState(int commandNum);
    GameState getGameState();
    
}
