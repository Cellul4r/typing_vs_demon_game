/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import object.OBJ_Bomb;



/**
 *
 * @author HP
 */
public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp=gp;
    }
    
    public void setObject(){
        //set new object here
        gp.obj[0]= new OBJ_Bomb();
        gp.obj[0].setInitialPosition(gp.tileSize * 18);
        
    }
}
