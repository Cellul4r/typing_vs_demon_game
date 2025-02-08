/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.OBJ_Demon;



/**
 *
 * @author HP
 */
public class AssetSetter {
    GamePanel gp;
    private static int count = 0;
    Random rand = new Random();
    
    public AssetSetter(GamePanel gp){
        this.gp=gp;
    }
    
    public void setObject(){
        //set new object here
        if(isAvailable()){
            int row = rand.nextInt(5); // Generates a number between 0 and 4
            switch(row){
                case 0:
                    gp.obj[count] = new OBJ_Demon();
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(0), count);
                    break;
                case 1:
                    gp.obj[count] = new OBJ_Demon();
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(1), count);
                    break;
                case 2:
                    gp.obj[count] = new OBJ_Demon();
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(2), count);
                    break;
                case 3:
                    gp.obj[count] = new OBJ_Demon();
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(3), count);
                    break;
                case 4:
                    gp.obj[count] = new OBJ_Demon();
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(4), count);
                    break;
            }
            count = (count+1)%gp.enemies_number;
        }
        
        
    }
    private boolean isAvailable(){
        int delay = rand.nextInt(14); //Placeholder Delay for enemies to pop up
        for(int i=0;i<gp.enemies_number;i++){
            if(gp.obj[i] == null && delay == 4)
                return true;
        }
        return false;
    }
}
