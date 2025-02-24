package entity;

import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author HP
 */

public abstract class Entity {
    
    protected GamePanel gp;
    protected int x, y;
    protected int speed;
    protected String direction;
    protected int channel;
    
    protected Rectangle solidArea;
    protected boolean collisionOn = false;
    
    public Entity(GamePanel gp) {
        this(gp, 0);
    }
    
    public Entity(GamePanel gp, int channel) {
        this.gp = gp;
        this.channel = channel;
        this.solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE / 2, GamePanel.TILE_SIZE / 2);
    }
    
    protected abstract void updateAnimation();
    protected abstract void getImage();
    public abstract void update();
    public abstract void draw(Graphics2D g2);
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getSpeed() {
        return this.speed;
    }
    
    public String getDirection() {
        return this.direction;
    }
    
    public Rectangle getSolidArea() {
        return this.solidArea;
    }
}
