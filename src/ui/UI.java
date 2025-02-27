package ui;

import game_2d.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author cellul4r
 */
public class UI {
    
    private final GamePanel gp;
    private final Font TimesNewRoman_80;
    private final Font TimesNewRoman_40;
    private final Font TimesNewRoman_30;
    private Graphics2D g2;
    private boolean showWaveCompletedMessage;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        TimesNewRoman_80 = new Font("Times New Roman", Font.BOLD, 80);
        TimesNewRoman_40 = new Font("Times New Roman", Font.BOLD, 40);
        TimesNewRoman_30 = new Font("Times New Roman", Font.BOLD, 30);
    }
    
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        switch(gp.gameState) {
            case GamePanel.TITLE_STATE -> drawTitleScreen();
            case GamePanel.PLAY_STATE -> drawPlayScreen();
            case GamePanel.PAUSE_STATE -> drawPauseScreen();
            case GamePanel.GAME_OVER_STATE -> drawGameOver();
        }
        g2.dispose();
    }
    
    private void drawTitleScreen(){
        switch(gp.titleScreenState) {
            case GamePanel.TITLE_MAIN -> drawMainMenu(); 
            case GamePanel.TITLE_TUTORIAL -> drawTutorialMenu();
            case GamePanel.TITLE_DIFFICULTY -> drawDifficultySelectionMenu();
        }
    }
    
    private void drawPlayScreen() {
        // draw UserInput's Word of the player
        drawText(gp.getPlayer().getUserInput(), TimesNewRoman_40, Color.white,  
                    GamePanel.TILE_SIZE * 4, GamePanel.SCREEN_HEIGHT - GamePanel.TILE_SIZE);
        // draw Score of player UI
        drawTopRightText(String.valueOf(gp.getPlayer().getScore()), TimesNewRoman_40,
                    Color.white, GamePanel.TILE_SIZE);
        // draw Wave Number UI
        drawCenteredText(String.valueOf(gp.getWave().getLevel()), TimesNewRoman_40, Color.white, GamePanel.TILE_SIZE);
        if(showWaveCompletedMessage) {
            drawCenteredText("Wave " + gp.getWave().getLevel() + " Completed!", TimesNewRoman_40, Color.black, GamePanel.SCREEN_HEIGHT / 2);
        }
        
        // draw Health UI
        int defaultWidth = 5 * GamePanel.TILE_SIZE;
        int heartWidth = (int)((double)gp.getPlayer().getHealth() / gp.getPlayer().getMaxHealth() * defaultWidth);
        int heartHeight = GamePanel.TILE_SIZE / 3;
        g2.setColor(Color.red);
        g2.fillRoundRect(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE / 2, heartWidth, heartHeight, 15, 15);
    }
    
    private void drawPauseScreen(){ // Pause Menu for the player

        // background Color
        setDimBackGround(Color.black);
        // draw Text "Pause"
        drawCenteredText("PAUSED", TimesNewRoman_80, Color.white, GamePanel.SCREEN_HEIGHT / 2);
    }
    
    private void drawGameOver(){ // When player lose -> GameOver Show up
        
        // background Color
        setDimBackGround(Color.black);
        // draw Text "Pause"
        drawCenteredText("GAME OVER", TimesNewRoman_80, Color.white, GamePanel.SCREEN_HEIGHT / 2);
        
        // selection menu for the player
        drawMenu(new String[]{"MAIN MENU"}, TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 10);
        
    }
    
    private void drawMainMenu() {
        setBackground(Color.BLACK);
        // draw TitleName
        drawCenteredText("Typing VS Demon", TimesNewRoman_80, Color.white, GamePanel.TILE_SIZE * 3);
        // draw MenuSelection
        drawMenu(new String[]{"NEW GAME", "QUIT"}, TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 8);
    }
    
    private void drawTutorialMenu() {
        setBackground(Color.BLACK);
        drawCenteredText("Controls", TimesNewRoman_80, Color.white, GamePanel.TILE_SIZE * 2);
        drawCenteredText(new String[]{"SHOOT : ENTER / SPACE", "MOVE UP : ^", "MOVE DOWN : v", 
                            "Type words to shoot enemies according to your row."}, 
                            TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 5);
        drawMenu(new String[] {"CONTINUE", "EXIT"}, TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 10);
    }
    
    private void drawDifficultySelectionMenu() {
        setBackground(Color.BLACK);
        drawCenteredText("Choose Difficulty", TimesNewRoman_80, Color.white, GamePanel.TILE_SIZE * 3);

        drawMenu(new String[]{"EASY", "MEDIUM", "HARD"}, TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 8);
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
        g2.drawString(text, x - GamePanel.TILE_SIZE, y);
    }
    
    private void drawCenteredText(String[] texts, Font font, Color color, int y) {
        
        for(int i = 0; i < texts.length; i++) {
            int newY = y + GamePanel.TILE_SIZE * i;
            drawCenteredText(texts[i], font, color, newY);
        }
    }
    
    private void drawMenu(String[] texts, Font font, Color color, int y) {
        
        for(int i = 0; i < texts.length; i++) {
            int newY = y + GamePanel.TILE_SIZE * i;
            drawCenteredText(texts[i], font, color, newY);
            
            if(gp.commandNum == i) {
                drawCursorMenu(getXforCenteredText(texts[i]), newY);
            }
        }
    }
    
    private void drawCursorMenu(int x, int y) {
        g2.drawString(">", x - GamePanel.TILE_SIZE, y);
    }
    
    private void setBackground(Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
    }
    
    private void setDimBackGround(Color color) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); //dim screen by 50%
        setBackground(color);
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f)); //dim screen by 50%
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    private int getXforCenteredText(String text){
        return GamePanel.SCREEN_WIDTH / 2 - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
    }
    
    private int getXTopRightText(String text) {
        return GamePanel.SCREEN_WIDTH - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
    }
    
    public void setShowWaveCompletedMessage(boolean show) {
        showWaveCompletedMessage = show;
    }
}
