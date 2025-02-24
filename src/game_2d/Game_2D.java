package game_2d;

import entity.Enemy;
import javax.swing.JFrame;
import word_generator.WordGenerator;

//To push, you first commit Coffee in Project, then push
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
        
        loadGame();
        gamepanel.startGameThread();
    }
        
    public static void loadGame() {
        Enemy.loadImage();
        WordGenerator.loadWordList();
    }
}

