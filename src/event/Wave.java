package event;

import entity.Enemy;
import entity.Entity;
import entity.Item;
import entity.ItemBomb;
import entity.ItemFreezer;
import entity.ItemHealer;
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
    
    private static final int ENTITY_DAFAULT = 5;
    private static final int ENTITY_TIME_LIMIT = 53;
    private static final int WAVE_TIME_LIMIT = 120;
    private final GamePanel gp;
    private final double enemyFactor;
    private final ArrayList<Entity>[] entityList;
    private int level;
    private int enemyAmount;
    private int enemySpawn;
    private int enemyTick = 0;
    private int waveTick = 0;
    private boolean isFreeze = false;
    private int freezeTime = 0;
    
    public Wave(GamePanel gp) {
        this.gp = gp;
        this.entityList = new ArrayList[GamePanel.GAME_ROW];
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            entityList[i] = new ArrayList();
        }
        
        enemyFactor = switch (gp.difficulty) {
            case GamePanel.EASY -> 0.25;
            case GamePanel.MEDIUM -> 0.50;
            case GamePanel.HARD -> 0.65;
            default -> 0.00;
        };
    }
    
    public boolean checkPlayerWord(int playerRow, String word) {
        for(Entity entity : entityList[playerRow]) {
            if(((Enemy)entity).getWord().equals(word)) {
                if(entity instanceof Item item) {
                    gp.getPlayer().setItem(item);
                } else {
                    gp.getSoundM().playSoundEffect(1); // change Number later
                }
                entityList[playerRow].remove(entity);
                return true;
            }
        }
        return false;
    }
        
    private void createWave() {
        enemyAmount = ENTITY_DAFAULT + (int)(ENTITY_DAFAULT*level*enemyFactor);
        enemySpawn = 0;
        System.out.println(level + " " + enemyAmount);
        level++;
        gp.getSoundM().playSoundEffect(SoundManager.ENEMY_SOUND);
    }
    
    private void randomEntity() {
        Random rm = new Random();
        int row = rm.nextInt(GamePanel.GAME_ROW);
        // 10% item 90% Enemy
        int chance = rm.nextInt(100);
        Entity entity;
        if(chance < 90) {
            entity = new Enemy(gp, row, level);
            enemySpawn++;
        } else {
            chance = rm.nextInt(100);
            if(chance <= 33) {
                entity = new ItemHealer(gp, row);
            } else if(chance <= 66){
                entity = new ItemBomb(gp, row);
            } else {
                entity = new ItemFreezer(gp, row);
            }
            enemyAmount--;
        }
        entityList[row].add(0,entity);
    }
    
    public void update() {
        // check if the player clear that wave
        if(isWaveEmpty()) {
            gp.getSoundM().stopSoundEffect(SoundManager.ENEMY_SOUND);
            if(level != 0) {
                gp.getUiM().setShowWaveCompletedMessage(true);
                // draw Summary how many Time has player played and show the score.
            }
            if(waveTick == WAVE_TIME_LIMIT) {
                createWave();
                gp.getUiM().setShowWaveCompletedMessage(false);
                waveTick = 0;
            } else {
                waveTick++;
            }
        }
        
        if(isFreeze) {
            if(freezeTime == ItemFreezer.FREEZE_TIME) {
                isFreeze = false;
                freezeTime = 0;
            } else {
                freezeTime++;
            }
            return;
        }
        // spawn enemy
        if(enemySpawn < enemyAmount) {
            if(enemyTick == ENTITY_TIME_LIMIT) {
                randomEntity();
                enemyTick = 0;
            } else {
                enemyTick++;
            }
        }
        // update enemy movements
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            for(Entity entity : entityList[i]) {
                entity.update();
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            for(Entity entity : entityList[i]) {
                entity.draw(g2);
            }
        }
    }
    
    public void deleteRowWave(int row) {
        ArrayList<Entity> newEntityList = new ArrayList<>();
        boolean hasEnemy = false;
        for(Entity entity : entityList[row]) {
            if(entity instanceof Item) {
                newEntityList.add(0, entity);
            } else {
                hasEnemy = true;
            }
        }
        if(hasEnemy) {
            gp.getSoundM().playSoundEffect(1);
        }
        entityList[row] = newEntityList;
    }
    
    private boolean isWaveEmpty() {
        boolean empty = true;
        for(int i = 0; i < GamePanel.GAME_ROW && empty; i++) {
            for(Entity entity : entityList[i]) {
                if(entity instanceof Enemy) {
                    empty = false;
                    break;
                }
            }
        }
        return (empty && enemySpawn == enemyAmount);
    }
    
    public void setFreezeStatus(boolean freeze) { isFreeze = freeze; }
    
    public int getLevel() { return this.level;}
}
