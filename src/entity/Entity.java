/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author HP
 */
public class Entity {
    
    protected int x, y;
    protected int speed;
    protected BufferedImage up1, up2, down1, down2, right, left1, left2;
    protected String direction;
    
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
}
