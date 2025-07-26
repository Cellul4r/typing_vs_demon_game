package entity;

import game_2d.GamePanel;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 *
 * @author cellul4r
 */
public abstract class Item extends WordObject {
    
    public Item(GamePanel gp, int channel) {
        super(gp, channel);
        Random rm = new Random();
        x = rm.nextInt(GamePanel.MAX_SCREEN_COL / 2     *GamePanel.TILE_SIZE, (GamePanel.MAX_SCREEN_COL - 2) * GamePanel.TILE_SIZE);
    }
    
    public abstract void useItem();
    
    public BufferedImage getItemImage() {
        return images[0];
    }
}
