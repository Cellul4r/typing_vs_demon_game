/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class Enemy extends Entity{
    
    private static final int DAMAGE = 1;
    private static final long INVINCIBLE_DURATION = 500;
    private static final int BASE_SPEED = 2;
    private static final int MAX_SPEED = 4;
    private static final Font FONT = new Font("Times New Roman", Font.BOLD, 16);
    private BufferedImage image;
    private String word;
    private final double speedFactor;
    private boolean invincibleFrame = false;
    private long invincibleTime;
    
    private String[] dictionary = {"d", "b", "c", "a"};
    
    public Enemy(GamePanel gp, int channel){
        this(gp, channel, 1);
    }
    
    public Enemy(GamePanel gp, int channel, int level) {
        super(gp, channel);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/resource/player_res/Mario.png"));
        } catch (IOException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Random rand = new Random();
        this.channel = channel;
        this.x = GamePanel.TILE_SIZE * 20;
        this.y = gp.getChannelY(channel);
        this.direction = "left";
        this.word = dictionary[rand.nextInt(4)];
        
        speedFactor = switch(gp.difficulty)  {
            case 0 -> 0.10;
            case 1 -> 0.15;
            case 2 -> 0.20;
            default -> 0.00;
        };
        this.speed = BASE_SPEED + (int)(BASE_SPEED * level * speedFactor);
        this.speed = Math.min(speed, MAX_SPEED);
        System.out.println(speed + " " + level);
    }
    
    @Override
    public void update(){
        
        collisionOn = gp.getCChecker().checkCollision(this);
        if(!collisionOn) {
            x -= speed;
        } else if(!invincibleFrame){
            gp.getPlayer().decreaseHealth(DAMAGE);
            setInvincibility();
        }
        if (invincibleFrame && System.currentTimeMillis() - invincibleTime > INVINCIBLE_DURATION) {
            invincibleFrame = false;
        }
        updateAnimation();
    }
    
    @Override
    protected void updateAnimation() {/* will do later */ };
    
    @Override
    public void draw(Graphics2D g2){
        // draw Enemy
        g2.drawImage(image, x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        
        // draw Enemy's Word
        g2.setColor(Color.WHITE);
        g2.fillRect(x + (GamePanel.TILE_SIZE - (g2.getFontMetrics().stringWidth(word)))/2 - 10, y - 25, g2.getFontMetrics().stringWidth(word) + 20, g2.getFontMetrics().getHeight());
        
        g2.setFont(FONT);
        g2.setColor(Color.BLACK);
        g2.drawRect(x + (GamePanel.TILE_SIZE - (g2.getFontMetrics().stringWidth(word)))/2 - 10, y - 25, g2.getFontMetrics().stringWidth(word) + 20, g2.getFontMetrics().getHeight());
        g2.drawString(word, x + (GamePanel.TILE_SIZE - (g2.getFontMetrics().stringWidth(word)))/2, y - 10);
    }
    
    public void setWord(int index){ this.word = dictionary[index];}
    
    public String getWord(){ return word.toUpperCase();}
    
    public int getYs(){ return y;}
    
    private void setInvincibility() {
        invincibleFrame = true;
        invincibleTime = System.currentTimeMillis();
    }
   
}
