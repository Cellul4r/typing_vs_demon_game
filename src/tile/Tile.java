/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import java.awt.image.BufferedImage;

/**
 *
 * @author cellul4r
 */
public class Tile {
    
    private BufferedImage image;
    private boolean collision = false;
        
    public Tile(BufferedImage image, boolean collision){
        this.image = image;
        this.collision = collision;
    }
    
    public boolean getCollision() {
        return this.collision;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
}
