package entity;

import game_2d.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnemyWitch extends Enemy {

    public EnemyWitch(GamePanel gp, int channel, int level) {
        super(gp, channel, level);
        this.damage = 10;
        this.speed = 3;
        this.maxSpeed = 10;
        setSpeed(level);
    }

    @Override
    protected void loadImage() {
        spriteTime = GamePanel.FPS / 4;
        imageAmount = 7;
        images = new BufferedImage[imageAmount];
        spriteAttackTime = GamePanel.FPS / 4;
        attackImageAmount = 7;
        attackImages = new BufferedImage[attackImageAmount];
        try {
            images[0] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_1.png"));
            images[1] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_2.png"));
            images[2] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_3.png"));
            images[3] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_4.png"));
            images[4] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_5.png"));
            images[5] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_6.png"));
            images[6] = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_7.png"));
            attackImages[0] = images[0];
            attackImages[1] = images[1];
            attackImages[2] = images[2];
            attackImages[3] = images[3];
            attackImages[4] = images[4];
            attackImages[5] = images[5];
            attackImages[6] = images[6];
            freezeImage = ImageIO.read(WordObject.class.getResourceAsStream("/resource/enemy_res/enemy3_freeze.png"));
        } catch (IOException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
