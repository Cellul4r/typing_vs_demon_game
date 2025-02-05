/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    private int originalTileSize = 16; //Pixel Size of Characters
    private int scale = 3; //Scale of Pixel Size
    
    public int tileSize = originalTileSize * scale; //Real Size of Characters
    
    private int maxScreenCol = 20;
    private int maxScreenRow = 16;
    private int screenWidth = tileSize * maxScreenCol;
    private int screenHeight = tileSize * maxScreenRow;
    
    final int player_FPS = 12;
    final int FPS = 60;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //Running game loops
    Player player = new Player(this, keyH);
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); //so the game can 'focus' on receiving key
    }
    
    
    
    public void startGameThread(){
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/player_FPS; //60 FPS == draw the screen every 0.016 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while(gameThread!=null){
            
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; //How much time pass divided by drawInterval
            lastTime = currentTime;
            
            if(delta >= 1){ //update character's position every delta
                update();
                repaint();
                delta--;
            }
        }
    }
    
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        
        g2.dispose();
    }
}
