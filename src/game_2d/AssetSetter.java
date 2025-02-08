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
        
        gp.obj[0]= new OBJ_Bomb();
        //I'm too tired to determine column of the game right now.
    }
}
