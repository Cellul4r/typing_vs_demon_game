package entity;

import game_2d.GamePanel;
import event.KeyHandler;
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
    private String userInput = "";
    private char keyChar;

    private int maxHealth;
    private int health;
    
    private int score = 0;
    
//    private int channel;
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues(){
        //Player's default position
        x = 2 * gp.TILE_SIZE;
        y = gp.getChannelY(2) - gp.TILE_SIZE / 2;
        channel = 2;
        
        speed = gp.CHANNEL_SPACING; //player moves by X pixels
        direction = "down";
        
        maxHealth = 50;
        health = maxHealth;
    }
    
    public void getPlayerImage(){
        
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/player2.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/player2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/player1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/player1.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/test2.png"));
            full_heart = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/FireMage.png"));
            empty_heart = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/penguin.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
     
    @Override
    public void update(){
        checkKey();
        updateAnimation();
    }
    
    private void checkKey() {
        if(keyH.getUpPressed() || keyH.getDownPressed()) {
            if(keyH.getUpPressed()){
                direction = "up";
                keyH.resetKeyUpPressed();
            } else if (keyH.getDownPressed()){
                direction = "down";
                keyH.resetKeyDownPressed();
            }
            
            movePlayer();
            
        }
        
        keyChar = Character.toUpperCase(keyH.getKeyChar());
        if(Character.isLetter(keyChar)){
            userInput+=keyChar;
            keyH.resetKeyChar();
        }
        
        if(keyH.getEnterPressed()){ 
            if(gp.getWave().checkPlayerWord(channel, userInput)){
                score++;
                userInput = "";
            }
        }

        if(keyH.getDeletePressed() && userInput.length()!=0){
            userInput = userInput.substring(0, userInput.length() - 1);
        }
    }
    
    private void movePlayer() {
        collisionOn = gp.getCChecker().checkCollision(this);
        if(!collisionOn) {
            switch(direction) {
                case "up" -> {
                    y -= speed;
                    channel--;
                }
                case "down" -> {
                    y += speed;
                    channel++;
                }
            }
        }
    }
    
    @Override
    protected void updateAnimation() {
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
        int tileSize = gp.TILE_SIZE;
        
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
        // draw Player
        g2.drawImage(image, x, y, 3 * tileSize / 2, 3 * tileSize / 2, null);
    }
    
    public void decreaseHealth(int amount){
        this.health -= Math.min(health, amount);
        if(health == 0) {
            gp.gameState = GamePanel.GAME_OVER_STATE;
        }
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public String getUserInput() {
        return this.userInput;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public int getMaxHealth() {
        return this.maxHealth;
    }
}
