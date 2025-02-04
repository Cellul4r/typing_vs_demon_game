/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_2d;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class GamePanel extends JPanel implements Runnable{
    private int originalTileSize = 16; //Pixel Size of Characters
    private int scale = 3; //Scale of Pixel Size
    
    private int tileSize = originalTileSize * scale; //Real Size of Characters
    
    private int maxScreenCol = 20;
    private int maxScreenRow = 15;
    private int screenWidth = tileSize * maxScreenCol;
    private int screenHeight = tileSize * maxScreenRow;
    
    Thread gameThread; //Running game loops
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
    }
    
    public void startGameThread(){
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
    }
}
