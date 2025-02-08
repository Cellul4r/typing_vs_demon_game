/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class OBJ_Demon extends SuperObject{
    public OBJ_Demon(){
        
        name = "the fucking bomb";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/item_res/Mario.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

