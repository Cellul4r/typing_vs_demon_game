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
import word_generator.WordGenerator;

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
    
    private static BufferedImage enemyImage1;
    private static BufferedImage enemyImage2;
    private BufferedImage image;
    
    private String word;
    private double speedFactor;
    private boolean invincibleFrame = false;
    private long invincibleTime;
    
    public Enemy(GamePanel gp, int channel){
        this(gp, channel, 1);
    }
    
    public Enemy(GamePanel gp, int channel, int level) {
        super(gp, channel);
        
        getImage();
        this.channel = channel;
        this.x = GamePanel.TILE_SIZE * 20;
        this.y = gp.getChannelY(channel);
        this.direction = "left";
        this.word = randomWord();
        
        setSpeedFactor(gp.difficulty);
        this.speed = BASE_SPEED + (int)(BASE_SPEED * level * speedFactor);
        this.speed = Math.min(speed, MAX_SPEED);
        System.out.println(speed + " " + level);
    }
    
    public static void loadImage() {
        try {
            enemyImage1 = ImageIO.read(Enemy.class.getResourceAsStream("/resource/enemy_res/enemy1.png"));
            enemyImage2 = ImageIO.read(Enemy.class.getResourceAsStream("/resource/enemy_res/enemy2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    @Override
    protected void getImage() {
        Random rm = new Random();
        int enemyType = rm.nextInt(2);
        image = switch(enemyType) {
            case 0 -> enemyImage1;
            case 1 -> enemyImage2;
            default -> null;
        };
    }
    
    private void setSpeedFactor(int difficulty) {
        speedFactor = switch(difficulty)  {
            case 0 -> 0.10;
            case 1 -> 0.15;
            case 2 -> 0.20;
            default -> 0.00;
        };
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
    protected void updateAnimation() {
    }
    
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
    
    public String randomWord() {
        Random rm = new Random();
        int type = rm.nextInt(3);
        return WordGenerator.randomWord(type);
    }
    
    public String getWord(){ return word.toUpperCase();}
    
    public int getYs(){ return y;}
    
    private void setInvincibility() {
        invincibleFrame = true;
        invincibleTime = System.currentTimeMillis();
    }
   
}
