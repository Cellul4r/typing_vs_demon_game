package game_2d;

import word_generator.WordGenerator;

import javax.swing.*;

//To push, you first commit Coffee in Project, then push
/**
 *
 * @author HP
 */
public class Game_2D {

    public static JFrame window = new JFrame();
    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle("Typing Vs Demons");
        
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);
        window.pack(); //Size the window to be equal to this.setPreferredSize

        window.setLocationRelativeTo(null); //set window at the center of the screen
        window.setVisible(true);

        loadGame();
        gamepanel.setUpGame();
        gamepanel.startGameThread();
    }

    public static void loadGame() {
        WordGenerator.loadWordList();
    }
}

