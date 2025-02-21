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
    private Sound sound = new Sound();
    private Thread gameThread; //Running game loops
    private Player player;
    private final KeyHandler keyH = new KeyHandler(this);
    private final TileManager tileM = new TileManager(this);
    private final CollisionChecker cChecker = new CollisionChecker(this);
    private final Wave wave = new Wave(this);
    
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public int commandNum = 0;
    public int titleScreenState = 0;
    private boolean musicPlayed = false;
    
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
        gameState = titleState;
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
        
        if(gameState == titleState){
            drawTitleScreen(g2);
            if(titleScreenState == 1 && !musicPlayed){
                playMusic(0);
            }
            if(titleScreenState == 0 && musicPlayed){
                stopMusic();                
            }
        }
        else{
            tileM.draw(g2);
            player.draw(g2);
            wave.draw(g2);
        
            if(gameState == pauseState){
                drawPauseScreen(g2);
                stopMusic();
            }
            if(player.getHealth() == 0){
                drawGameOver(g2);
                gameState = gameOverState;
            }
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
    
    private void drawTitleScreen(Graphics2D g2){
        
        if(titleScreenState == 0){
            String text = "Typing VS Demon";
            int x;
        
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            font = new Font("Times New Roman", Font.BOLD, 80);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
        
            int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //Centered Text
        
            x = getXforCenteredText(text, g2);
            int y = TILE_SIZE * 3;
        
            g2.drawString(text, x, y);
        
            font = new Font("Times New Roman", Font.BOLD, 30);
            g2.setFont(font);
        
            text = "NEW GAME";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 8;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - TILE_SIZE, y);
            }
        
            text = "QUIT";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 9;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - TILE_SIZE, y);
            }
        }
        
        else if(titleScreenState == 1){
            String text = "Controls";
            int x;
        
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            font = new Font("Times New Roman", Font.BOLD, 80);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
        
            int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //Centered Text
        
            x = getXforCenteredText(text, g2);
            int y = TILE_SIZE * 2;
        
            g2.drawString(text, x, y);
        
            font = new Font("Times New Roman", Font.BOLD, 30);
            g2.setFont(font);
        
            text = "SHOOT : ENTER / SPACE";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 5;
            g2.drawString(text, x, y);
        
            text = "MOVE UP : ^";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 6;
            g2.drawString(text, x, y);
            
            
            text = "MOVE DOWN : v";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 7;
            g2.drawString(text, x, y);
            
            
            text = "Type words to shoot enemies according to your row.";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 8;
            g2.drawString(text, x, y);
            
            text = "CONTINUE";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 10;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - TILE_SIZE, y);
            }
            
            text = "EXIT";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 11;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - TILE_SIZE, y);
            }
        }
        
        else if(titleScreenState == 2){
            String text = "Choose Difficulty";
            int x;
        
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            font = new Font("Times New Roman", Font.BOLD, 80);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
        
            int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //Centered Text
        
            x = getXforCenteredText(text, g2);
            int y = TILE_SIZE * 3;
        
            g2.drawString(text, x, y);
        
            font = new Font("Times New Roman", Font.BOLD, 30);
            g2.setFont(font);
        
            text = "EASY";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 8;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - TILE_SIZE, y);
            }
        
            text = "MEDIUM";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 9;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - TILE_SIZE, y);
            }
            
            text = "HARD";
            x = getXforCenteredText(text, g2);
            y = TILE_SIZE * 10;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x - TILE_SIZE, y);
            }
        }
        
        
        g2.dispose();
    }
    
    private int getXforCenteredText(String text, Graphics2D g2){
        return SCREEN_WIDTH/2 - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
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
    
    private void drawGameOver(Graphics2D g2){
        String text = "GAME OVER";
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
        
        font = new Font("Times New Roman", Font.BOLD, 30);
        g2.setFont(font);
        
        text = "RESTART";
        x = TILE_SIZE * 5;
        y = TILE_SIZE * 10;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - TILE_SIZE, y);
        }
        
        text = "MAIN MENU";
        x = TILE_SIZE * 10;
        y = TILE_SIZE * 10;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - TILE_SIZE, y);
        }
        
        g2.dispose();
        
    }
    
    public void restart(){
        //Later
    }
}
