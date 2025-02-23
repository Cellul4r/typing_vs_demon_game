package event;

import entity.Enemy;
import entity.Entity;
import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cellul4r
 */
public class Wave {
    
    private static final int ENEMY_DEFAULT = 5;
    private int level;
    private int enemyAmount;
    private int enemySpawn;
    private final GamePanel gp;
    private final double enemyFactor;
    private final ArrayList<Entity>[] enemyList;
    private int enemyTick = 0;
    private int enemyLimit = 40;
    
    public Wave(GamePanel gp) {
        this.gp = gp;
        this.enemyList = new ArrayList[GamePanel.GAME_ROW];
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            enemyList[i] = new ArrayList();
        }
        
        enemyFactor = switch (gp.difficulty) {
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
    }
    
    private void randomEntity() {
        Random rm = new Random();
        int row = rm.nextInt(GamePanel.GAME_ROW);
        Enemy enemy = new Enemy(gp, row, level);
        enemyList[row].add(enemy);
        enemySpawn++;
    }
    
    public void update() {
        // check if the player clear that wave
        if(isWaveEmpty()) {
            createWave();
        }
        // spawn enemy
        if(enemySpawn < enemyAmount) {
            if(enemyTick == enemyLimit) {
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
