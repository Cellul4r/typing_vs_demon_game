package entity;

import game_2d.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author cellul4r
 */
public class ItemBomb extends Item {
     
    public ItemBomb(GamePanel gp, int channel) {
        super(gp, channel);
    }
    
    @Override
    public void useItem() {
        System.out.println("Boom!");
        gp.getWave().deleteRowWave(gp.getPlayer().getChannel());
    }

    @Override
    protected void loadImage() {
        spriteTime = 0;
        imageAmount = 1;
        images = new BufferedImage[imageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/item_res/item_bomb.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
     
}
