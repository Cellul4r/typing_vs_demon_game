package entity;

import game_2d.GamePanel;
import event.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import sound.SoundManager;

/**
 *
 * @author HP
 */
public class Player extends Entity {
    
    private static final int DEFAULT_X = 2 * GamePanel.TILE_SIZE;
    private static final int DEFAULT_CHANNEL = 2;
    private final KeyHandler keyH;
    private String userInput = "";
    private char keyChar;
    private int maxHealth;
    private int health;
    private int score = 0;
    private BufferedImage playerImage;
    private Item item;
//    private int spriteCounter = 0;
//    private int spriteNum = 1;
    
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp, DEFAULT_X, GamePanel.CHANNEL_SPACING, DEFAULT_CHANNEL);
        this.keyH = keyH;
        
        setDefaultValues();
        getImage();
    }
    
    public void setDefaultValues(){
        direction = "down";
        maxHealth = 50;
        health = maxHealth;
    }
    
    @Override
    protected void getImage(){
        try{
            playerImage = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/player1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
     
    @Override
    public void update(){
        if(health == 0) {
            gp.gameState = GamePanel.GAME_OVER_STATE;
            gp.getSoundM().stopMusic(SoundManager.ENEMY_SOUND);
        }
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
        } else if(keyH.getDeletePressed() && userInput.length()!=0){
            userInput = userInput.substring(0, userInput.length() - 1);
            keyH.resetKeyDeletePressed();
        } else if(keyH.getTabPressed()) {
            if(item != null) {
                item.useItem();
                item = null;
            }
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
//        spriteCounter++;
//        if(spriteCounter > 1){
//            if(spriteNum == 1){
//                spriteNum = 2;
//            }
//            else if(spriteNum == 2){
//                spriteNum = 1;
//            }
//            spriteCounter = 0;
//        }
    }
    
    @Override
    public void draw(Graphics2D g2){
        //g2.setColor(Color.black);
        int tileSize = GamePanel.TILE_SIZE;
        
        // draw Player
        g2.drawImage(playerImage, x, y, 3 * tileSize / 2, 3 * tileSize / 2, null);
    }
    
    public void changeHealth(int amount) {
        health += amount;
        if(health < 0) health = 0;
        if(health > maxHealth) health = maxHealth;
    }
    
    public void setItem(Item item) { this.item = item; }
    
    public int getHealth(){ return this.health;}
    
    public String getUserInput() { return this.userInput;}
    
    public int getScore() { return this.score;}
    
    public int getMaxHealth() { return this.maxHealth;}
}
