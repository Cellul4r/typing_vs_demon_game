package event;

import entity.*;
import game_2d.GamePanel;
import sound.SoundManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cellul4r
 */
public class Wave {
    
    private static final int WORD_OBJECT_DEFAULT = 7;
    private static final int WORD_OBEJCT_TIME_LIMIT = 30;
    private static final int WAVE_TIME_LIMIT = 120;
    private final GamePanel gp;
    private final double enemyFactor;
    private final ArrayList<WordObject>[] wordObjectList;
    private int level;
    private int enemyAmount;
    private int enemySpawn;
    private int enemyTick = 0;
    private int waveTick = 0;
    private boolean isFreeze = false;
    private int freezeTime = 0;
    
    public Wave(GamePanel gp) {
        this.gp = gp;
        this.wordObjectList = new ArrayList[GamePanel.GAME_ROW];
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            wordObjectList[i] = new ArrayList();
        }
        
        enemyFactor = switch (gp.difficulty) {
            case GamePanel.EASY -> 0.25;
            case GamePanel.MEDIUM -> 0.50;
            case GamePanel.HARD -> 0.65;
            default -> 0.00;
        };
    }
    
    public boolean checkPlayerWord(int playerRow, String word) {
        for(WordObject wordObject : wordObjectList[playerRow]) {
            if(wordObject.getWord().equals(word)) {
                gp.getSoundM().play(SoundManager.GUN_SOUND);
                if(wordObject instanceof Item item) {
                    gp.getPlayer().setItem(item);
                } else {
                    gp.getSoundM().play(SoundManager.ENEMY_DEAD); // change Number later
                }
                wordObjectList[playerRow].remove(wordObject);
                return true;
            }
        }
        gp.getSoundM().play(SoundManager.WRONG_TYPED);
        return false;
    }
        
    private void createWave() {
        enemyAmount = WORD_OBJECT_DEFAULT + (int)(WORD_OBJECT_DEFAULT*level*enemyFactor);
        enemySpawn = 0;
        level++;
        gp.getSoundM().play(SoundManager.ENEMY_SOUND);
    }
    
    private void randomWordObject() {
        Random rm = new Random();
        int row = rm.nextInt(GamePanel.GAME_ROW);
        // 5% item 95% Enemy
        int chance = rm.nextInt(100);
        WordObject wordObject;
        if(chance < 95) {
            chance = rm.nextInt(100);
            if(chance < 50) {
                wordObject = new EnemySquid(gp, row, level);
            } else {
                wordObject = new EnemyStick(gp, row, level);
            }
            enemySpawn++;
        } else {
            chance = rm.nextInt(100);
            if(chance <= 33) {
                wordObject = new ItemHealer(gp, row);
            } else if(chance <= 66){
                wordObject = new ItemBomb(gp, row);
            } else {
                wordObject = new ItemFreezer(gp, row);
            }
            enemyAmount--;
        }
        wordObjectList[row].add(0,wordObject);
    }
    
    public void update() {
        // check if the player clear that wave
        if(isWaveEmpty()) {
            gp.getSoundM().stop(SoundManager.ENEMY_SOUND);
            if(level != 0) {
                gp.getUiM().setShowWaveCompletedMessage(true);
                // draw Summary how many Time has player played and show the score.
            }
            if(waveTick == WAVE_TIME_LIMIT) {
                if(level == 7) {
                    gp.gameState = GamePanel.GAME_OVER_STATE;
                }
                createWave();
                gp.getUiM().setShowWaveCompletedMessage(false);
                waveTick = 0;
            } else {
                waveTick++;
            }
        }
        
        // chack if freeze item is being used right now or not
        if(isFreeze) {
            if(freezeTime == 0) {
                // firstTime for freeze
                for(int i = 0; i < GamePanel.GAME_ROW; i++) {
                    for(WordObject wordObject : wordObjectList[i]) {
                        if(wordObject instanceof Enemy enemy) {
                            enemy.setToFreezeImage();
                        }
                    }
                }
            }
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
            if(enemyTick == WORD_OBEJCT_TIME_LIMIT) {
                randomWordObject();
                enemyTick = 0;
            } else {
                enemyTick++;
            }
        }
        // update enemy movements
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            for(WordObject wordObject : wordObjectList[i]) {
                wordObject.update();
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        for(int i = 0; i < GamePanel.GAME_ROW; i++) {
            for(WordObject wordObject : wordObjectList[i]) {
                wordObject.draw(g2);
            }
        }
    }
    
    public void deleteRowWave(int row) {
        ArrayList<WordObject> newWordObjectList = new ArrayList<>();
        boolean hasEnemy = false;
        for(WordObject wordObject : wordObjectList[row]) {
            if(wordObject instanceof Item) {
                newWordObjectList.add(0, wordObject);
            } else {
                hasEnemy = true;
            }
        }
        if(hasEnemy) {
            gp.getSoundM().play(SoundManager.ENEMY_DEAD);
        }
        wordObjectList[row] = newWordObjectList;
    }
    
    private boolean isWaveEmpty() {
        boolean empty = true;
        for(int i = 0; i < GamePanel.GAME_ROW && empty; i++) {
            for(WordObject wordObject : wordObjectList[i]) {
                if(wordObject instanceof Enemy) {
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
