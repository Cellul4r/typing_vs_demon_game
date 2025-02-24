package entity;

import game_2d.GamePanel;

/**
 *
 * @author cellul4r
 */
public abstract class Item extends Entity {

    public Item(GamePanel gp) {
        super(gp);
    }
    
    public abstract void useItem();
}
