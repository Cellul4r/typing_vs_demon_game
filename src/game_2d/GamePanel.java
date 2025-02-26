package game_2d;

import entity.Enemy;
import sound.Sound;
import event.KeyHandler;
import event.CollisionChecker;
import entity.Player;
import event.Wave;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import sound.SoundManager;
import tile.TileManager;
import ui.UI;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    
    // default Settings for SCREEN
    private static final int ORIGINAL_TILE_SIZE = 16; //Pixel Size of Characters
    private static final int SCALE = 3; //Scale of Pixel Size
    
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //Real Size of Characters
    
    public static final int MAX_SCREEN_COL = 20;
    public static final int MAX_SCREEN_ROW = 14;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    // default Settings for gameplay
    public static final int GAME_ROW = 5;
    private static final int GAME_SCALE = 2;
    private static final int FIRST_CHANNEL_Y = 3 * TILE_SIZE;
    public static final int CHANNEL_SPACING = GAME_SCALE * TILE_SIZE;
    public static final int FPS = 30;
    
    // Game State Settings
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int GAME_OVER_STATE = 3;
    public static final int TITLE_MAIN = 0;
    public static final int TITLE_TUTORIAL = 1;
    public static final int TITLE_DIFFICULTY = 2;
    public int gameState;
    public int commandNum = 0;
    public int titleScreenState = 0;
    
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public int difficulty = 0;
    
    // Component
    private static int channelRow[];
    private Thread gameThread; //Running game loops
    private final KeyHandler keyH = new KeyHandler(this);
    private final TileManager tileM = new TileManager();
    private final SoundManager soundM = new SoundManager();
    private final CollisionChecker cChecker = new CollisionChecker(this);
    private final UI uiManager = new UI(this);
    
    private Player player;
    private Wave wave;
    
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
        for(int i = 0, j = FIRST_CHANNEL_Y; i < GAME_ROW; i++, j += CHANNEL_SPACING) {
            channelRow[i] = j;
        }
        
        gameState = TITLE_STATE;
        soundM.playMusic(SoundManager.TITLE_MUSIC);
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
            
            if(delta >= 1) { //update character's position every delta
                update();
                repaint();
                delta--;
            }
        }
        System.out.println("Run");
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
        uiManager.draw(g2);
        g2.dispose();
    }
    
    public void restartGame() {
        wave = new Wave(this);
        player = new Player(this, keyH);
    }
    
    public int getChannelY(int row) { return this.channelRow[row];}
    
    public TileManager getTileM() { return this.tileM;}
    
    public CollisionChecker getCChecker() { return this.cChecker;}
    
    public SoundManager getSoundM() {return this.soundM;}
    
    public Player getPlayer(){ return player;}
    
    public Wave getWave() { return this.wave;}  
}
