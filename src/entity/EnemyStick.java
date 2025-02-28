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
public class EnemyStick extends Enemy {
    
    public EnemyStick(GamePanel gp, int channel, int level) {
        super(gp, channel, level);
        this.damage = 5;
        this.baseSpeed = 1;
        this.maxSpeed = 4;
        setSpeed(level);
    }
    
    @Override
    public void loadImage() {
        spriteTime = GamePanel.FPS / 4;
        imageAmount = 1;
        images = new BufferedImage[imageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2.png"));
            freezeImage = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_freeze.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
