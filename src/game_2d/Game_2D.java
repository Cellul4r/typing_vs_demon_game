/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game_2d;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author HP
 */
public class Game_2D {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println("Hello World");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Typing Vs Demons");
        
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);
        
        window.pack(); //Size the window to be equal to this.setPreferredSize
        
        window.setLocationRelativeTo(null); //set window at the center of the screen
        window.setVisible(true);
    }
        
}

class GamePanel extends JPanel implements Runnable{
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