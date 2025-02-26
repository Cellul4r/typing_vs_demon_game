package entity;

import game_2d.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author cellul4r
 */
public abstract class Item extends Entity {
    
    private BufferedImage image;
    
    public Item(GamePanel gp) {
        super(gp);
    }
    
    public abstract void useItem();
}
