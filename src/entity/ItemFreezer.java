package entity;

import game_2d.GamePanel;
import sound.SoundManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cellul4r
 */
public class ItemFreezer extends Item {
    
    public static int FREEZE_TIME = 150;
    
    public ItemFreezer(GamePanel gp, int channel) {
        super(gp, channel);
    }

    @Override
    public void useItem() {
        gp.getWave().setFreezeStatus(true);
        gp.getSoundM().play(SoundManager.FREEZE_SOUND);
    }

    @Override
    protected void loadImage() {
        spriteTime = 0;
        imageAmount = 1;
        images = new BufferedImage[imageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/item_res/item_freezer.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
