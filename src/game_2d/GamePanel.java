/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import tile.TileManager;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    
    private final int originalTileSize = 16; //Pixel Size of Characters
    private final int scale = 3; //Scale of Pixel Size
    
    public final int tileSize = originalTileSize * scale; //Real Size of Characters
    
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    
    public final int gameRow = 5;
    private final int gameScale = 2;
    private final int firstChannelY = 3 * tileSize;
    public final int channelSpacing = gameScale * tileSize;
    
    private final int FPS = 12;
    
    // channel Row channel 1 at ? y pixels
    private int channelRow[];
    private Thread gameThread; //Running game loops
    private Player player;
    private final TileManager tileM = new TileManager(this);
    private final CollisionChecker cChecker = new CollisionChecker(this);
    
    public int enemyAmount = 5; //How much enemies can appear at once on the screen
    public Entity[] enemyList = new Entity[enemyAmount];
    
    public GamePanel() {
        setUpGame();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(player.getKeyHandler());
        this.setFocusable(true); //so the game can 'focus' on receiving key
    }
    
    private void setUpGame() {
        channelRow = new int[gameRow];
        for(int i = 0, j = firstChannelY; i < gameRow; i++, j += channelSpacing) {
            channelRow[i] = j;
        }
        player = new Player(this);
        
        Random rand = new Random();
        for(int i = 0; i < enemyAmount; i++) {
            int row = rand.nextInt(5); // Generates a number between 0 and 4
            enemyList[i] = new Enemy(this, row);
        }
    }
    
    public int getChannelY(int row) {
        return this.channelRow[row];
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
        player.update();
        for(int i=0; i<enemyList.length;i++){
            if(enemyList[i] != null){
                enemyList[i].update();
            }
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        
        tileM.draw(g2);
        player.draw(g2);
        for(int i = 0; i< enemyList.length; i++){
            if(enemyList[i] != null){
                enemyList[i].draw(g2);
            }
        }
//        enemy.setEnemies(this);
        //aSetter.setObject();
        
        g2.dispose();
    }
    
    public TileManager getTileM() {
        return this.tileM;
    }
    
    public CollisionChecker getCChecker() {
        return this.cChecker;
    }
}
