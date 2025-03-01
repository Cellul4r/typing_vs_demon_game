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
public class EnemySquid extends Enemy {
    
    public EnemySquid(GamePanel gp, int channel, int level) {
        super(gp, channel, level);
        this.damage = 1;
        this.baseSpeed = 2;
        this.maxSpeed = 4;
        setSpeed(level);
    }
    
    @Override
    protected void loadImage() {
        spriteTime = GamePanel.FPS / 4;
        imageAmount = 8;
        images = new BufferedImage[imageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_1.png"));
            images[1] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_2.png"));
            images[2] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_3.png"));
            images[3] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_4.png"));
            images[4] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_5.png"));
            images[5] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_6.png"));
            images[6] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_7.png"));
            images[7] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_8.png"));
            freezeImage = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy1_freeze.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

}
