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
    private final ArrayList<Entity>[] enemyList;
    
    public Wave(GamePanel gp) {
        this.gp = gp;
        this.enemyList = new ArrayList[gp.gameRow];
        for(int i = 0; i < gp.gameRow; i++) {
            enemyList[i] = new ArrayList();
        }
    }
    
    public boolean checkPlayerWord(int playerRow, String word) {
        for(Entity entity : enemyList[playerRow]) {
            if(((Enemy)entity).getWord().equals(word)) {
                enemyList[playerRow].remove(entity);
                enemyAmount--;
                return true;
            }
        }
        
        return false;
    }
    
    private void createWave() {
        level++;
        enemyAmount = 5;
        Random rm = new Random();
        for(int i = 0; i < enemyAmount; i++) {
            int row = rm.nextInt(gp.gameRow);
            Enemy enemy = new Enemy(gp, row);
            enemyList[row].add(enemy);
        }
    }
    
    public void update() {
        if(enemyAmount == 0) {
            createWave();
        }
        
        for(int i = 0; i < gp.gameRow; i++) {
            for(Entity entity : enemyList[i]) {
                entity.update();
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        for(int i = 0; i < gp.gameRow; i++) {
            for(Entity entity : enemyList[i]) {
                entity.draw(g2);
            }
        }
    }
}
