/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class Enemy extends Entity{
    
    public BufferedImage image;
    protected String word;
    private Font font;
    
    private String[] dictionary = {"Let", "There", "Be", "Light"};
    
    public Enemy(GamePanel gp, int channel){
        super(gp);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/item_res/Mario.png"));
        } catch (IOException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
        }
        Random rand = new Random();
        this.channel = channel;
        this.x = gp.tileSize * 20;
        this.y = gp.getChannelY(channel);
        this.direction = "left";
        this.word = dictionary[rand.nextInt(4)];
        this.speed = 2;
        this.font = new Font("Times New Roman", Font.BOLD, 16);
    }
    
    @Override
    public void update(){
        
        collisionOn = gp.getCChecker().checkCollision(this);
        if(!collisionOn) {
            x -= speed;
        }
    }
    
    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        g2.setFont(font);
        g2.drawString(word, x + word.length() * 2, y - 10);
    }
    
    public void setWord(int index){
        this.word = dictionary[index];
    }
    
    public String getWord(){
        return word.toUpperCase();
    }
    
    public int getYs(){
        return y;
    }
   
}
