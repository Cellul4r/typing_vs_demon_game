package entity;

import game_2d.GamePanel;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import word_generator.WordGenerator;

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
    }

    @Override
    protected void updateAnimation() {
        // item has no animation
    }

    @Override
    protected void getImage() {
        try {
            image = ImageIO.read(Enemy.class.getResourceAsStream("/resource/player_res/penguin.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    @Override
    public void update() {
        // item does not have to move
    }       
}
