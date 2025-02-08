/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import game_2d.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class OBJ_Bomb extends SuperObject{
    public OBJ_Bomb(){
        
        name = "the fucking bomb";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/item_res/penguin.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}    


