/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class Demon1 extends Enemy{
    public Demon1(){
        name = "the fucking Mario";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/player_res/Mario.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
