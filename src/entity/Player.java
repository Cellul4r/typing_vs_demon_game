/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import game_2d.KeyHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class Player extends Entity{
    
    private final KeyHandler keyH;
    private Font font;
    private String userInput = "";
    private char keyChar;
    private int playerRow;
    
//    private int channel;
    public Player(GamePanel gp){
        this.keyH = new KeyHandler();
        setDefaultValues(gp);
        getPlayerImage();
    }
    
    public void setDefaultValues(GamePanel gp){
        //Player's default position
        x = 2 * gp.tileSize;
        y = gp.getChannelY(1) + 3 * gp.tileSize / 2;
        this.font = new Font("Times New Roman", Font.BOLD, 40);
        playerRow = gp.getChannelY(2);
        speed = gp.channelSpacing; //player moves by X pixels
        direction = "down";
    }
    
    public void getPlayerImage(){
        
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
//            up1 = ImageIO.read(getClass().getResourceAsStream("/player_res/grp9WT.png"));
//            up2 = ImageIO.read(getClass().getResourceAsStream("/player_res/grp9WT.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player_res/Mario.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public KeyHandler getKeyHandler() {
        return this.keyH;
    }
    
    public void update(GamePanel gp){

        if(keyH.getUpPressed()){
            direction = "up";
            y -= speed;
            playerRow -= speed;
        } else if (keyH.getDownPressed()){
            direction = "down";
            y += speed;
            playerRow += speed;
        }
        
        keyChar = Character.toUpperCase(keyH.getKeyChar());
        if(Character.isLetter(keyChar)){
            userInput+=keyChar;
            keyH.resetKeyChar();
        }
        
        if(keyH.getEnterPressed()){
            for(int i=0;i<gp.enemies_number;i++){
                if(gp.obj[i] == null)
                    continue;
                if(gp.obj[i].getWord().equals(userInput) && gp.obj[i].getYs() == playerRow){
                    gp.obj[i] = null;
                    break;
                }
            }
            userInput = "";
        }

        if(keyH.getDeletePressed()){
            userInput = userInput.substring(0, userInput.length() - 1);
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
    
    public void draw(Graphics2D g2, GamePanel gp){
        //g2.setColor(Color.black);
        int tileSize = gp.tileSize;
        g2.fillRect(3 * tileSize, 2 * tileSize, tileSize, gp.gameRow * gp.channelSpacing); //Draw Rectangle at X, Y, width, height
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
        g2.drawImage(image, x, y, tileSize, tileSize, null);
        
        g2.setFont(font);
        g2.drawString(userInput, gp.tileSize * 4, gp.getChannelY(4) + 4 * gp.tileSize / 2);
    }
    
//    private boolean canMoveUp(){
//        return !(y == gp.getChannelY(1) + 3 * gp.tileSize / 2 - (2 * gp.channelSpacing));
//    }
//    
//    private boolean canMoveDown(){
//        return !((y == gp.getChannelY(3) + 3 * gp.tileSize / 2));
//    }
    
}
