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
    
    private GamePanel gp;
    private final KeyHandler keyH;
//    private int channel;
    public Player(GamePanel gp, KeyHandler keyH){
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        //Player's default position
        x = 2 * GamePanel.TILE_SIZE;
        y = GamePanel.channelRow[2];
        speed = GamePanel.CHANNEL_SPACING; //player moves by X pixels
        System.out.println(GamePanel.CHANNEL_SPACING);
        direction = "down";
    }
    
    public void getPlayerImage(){
        
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
//            up1 = ImageIO.read(getClass().getResourceAsStream("/player_res/grp9WT.png"));
//            up2 = ImageIO.read(getClass().getResourceAsStream("/player_res/grp9WT.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player_res/penguin.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player_res/penguin.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player_res/Mario.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void update(){

        if(keyH.getUpPressed()){
            direction = "up";
            y -= speed;
        } else if (keyH.getDownPressed()){
            direction = "down";
            y += speed;
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
    
    public void draw(Graphics2D g2){
        //g2.setColor(Color.black);
        int tileSize = GamePanel.TILE_SIZE;
        g2.fillRect(3 * tileSize, 2 * tileSize, tileSize, GamePanel.GAME_ROW * GamePanel.CHANNEL_SPACING); //Draw Rectangle at X, Y, width, height
//        g2.fillRoundRect(150, 100, 30, 560, 20, 20); //End Line (int x, int y, int width, int height, int arcWidth, int arcHeight)
        
//        g2.fillRoundRect(150, 130, 3000, 1, 10, 10);
        g2.fillRoundRect(3 * tileSize, 2 * tileSize, 3000, 1, 10, 10);
        g2.fillRoundRect(3 * tileSize, 4 * tileSize, 3000, 1, 10, 10);
        g2.fillRoundRect(3 * tileSize, 6 * tileSize, 3000, 1, 10, 10);
        g2.fillRoundRect(3 * tileSize, 8 * tileSize, 3000, 1, 10, 10);
        g2.fillRoundRect(3 * tileSize, 10 * tileSize, 3000, 1, 10, 10);
        g2.fillRoundRect(3 * tileSize, 12 * tileSize, 3000, 1, 10, 10);
//        g2.fillRoundRect(150, 330, 3000, 1, 10, 10);
//        g2.fillRoundRect(150, 430, 3000, 1, 10, 10);
//        g2.fillRoundRect(150, 530, 3000, 1, 10, 10);
//        g2.fillRoundRect(150, 630, 3000, 1, 10, 10);
        
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
        g2.drawImage(image, x, y, 1 * GamePanel.TILE_SIZE, 1 * GamePanel.TILE_SIZE, null);
    }
}
