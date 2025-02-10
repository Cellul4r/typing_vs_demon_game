/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game_2d.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author HP
 */
public class Enemy extends Entity{
    private static int count = 0;
    Random rand = new Random();
    
    public BufferedImage image;
    public String name;
    protected int obj_x, obj_y;
    private int obj_speed = 7;
    
    public Enemy(GamePanel gp){
        super(gp);
    }
    
    public void setEnemies(){
        if(isAvailable()){
            int row = rand.nextInt(5); // Generates a number between 0 and 4
            switch(row){
                case 0:
                    gp.obj[count] = new Demon1(gp);
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(0), count);
                    break;
                case 1:
                    gp.obj[count] = new Demon1(gp);
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(1), count);
                    break;
                case 2:
                    gp.obj[count] = new Demon1(gp);
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(2), count);
                    break;
                case 3:
                    gp.obj[count] = new Demon1(gp);
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(3), count);
                    break;
                case 4:
                    gp.obj[count] = new Demon1(gp);
                    gp.obj[count].setInitialPosition(gp.tileSize * 20, gp.getChannelY(4), count);
                    break;
            }
            count = (count+1)%gp.enemies_number;
        }
    }
    private boolean isAvailable(){
        int delay = rand.nextInt(14); //Delay for enemies to pop up, can be used to up difficulty
        for(int i=0;i<gp.enemies_number;i++){
            if(gp.obj[i] == null && delay == 4)
                return true;
        }
        return false;
    }
    
    public void update(){
        obj_x -= obj_speed;
    }
    public void draw(Graphics2D g2, GamePanel gp){
        if(obj_x >= 3 * gp.tileSize){ //when obj_x have yet to passes the finish line
            g2.drawImage(image, obj_x, obj_y, gp.tileSize, gp.tileSize, null);
        }else{
            gp.obj[count] = null;
        }
    }

    private void setInitialPosition(int x, int y, int count) {
        this.obj_x = x;
        this.obj_y = y;
        this.count = count;
    }
}
