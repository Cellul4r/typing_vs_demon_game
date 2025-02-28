package entity;

import game_2d.GamePanel;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 *
 * @author cellul4r
 */
public abstract class Item extends WordObject {
    
    protected static BufferedImage itemImage;
    
    public Item(GamePanel gp, int channel) {
        super(gp, channel);
        Random rm = new Random();
        x = rm.nextInt(5*GamePanel.TILE_SIZE, 18*GamePanel.TILE_SIZE);
    }
    
    public abstract void useItem();
    
}
