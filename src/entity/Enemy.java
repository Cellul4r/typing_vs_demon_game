package entity;

import game_2d.GamePanel;
import java.awt.image.BufferedImage;
/**
 *
 * @author cellul4r
 */
public abstract class Enemy extends WordObject {
    
    protected static final int DEFAULT_X = 20 * GamePanel.TILE_SIZE;
    protected static final long INVINCIBLE_DURATION = 1500;
    
    protected int damage;
    protected int baseSpeed;
    protected int maxSpeed;
    protected double speedFactor;
    protected boolean invincibleFrame = false;
    protected long invincibleTime;
    protected int spriteAttackTime;
    protected int attackImageAmount;
    protected int spriteAttackCounter = 0;
    protected int spriteAttackNum = 0;
    protected BufferedImage[] attackImages;
    protected BufferedImage freezeImage;
    
    public Enemy(GamePanel gp, int channel){
        this(gp, channel, 0);
    }
    
    public Enemy(GamePanel gp, int channel, int level) {
        super(gp, DEFAULT_X, channel);
    }
    
    public Enemy(GamePanel gp, int channel, int baseSpeed, int maxSpeed, int level) {
        super(gp, DEFAULT_X, channel);
        this.baseSpeed = baseSpeed;
        this.maxSpeed = maxSpeed;
        
    }
    
    @Override
    protected void getImage() {
        if(collisionOn && attackImages[spriteAttackNum] != null) {
            // attack animation
            image = attackImages[spriteAttackNum];
        } else {
            // walk animation
            super.getImage();
        }
    }
    
    @Override
    protected void updateAnimation() {
        if(collisionOn) {
            spriteAttackCounter++;
            if(spriteAttackCounter > spriteAttackTime) {
                spriteAttackNum++;
                if(spriteAttackNum >= attackImageAmount) {
                    spriteAttackNum = 0;
                }
                getImage();
                spriteAttackCounter = 0;
            }
        } else {
            super.updateAnimation();
        }
    }
    
    @Override
    public void update(){
        collisionOn = gp.getCChecker().checkCollision(this);
        if(!collisionOn) {
            x -= speed;
        } else if(!invincibleFrame){
            gp.getPlayer().changeHealth(-damage);
            setInvincibility();
        }
        if (invincibleFrame && System.currentTimeMillis() - invincibleTime > INVINCIBLE_DURATION) {
            invincibleFrame = false;
        }
        updateAnimation();
    }
        
    private double getSpeedFactor(int difficulty) {
        return switch(difficulty)  {
            case 0 -> 0.10;
            case 1 -> 0.15;
            case 2 -> 0.20;
            default -> 0.00;
        };
    }
    
    protected void setSpeed(int level) {
        speedFactor = getSpeedFactor(gp.difficulty);
        this.speed = baseSpeed + (int)(baseSpeed * level * speedFactor);
        System.out.println(speed + " " + level);
    }
    
    private void setInvincibility() {
        invincibleFrame = true;
        invincibleTime = System.currentTimeMillis();
    }
    
    public void setToFreezeImage() {
        image = freezeImage;
    }
}
