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
public class ItemHealer extends Item {
    
    private static final int HEAL_VALUE = 20;
    
    public ItemHealer(GamePanel gp, int channel) {
        super(gp, channel);
    }
    
    @Override
    public void useItem() {
        gp.getPlayer().changeHealth(HEAL_VALUE);
        gp.getSoundM().play(SoundManager.HEALING_SOUND);
    }
    
    @Override
    protected void loadImage() {
        spriteTime = 0;
        imageAmount = 1;
        images = new BufferedImage[imageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/item_res/item_healer.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }   
}
