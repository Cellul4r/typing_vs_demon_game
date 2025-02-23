/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import event.CollisionChecker;
import event.KeyHandler;
import entity.Enemy;
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
import ui.UI;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    
    // default Settings for SCREEN
    private final int ORIGINAL_TILE_SIZE = 16; //Pixel Size of Characters
    private final int SCALE = 4; //Scale of Pixel Size
    
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //Real Size of Characters
    
    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREEN_ROW = 14;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    public final int GAME_ROW = 5;
    private final int GAME_SCALE = 2;
    private final int FIRST_CHANNEL_Y = 3 * TILE_SIZE;
    public final int CHANNEL_SPACING = GAME_SCALE * TILE_SIZE;
    
    public final int FPS = 30;
    
    // channel Row channel 1 at ? y pixels
    // Association Object
    private int channelRow[];
    public Sound sound = new Sound();
    private Thread gameThread; //Running game loops
    public Player player;
    private final KeyHandler keyH = new KeyHandler(this);
    private final TileManager tileM = new TileManager(this);
    private final CollisionChecker cChecker = new CollisionChecker(this);
    private final Wave wave = new Wave(this);
    private final UI ui = new UI(this);
    
    
    // Game State Settings
    public int gameState;
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int GAME_OVER_STATE = 3;
    public int commandNum = 0;
    public int titleScreenState = 0;
    public boolean musicPlayed = false;
    
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
        gameState = TITLE_STATE;
        for(int i = 0, j = FIRST_CHANNEL_Y; i < GAME_ROW; i++, j += CHANNEL_SPACING) {
            channelRow[i] = j;
        }
        gameState = TITLE_STATE;
        player = new Player(this, keyH);
    }
    
    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    // Game Loop : run -> update -> draw
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
        
        if(gameState == PLAY_STATE){
            player.update();
            wave.update();
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if(gameState == PLAY_STATE){
            tileM.draw(g2);
            player.draw(g2);
            wave.draw(g2);
        }
        ui.draw(g2);
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
    
    public void playMusic(int i){
        
        sound.setFile(i);
        sound.play();
        musicPlayed = true;
        sound.loop();
    }
    
    public void stopMusic(){
        
        sound.stop();
        musicPlayed = false;
    }
    
    public void playSoundEffect(int i){
        
        sound.setFile(i);
        sound.play();
    }
}
