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
    
    private static final int ORIGINAL_TILE_SIZE = 16; //Pixel Size of Characters
    private static final int SCALE = 3; //Scale of Pixel Size
    
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //Real Size of Characters
    
    public static final int MAX_SCREEN_COL = 20;
    public static final int MAX_SCREEN_ROW = 14;
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    public static final int GAME_ROW = 5;
    public static final int GAME_SCALE = 2;
    public static final int FIRST_CHANNEL_Y = 3 * TILE_SIZE;
    public static final int CHANNEL_SPACING = GAME_ROW * GAME_SCALE;
    
//    public static final int PLAYER_FPS = 12;
    private static final int FPS = 30;
    
    // channel Row channel 1 at ? y pixels
    public static int channelRow[];
    KeyHandler keyH;
    Thread gameThread; //Running game loops
    Player player;
    
    public GamePanel(){
        setUpGame();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); //so the game can 'focus' on receiving key
    }
    
    private void setUpGame() {
        channelRow = new int[GAME_ROW];
        for(int i = 0, j = FIRST_CHANNEL_Y; i < GAME_ROW; i++, j += CHANNEL_SPACING) {
            channelRow[i] = j;
        }
        keyH = new KeyHandler();
        player = new Player(this, keyH);
    }
    
    public void startGameThread(){
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/ FPS; //60 FPS == draw the screen every 0.016 seconds
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
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        
        g2.dispose();
    }
    
}
