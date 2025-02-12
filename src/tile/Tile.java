/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import java.awt.Color;

/**
 *
 * @author cellul4r
 */
public class Tile {
    
    private Color color;
    private boolean collision = false;
    
    public Tile(Color color, boolean collision) {
        this.color = color;
        this.collision = collision;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public boolean getCollision() {
        return this.collision;
    }
}
