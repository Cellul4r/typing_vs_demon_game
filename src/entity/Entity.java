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
    
    public int x, y;
    public int speed;
    
    public BufferedImage up1, up2, down1, down2, right;
    public String direction;
    
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
