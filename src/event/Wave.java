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
    
    GamePanel gp;
    private int level;
    private int enemyAmount;
    private int enemySpawn;
    private int enemyDefault = 5;
    private double enemyFactor;
    private final ArrayList<Entity>[] enemyList;
    private int enemyTick = 0;
    private int enemyLimit = 30;
    
    public Wave(GamePanel gp) {
        this.gp = gp;
        this.enemyList = new ArrayList[gp.GAME_ROW];
        for(int i = 0; i < gp.GAME_ROW; i++) {
            enemyList[i] = new ArrayList();
        }
        
        enemyFactor = switch (gp.difficulty) {
            case 0 -> 0.25;
            case 1 -> 0.50;
            case 2 -> 0.65;
            default -> 0.00;
        };
    }
    
    public boolean checkPlayerWord(int playerRow, String word) {
        for(Entity entity : enemyList[playerRow]) {
            if(((Enemy)entity).getWord().equals(word)) {
                enemyList[playerRow].remove(entity);
                gp.playSoundEffect(1);
                return true;
            }
        }
        
        return false;
    }
        
    private void createWave() {
        enemyAmount = enemyDefault + (int)(enemyDefault*level*enemyFactor);
        enemySpawn = 0;
        System.out.println(level + " "+ enemyAmount);
        level++;
    }
    
    private void randomEntity() {
        Random rm = new Random();
        int row = rm.nextInt(gp.GAME_ROW);
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
        for(int i = 0; i < gp.GAME_ROW; i++) {
            for(Entity entity : enemyList[i]) {
                entity.update();
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        
        for(int i = 0; i < gp.GAME_ROW; i++) {
            for(Entity entity : enemyList[i]) {
                entity.draw(g2);
            }
        }
    }
    
    private boolean isWaveEmpty() {
        boolean empty = true;
        for(int i = 0; i < gp.GAME_ROW; i++) {
            if(!enemyList[i].isEmpty()) {
                empty = false;
                break;
            }
        }
        return (empty && enemySpawn == enemyAmount);
    }
}
