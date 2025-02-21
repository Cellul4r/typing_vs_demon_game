/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import event.KeyHandler;
import event.CollisionChecker;
import entity.Player;
import event.Wave;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;
import tile.TileManager;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    
    private final int ORIGINAL_TILE_SIZE = 16; //Pixel Size of Characters
    private final int SCALE = 3; //Scale of Pixel Size
    
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //Real Size of Characters
    
    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREEN_ROW = 14;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    public final int GAME_ROW = 5;
    private final int GAME_SCALE = 2;
    private final int FIRST_CHANNEL_Y = 3 * TILE_SIZE;
    public final int CHANNEL_SPACING = GAME_SCALE * TILE_SIZE;
    
    private final int FPS = 30;
    
    // channel Row channel 1 at ? y pixels
    private int channelRow[];
    private Thread gameThread; //Running game loops
    private Player player;
    private final KeyHandler keyH = new KeyHandler(this);
    private final TileManager tileM = new TileManager(this);
    private final CollisionChecker cChecker = new CollisionChecker(this);
    private final Wave wave = new Wave(this);
    
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    
    private Font font;
    
    public GamePanel() {
        setUpGame();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); //so the game can 'focus' on receiving key
    }
    
    private void setUpGame() {
        channelRow = new int[GAME_ROW];
        gameState = playState;
        for(int i = 0, j = FIRST_CHANNEL_Y; i < GAME_ROW; i++, j += CHANNEL_SPACING) {
            channelRow[i] = j;
        }
        
        player = new Player(this, keyH);
        
    }
    
    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; //60 FPS == draw the screen every 0.016 seconds
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
        
        if(gameState == playState){
            player.update();
            wave.update();
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        tileM.draw(g2);
        player.draw(g2);
        wave.draw(g2);
        
        if(gameState == pauseState){
            drawPauseScreen(g2);
        }
        
        g2.dispose();
    }
    
    public int getChannelY(int row) {
        return this.channelRow[row];
    }
    
    public TileManager getTileM() {
        return this.tileM;
    }
    
    public CollisionChecker getCChecker() {
        return this.cChecker;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public Wave getWave() {
        return this.wave;
    }
    
    private void drawPauseScreen(Graphics2D g2){
        
        String text = "PAUSED";
        int x;
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); //dim screen by 50%
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        font = new Font("Times New Roman", Font.BOLD, 80);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //Centered Text
        
        x = SCREEN_WIDTH/2 - length/2;
        int y = SCREEN_HEIGHT/2;
        
        g2.drawString(text, x, y);
        g2.dispose();
    }
}
