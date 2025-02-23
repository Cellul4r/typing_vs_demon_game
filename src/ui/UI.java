package ui;

import game_2d.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 *
 * @author cellul4r
 */
public class UI {
    
    private GamePanel gp;
    private Graphics2D g2;
    private final Font TimesNewRoman_80;
    private final Font TimesNewRoman_30;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        
        TimesNewRoman_80 = new Font("Times New Roman", Font.BOLD, 80);
        TimesNewRoman_30 = new Font("Times New Roman", Font.BOLD, 30);
    }
    
    public void draw(Graphics2D g2) {
        this.g2 = g2;
               
        switch(gp.gameState) {
            case GamePanel.TITLE_STATE:
                drawTitleScreen();
                break;
            // do something for play State
            // draw Hp of the player, Score of player
            case GamePanel.PLAY_STATE:
                drawPlayScreen();
                break;
            case GamePanel.PAUSE_STATE:
                drawPauseScreen();
                break;
            case GamePanel.GAME_OVER_STATE:
                drawGameOver();
                break;
            default:
                break;
        }
        
        g2.dispose();
    }
    
    private void drawTitleScreen(){
        
        switch(gp.titleScreenState) {
            case 0: // Main Title Menu
                {
                    drawMainMenu();
                    break;
                }
            case 1: // Tutorial
                {    
                    drawTutorialMenu();
                    break;
                }

            case 2: // Choose Difficulty
                {
                    drawDifficultySelectionMenu();
                    break;
                }
            default:
                break;
        }
        
    }
    
    private void drawPlayScreen() {
        // draw UserInput's Word of the player
        drawText(gp.player.getUserInput(), TimesNewRoman_30.deriveFont(40f), Color.white, 
                    gp.TILE_SIZE * 4, gp.SCREEN_HEIGHT - gp.TILE_SIZE);
        
        // draw Score of player UI
        String sScore = String.valueOf(gp.player.getScore());
        drawTopRightText(sScore, TimesNewRoman_30.deriveFont(40f), Color.white, gp.TILE_SIZE);

        // draw Health UI
        int defaultWidth = 5 * gp.TILE_SIZE;
        int heartWidth = (int)((double)gp.player.getHealth() / gp.player.getMaxHealth() * defaultWidth);
        int heartHeight = gp.TILE_SIZE / 3;
        
        g2.setColor(Color.green);
        g2.fillRoundRect(gp.TILE_SIZE, gp.TILE_SIZE / 2, heartWidth, heartHeight, 15, 15);
    }
    
    private void drawPauseScreen(){ // Pause Menu for the player

        // background Color
        setDimBackGround(Color.black);
        // draw Text "Pause"
        drawCenteredText("PAUSED", TimesNewRoman_80, Color.white, gp.SCREEN_HEIGHT / 2);
    }
    
    private void drawGameOver(){ // When player lose -> GameOver Show up
        
        // background Color
        setDimBackGround(Color.black);
        // draw Text "Pause"
        drawCenteredText("GAME OVER", TimesNewRoman_80, Color.white, gp.SCREEN_HEIGHT / 2);
        
        // selection menu for the player
        String texts[] = {"RESTART", "MAIN MENU"};
        drawCenteredText(texts, TimesNewRoman_30, Color.white, gp.TILE_SIZE * 10);
        
    }
    
    private void drawMainMenu() {
        // background color
        setBackground(Color.BLACK);
        // GameTitle Text
        String text = "Typing VS Demon";
        drawCenteredText(text, TimesNewRoman_80, Color.white, gp.TILE_SIZE * 3);

        // menu selection Text
        String[] texts = {"NEW GAME", "QUIT"};
        drawMenu(texts, TimesNewRoman_30, Color.white, gp.TILE_SIZE * 8);
    }
    
    private void drawTutorialMenu() {
        // background color
        setBackground(Color.BLACK);

        // Tutorial
        String text = "Controls";
        drawCenteredText(text, TimesNewRoman_80, Color.white, gp.TILE_SIZE * 2);

        // tutorial for the player
        String[] texts = {"SHOOT : ENTER / SPACE", "MOVE UP : ^", "MOVE DOWN : v", 
                            "Type words to shoot enemies according to your row."};
        drawCenteredText(texts, TimesNewRoman_30, Color.white, gp.TILE_SIZE * 5);

        // Menu select for the player to choose
        texts = new String[] {"CONTINUE", "EXIT"};
        drawMenu(texts, TimesNewRoman_30, Color.white, gp.TILE_SIZE * 10);
    }
    
    private void drawDifficultySelectionMenu() {
        // background color
        setBackground(Color.BLACK);

        String text = "Choose Difficulty";
        drawCenteredText(text, TimesNewRoman_80, Color.white, gp.TILE_SIZE * 3);

        // Selection Menu
        String[] texts = {"EASY", "MEDIUM", "HARD"};
        drawMenu(texts, TimesNewRoman_30, Color.white, gp.TILE_SIZE * 8);
    }
    
    private void drawText(String text, Font font, Color color, int x, int y) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }
    
    private void drawCenteredText(String text, Font font, Color color, int y) {
        g2.setFont(font);
        g2.setColor(color);
        int x = getXforCenteredText(text);
        g2.drawString(text, x, y);
    }
    
    private void drawTopRightText(String text, Font font, Color color, int y) {
        g2.setFont(font);
        g2.setColor(color);
        int x = getXTopRightText(text);
        g2.drawString(text, x - gp.TILE_SIZE, y);
    }
    
    private void drawCenteredText(String[] texts, Font font, Color color, int y) {
        
        for(int i = 0; i < texts.length; i++) {
            int newY = y + gp.TILE_SIZE * i;
            drawCenteredText(texts[i], font, color, newY);
        }
    }
    
    private void drawMenu(String[] texts, Font font, Color color, int y) {
        
        for(int i = 0; i < texts.length; i++) {
            int newY = y + gp.TILE_SIZE * i;
            drawCenteredText(texts[i], font, color, newY);
            
            int x = getXforCenteredText(texts[i]);
            if(gp.commandNum == i) {
                drawCursorMenu(x, newY);
            }
        }
    }
    
    private void drawCursorMenu(int x, int y) {
        g2.drawString(">", x - gp.TILE_SIZE, y);
    }
    
    private void setBackground(Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
    }
    
    private void setDimBackGround(Color color) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); //dim screen by 50%
        setBackground(Color.BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    private int getXforCenteredText(String text){
        return gp.SCREEN_WIDTH / 2 - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
    }
    
    private int getXTopRightText(String text) {
        return gp.SCREEN_WIDTH - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
    }
}
