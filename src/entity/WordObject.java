package entity;

import game_2d.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import word_generator.WordGenerator;

/**
 *
 * @author HP
 */
public class WordObject extends EnemyStick {
    
    public static final Font FONT = new Font("Times New Roman", Font.BOLD, 16);
    protected String word;
    
    public WordObject(GamePanel gp, int channel) {
        this(gp, 0, channel);
    }
    
    public WordObject(GamePanel gp, int x, int channel) {
        this(gp, x, 0, channel);
    }
    
    public WordObject(GamePanel gp, int x,int speed, int channel) {
        super(gp,x,speed,channel);
        this.word = randomWord();
    }
    
    @Override
    protected void getImage() {
        image = images[spriteNum];
    }
    
    @Override
    public void update() {};
    
    @Override
    public void draw(Graphics2D g2){
        // draw Enemy
        g2.drawImage(image, x, y, 5 * GamePanel.TILE_SIZE / 4, 5 * GamePanel.TILE_SIZE / 4, null);
        
        // draw Enemy's Word
        g2.setColor(Color.WHITE);
        g2.fillRect(x + (GamePanel.TILE_SIZE - (g2.getFontMetrics().stringWidth(word)))/2 - 10, y - 25, g2.getFontMetrics().stringWidth(word) + 20, g2.getFontMetrics().getHeight());
        
        g2.setFont(FONT);
        g2.setColor(Color.BLACK);
        g2.drawRect(x + (GamePanel.TILE_SIZE - (g2.getFontMetrics().stringWidth(word)))/2 - 10, y - 25, g2.getFontMetrics().stringWidth(word) + 20, g2.getFontMetrics().getHeight());
        g2.drawString(word, x + (GamePanel.TILE_SIZE - (g2.getFontMetrics().stringWidth(word)))/2, y - 10);
    }
    
    public String randomWord() {
        return WordGenerator.randomWord(gp.difficulty);
    }
    
    public String getWord(){ return word.toUpperCase();}
    
    public int getYs(){ return y;}

    @Override
    public void loadImage() {}
    
}
