/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author HP
 */

// will make this class abstract
public abstract class Entity {
    
    protected GamePanel gp;
    protected int x, y;
    protected int speed;
    protected BufferedImage up1, up2, down1, down2, right, left1, left2;
    protected String direction;
    protected int channel;
    
    protected Rectangle solidArea;
    protected boolean collisionOn = false;
    
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    
    public Entity(GamePanel gp) {
        this.gp = gp;
        this.solidArea = new Rectangle(0, 0, gp.tileSize / 2, gp.tileSize / 2);
    }
    
    public Entity(GamePanel gp, int channel) {
        this.gp = gp;
        this.channel = channel;
    }
    
    public abstract void update();
    public abstract void draw(Graphics2D g2);
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getSpeed() {
        return this.speed;
    }
    
    public String getDirection() {
        return this.direction;
    }
    
    public Rectangle getSolidArea() {
        return this.solidArea;
    }
}
