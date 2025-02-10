/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author HP
 */
public class SuperObject {
    
    // OBSOLETE RIGHT NOW UNLESS YOU WANT TO ADD OBJECT
    
    
    public BufferedImage image;
    public String name;
    protected int obj_x, obj_y, count;
    private int obj_speed = 7;

    public void update(){
        obj_x -= obj_speed;
    }
    public void draw(Graphics2D g2, GamePanel gp){
        
        if(obj_x >= 3 * gp.tileSize){
            g2.drawImage(image, obj_x, obj_y, gp.tileSize, gp.tileSize, null);
        }else{
            gp.obj[count] = null;
        }
    }

    public void setInitialPosition(int x, int y, int count) {
        this.obj_x = x;
        this.obj_y = y;
        this.count = count;
    }
    
}
