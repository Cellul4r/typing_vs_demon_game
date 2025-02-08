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
    
    public BufferedImage image;
    public String name;
    protected int obj_x;
    private int obj_speed = 3;

    public void update(){
        obj_x -= obj_speed;
    }
    public void draw(Graphics2D g2, GamePanel gp){
        //I'm too tired to determine column of the game right now.
        g2.drawImage(image, obj_x, gp.getChannelY(1) + 3 * gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
    }

    public void setInitialPosition(int x) {
        this.obj_x = x;
    }
}
