/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import entity.Entity;
import java.awt.Rectangle;

/**
 *
 * @author cellul4r
 */
public class CollisionChecker {
    
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    public boolean checkCollision(Entity entity) {
        
        Rectangle solidArea = entity.getSolidArea();
        int entityLeftX = entity.getX() - solidArea.x;
        int entityRightX = entity.getX() + solidArea.x;
        int entityTopY = entity.getY() - solidArea.y;
        int entityBottomY = entity.getY() + solidArea.y;
        
        int entityLeftCol = entityLeftX / gp.TILE_SIZE;
        int entityRightCol = entityRightX / gp.TILE_SIZE;
        int entityTopRow = entityTopY / gp.TILE_SIZE;
        int entityBottomRow = entityBottomY / gp.TILE_SIZE;

        int tileNum1, tileNum2;
        
        switch(entity.getDirection()) {
            case "left" -> {
                entityLeftCol = (int)(entityLeftX - entity.getSpeed()) / gp.TILE_SIZE;
                tileNum1 = gp.getTileM().getMapTileNum(entityLeftCol, entityTopRow);
                tileNum2 = gp.getTileM().getMapTileNum(entityLeftCol, entityBottomRow);
            }
            case "right" -> {
                entityRightCol = (int)(entityRightX + entity.getSpeed()) / gp.TILE_SIZE;
                tileNum1 = gp.getTileM().getMapTileNum(entityRightCol, entityTopRow);
                tileNum2 = gp.getTileM().getMapTileNum(entityRightCol, entityBottomRow);
            }
            case "up" -> {
                entityTopRow = (int)(entityTopY - entity.getSpeed()) / gp.TILE_SIZE;
                tileNum1 = gp.getTileM().getMapTileNum(entityLeftCol, entityTopRow);
                tileNum2 = gp.getTileM().getMapTileNum(entityRightCol, entityTopRow);
            }
            case "down" -> {
                entityBottomRow = (int)(entityBottomY + entity.getSpeed()) / gp.TILE_SIZE;
                tileNum1 = gp.getTileM().getMapTileNum(entityLeftCol, entityBottomRow);
                tileNum2 = gp.getTileM().getMapTileNum(entityRightCol, entityBottomRow);
            }
            default -> {
                tileNum1 = 0;
                tileNum2 = 0;
            }
        }
        
        return (gp.getTileM().getTile(tileNum1).getCollision() || 
                gp.getTileM().getTile(tileNum2).getCollision());
    }
}
