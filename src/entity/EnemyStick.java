package entity;

import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author HP
 */

public abstract class EnemyStick {
    
    protected int spriteTime;
    protected int imageAmount;
    protected BufferedImage[] images;
    protected BufferedImage image;
    
    protected GamePanel gp;
    protected int x, y;
    protected int speed;
    protected String direction;
    protected int channel;
    protected int spriteCounter = 0;
    protected int spriteNum = 0;
    
    
    protected Rectangle solidArea;
    protected boolean collisionOn = false;
    
    public EnemyStick(GamePanel gp, int channel) {
        this(gp, 0, channel);
    }
    
    public EnemyStick(GamePanel gp, int x, int channel) {
        this(gp,x,0,channel);
    }
    
    public EnemyStick(GamePanel gp, int x, int speed, int channel) {
        this(gp,x,speed,"left",channel);
    }
    
    public EnemyStick(GamePanel gp, int x, int speed, String direction, int channel) {
        this.gp = gp;
        this.x = x;
        this.y = gp.getChannelY(channel);
        this.speed = speed;
        this.direction = direction;
        this.channel = channel;
        this.solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE / 2, GamePanel.TILE_SIZE / 2);
        loadImage();
        getImage();
    }
    
    protected void getImage() {
        if(images[spriteNum] == null) {
            System.out.println("Pic null " + getClass().getSimpleName());
//            return;
        }
        image = images[spriteNum];
    }
    
    protected void updateAnimation() {
        spriteCounter++;
        if(spriteCounter > spriteTime){
            spriteNum++;
            if(spriteNum >= imageAmount) {
                spriteNum = 0;
            }
            getImage();
            spriteCounter = 0;
        }
    }
    
    public abstract void loadImage();
    public abstract void update();
    public abstract void draw(Graphics2D g2);
    
    public int getX() { return this.x;}
    
    public int getY() { return this.y;}
    
    public int getSpeed() {return this.speed;}
    
    public String getDirection() {return this.direction;}
    
    public Rectangle getSolidArea() {return this.solidArea;}
    
    public int getChannel() {return this.channel;}
}
