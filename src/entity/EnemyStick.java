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
        this.damage = 2;
        this.baseSpeed = 1;
        this.maxSpeed = 4;
        setSpeed(level);
    }
    
    @Override
    protected void loadImage() {
        spriteTime = GamePanel.FPS / 6;
        imageAmount = 6;
        images = new BufferedImage[imageAmount];
        spriteAttackTime = GamePanel.FPS  / 8;
        attackImageAmount = 8;      
        attackImages = new BufferedImage[attackImageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_1.png"));
            images[1] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_2.png"));
            images[2] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_3.png"));
            images[3] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_4.png"));
            images[4] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_5.png"));
            images[5] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_6.png"));
            attackImages[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_1.png"));
            attackImages[1] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_2.png"));
            attackImages[2] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_3.png"));
            attackImages[3] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_4.png"));
            attackImages[4] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_5.png"));
            attackImages[5] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_6.png"));
            attackImages[6] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_7.png"));
            attackImages[7] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemyAttack2_8.png"));
            freezeImage = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy2_freeze.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
