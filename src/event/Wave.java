package event;

import entity.Enemy;
import entity.Entity;
import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import sound.SoundManager;

/**
 *
 * @author cellul4r
 */
public class Wave {
    
    private static final int ENEMY_DEFAULT = 5;
    private static final int ENEMY_TIME_LIMIT = 53;
    private static final int WAVE_TIME_LIMIT = 120;
    private final GamePanel gp;
    private final double enemyFactor;
    private final ArrayList<Entity>[] enemyList;
    private int level;
    private int enemyAmount;
    private int enemySpawn;
    private int enemyTick = 0;
    private int waveTick = 0;
    
    public Wave(GamePanel gp) {
        this.gp = gp;
        this.enemyList = new ArrayList[GamePanel.GAME_ROW];
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            enemyList[i] = new ArrayList();
        }
        
        enemyFactor = switch (gp.difficulty) {
            // 0.25
            case GamePanel.EASY -> 0.25;
            case GamePanel.MEDIUM -> 0.50;
            case GamePanel.HARD -> 0.65;
            default -> 0.00;
        };
    }
    
    public boolean checkPlayerWord(int playerRow, String word) {
        for(Entity entity : enemyList[playerRow]) {
            if(((Enemy)entity).getWord().equals(word)) {
                enemyList[playerRow].remove(entity);
                gp.getSoundM().playSoundEffect(1); // change Number later
                return true;
            }
        }
        return false;
    }
        
    private void createWave() {
        enemyAmount = ENEMY_DEFAULT + (int)(ENEMY_DEFAULT*level*enemyFactor);
        enemySpawn = 0;
        System.out.println(level + " "+ enemyAmount);
        level++;
        gp.getSoundM().playSoundEffect(SoundManager.ENEMY_SOUND);
    }
    
    private void randomEntity() {
        Random rm = new Random();
        int row = rm.nextInt(GamePanel.GAME_ROW);
        Enemy enemy = new Enemy(gp, row, level);
        enemyList[row].add(0,enemy);
        enemySpawn++;
    }
    
    public void update() {
        // check if the player clear that wave
        if(isWaveEmpty()) {
            gp.getSoundM().stopSoundEffect(SoundManager.ENEMY_SOUND);
            if(level != 0) {
                // draw Summary how many Time has player played and show the score.
            }
            if(waveTick == WAVE_TIME_LIMIT) {
                createWave();
                waveTick = 0;
            } else {
                waveTick++;
            }
        }
        // spawn enemy
        if(enemySpawn < enemyAmount) {
            if(enemyTick == ENEMY_TIME_LIMIT) {
                randomEntity();
                enemyTick = 0;
            } else {
                enemyTick++;
            }
        }
        // update enemy movements
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            for(Entity entity : enemyList[i]) {
                entity.update();
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            for(Entity entity : enemyList[i]) {
                entity.draw(g2);
            }
        }
    }
    
    private boolean isWaveEmpty() {
        boolean empty = true;
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            if(!enemyList[i].isEmpty()) {
                empty = false;
                break;
            }
        }
        return (empty && enemySpawn == enemyAmount);
    }
    
    public int getLevel() { return this.level;}
}
