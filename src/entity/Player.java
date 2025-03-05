package entity;

import game_2d.GamePanel;
import event.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    private Item item;
    
    
    
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp, DEFAULT_X, GamePanel.CHANNEL_SPACING, DEFAULT_CHANNEL);
        this.keyH = keyH;
        this.maxHealth = 100;
        this.health = maxHealth;
    }
    
    @Override
    protected void loadImage() {
        spriteTime = 5;
        imageAmount = 5;
        images = new BufferedImage[imageAmount];
        try{
            images[0] = ImageIO.read(Player.class.getResourceAsStream("/resource/player_res/player1.png"));
            images[1] = ImageIO.read(Player.class.getResourceAsStream("/resource/player_res/player2.png"));
            images[2] = ImageIO.read(Player.class.getResourceAsStream("/resource/player_res/player3.png"));
            images[3] = ImageIO.read(Player.class.getResourceAsStream("/resource/player_res/player4.png"));
            images[4] = ImageIO.read(Player.class.getResourceAsStream("/resource/player_res/player5.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
         
    @Override
    public void update(){
        if(health == 0) {
            gp.gameState = GamePanel.GAME_OVER_STATE;
            gp.getSoundM().stopMusic(SoundManager.ENEMY_SOUND);
            gp.getSoundM().stopMusic(SoundManager.PLAY_MUSIC);
            gp.getSoundM().playSoundEffect(SoundManager.PLAYER_LOSE);
            return;
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
            keyH.resetKeyEnterPressed();
        } else if(keyH.getDeletePressed() && userInput.length()!=0){
            userInput = userInput.substring(0, userInput.length() - 1);
            keyH.resetKeyDeletePressed();
        } else if(keyH.getSpacePressed()) {
            if(item != null) {
                item.useItem();
                item = null;
            }
            keyH.resetKeySpacePressed();
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
    public void draw(Graphics2D g2){
        //g2.setColor(Color.black);
        int tileSize = GamePanel.TILE_SIZE;
        
        // draw Player
        
        g2.drawImage(image, x, y, 3 * tileSize / 2, 3 * tileSize / 2, null);
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
    
    public Item getItem() { return this.item; }
}
