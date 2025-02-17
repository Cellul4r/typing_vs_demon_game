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
public class Player extends Entity {
    
    private final KeyHandler keyH;
    private Font font;
    private String userInput = "";
    private char keyChar;
    private int playerRow;
    
    private int maxHealth;
    private int health;
    
//    private int channel;
    public Player(GamePanel gp){
        super(gp);
        this.keyH = new KeyHandler();
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues(){
        //Player's default position
        x = 2 * gp.tileSize;
        y = gp.getChannelY(1) + 4 * gp.tileSize;
        
        font = new Font("Times New Roman", Font.BOLD, 40);
        playerRow = gp.getChannelY(2);
        speed = gp.channelSpacing; //player moves by X pixels
        direction = "down";
        
        maxHealth = 12;
        health = maxHealth;
    }
    
    public void getPlayerImage(){
        
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player_res/test2.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player_res/Mario.png"));
            full_heart = ImageIO.read(getClass().getResourceAsStream("/player_res/FireMage.png"));
            empty_heart = ImageIO.read(getClass().getResourceAsStream("/player_res/penguin.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public KeyHandler getKeyHandler() {
        return this.keyH;
    }
    
    @Override
    public void update(){
        if(keyH.getUpPressed() || keyH.getDownPressed()) {
            if(keyH.getUpPressed()){
                direction = "up";
            } else if (keyH.getDownPressed()){
                direction = "down";
            }
            
            collisionOn = gp.getCChecker().checkCollision(this);
            if(!collisionOn) {
                switch(direction) {
                    case "up" -> {
                        y -= speed;
                        playerRow -= speed;
                    }
                    case "down" -> {
                        y += speed;
                        playerRow += speed;
                    }
                }
            } else {
                System.out.print("Collision!");
            }
        }
        
        keyChar = Character.toUpperCase(keyH.getKeyChar());
        if(Character.isLetter(keyChar)){
            userInput+=keyChar;
            keyH.resetKeyChar();
        }
        
        if(keyH.getEnterPressed()){ 
            for(int i=0;i<gp.enemyAmount;i++){
                if(gp.enemyList[i] == null)
                    continue;
                Enemy enemy = (Enemy)gp.enemyList[i];
                if(enemy.getWord().equals(userInput) && enemy.getYs() == playerRow){
                    gp.enemyList[i] = null;
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
    
    @Override
    public void draw(Graphics2D g2){
        //g2.setColor(Color.black);
        int tileSize = gp.tileSize;
        
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
        
        int u = 0;
        int heart_x = gp.tileSize / 2;
        int heart_y = gp.tileSize / 2;
        while(u < maxHealth){
            g2.drawImage(empty_heart, heart_x, heart_y, gp.tileSize, gp.tileSize, null);
            u++;
            heart_x += gp.tileSize;
        }
        
        u = 0;
        heart_x = gp.tileSize / 2;
        heart_y = gp.tileSize / 2;
        while(u < health){
            g2.drawImage(empty_heart, heart_x, heart_y, gp.tileSize, gp.tileSize, null);
            if(u < health){
                g2.drawImage(full_heart, heart_x, heart_y, gp.tileSize, gp.tileSize, null);
            }
            u++;
            heart_x += gp.tileSize;
        }
    }
    
    public void setHealth(){
        health -= 1;
    }
    
}
