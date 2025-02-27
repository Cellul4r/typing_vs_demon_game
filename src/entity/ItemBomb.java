package entity;

import game_2d.GamePanel;
import java.io.IOException;
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
    protected void updateAnimation() {
        // item has no animation
    }

    @Override
    protected void getImage() {
        try {
            image = ImageIO.read(Enemy.class.getResourceAsStream("/resource/player_res/FireMage.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    @Override
    public void update() {
        // item does not have to move
    }       
}
