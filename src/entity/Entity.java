/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.image.BufferedImage;

/**
 *
 * @author HP
 */
public class Entity {
    
    protected int x, y;
    protected int speed;
    
    protected BufferedImage up1, up2, down1, down2, right;
    protected String direction;
    
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    
    protected String getDirection() {
        return this.direction;
    }
    
    protected int getSpeed() {
        return this.speed;
    }
    
    protected int getX() {
        return this.x;
    }
    
    protected int getY() {
        return this.y;
    }
    
    protected void setDirection(String direction) {
        this.direction = direction;
    }
    
    protected void setX(int x) {
        this.x = x;
    }
    
    protected void setY(int y) {
        this.y = y;
    }
    
}
