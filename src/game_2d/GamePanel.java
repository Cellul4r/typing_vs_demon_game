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
import object.SuperObject;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    
    private final int originalTileSize = 16; //Pixel Size of Characters
    private final int scale = 3; //Scale of Pixel Size
    
    public final int tileSize = originalTileSize * scale; //Real Size of Characters
    
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    
    public final int gameRow = 5;
    private final int gameScale = 2;
    private final int firstChannelY = 3 * tileSize;
    public final int channelSpacing = gameScale * tileSize;
    
//    public static final int PLAYER_FPS = 12;
    private final int FPS = 12;
    
    // channel Row channel 1 at ? y pixels
    private int channelRow[];
    private Thread gameThread; //Running game loops
    private Player player;
    public AssetSetter aSetter = new AssetSetter(this);
    
    public int enemies_number = 10;
    public SuperObject obj[] = new SuperObject[enemies_number];
    
    public GamePanel(){
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
        aSetter.setObject();
    }
    
    public int getChannelY(int row) {
        return this.channelRow[row];
    }
    
    public void startGameThread(){
        
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
        for(int i=0; i<obj.length;i++){
            if(obj[i] != null){
                obj[i].update();
            }
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        for(int i=0; i<obj.length;i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);
        aSetter.setObject();
        
        g2.dispose();
    }
    
}
