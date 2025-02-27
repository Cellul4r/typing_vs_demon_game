package entity;

import game_2d.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    }

    @Override
    protected void updateAnimation() {
        // item has no animation
    }

    @Override
    protected void getImage() {
        try {
            image = ImageIO.read(Enemy.class.getResourceAsStream("/resource/player_res/test.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    @Override
    public void update() {
        // item does not have to move
    } 
    
}
