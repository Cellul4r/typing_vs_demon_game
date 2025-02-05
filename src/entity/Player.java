/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import game_2d.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;
    
    public Player(GamePanel gp, KeyHandler keyH){
        
        this.gp = gp;
        this.keyH = keyH;
        
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        //Player's default position
        x = 70;
        y = 155;
        speed = 100; //player moves by X pixels
        direction = "down";
    }
    
    public void getPlayerImage(){
        
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player_res/grp9WT.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player_res/grp9WT.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player_res/penguin.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player_res/Mario.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player_res/Mario.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed == true){
                direction = "up";
                y -= speed;
            }
            else if (keyH.downPressed == true){
                direction = "down";
                y += speed;
            }
            else if (keyH.leftPressed == true){
                x -= speed;
            }
            else if (keyH.rightPressed == true){
                x += speed;
            }
        
        
            //Change Sprite (Ignore this until we have time to polish it)
            spriteCounter++;
            if(spriteCounter > 1){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.black);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize); //Draw Rectangle at X, Y, width, height
        g2.fillRoundRect(150, 100, 30, 560, 20, 20); //End Line (int x, int y, int width, int height, int arcWidth, int arcHeight)
        
        g2.fillRoundRect(150, 130, 3000, 1, 10, 10);
        g2.fillRoundRect(150, 230, 3000, 1, 10, 10);
        g2.fillRoundRect(150, 330, 3000, 1, 10, 10);
        g2.fillRoundRect(150, 430, 3000, 1, 10, 10);
        g2.fillRoundRect(150, 530, 3000, 1, 10, 10);
        g2.fillRoundRect(150, 630, 3000, 1, 10, 10);
        
        BufferedImage image = null;
        
        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
