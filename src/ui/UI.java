package ui;

import game_2d.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cellul4r
 */
public class UI {
    
    private final GamePanel gp;
    private final Font TimesNewRoman_80;
    private final Font TimesNewRoman_40;
    private final Font TimesNewRoman_30;
    private BufferedImage cursorImage;
    private BufferedImage mainMenuBackground;
    private BufferedImage difficultyMenuBackground;
    private Graphics2D g2;
    private boolean showWaveCompletedMessage;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        TimesNewRoman_80 = new Font("Times New Roman", Font.BOLD, 80);
        TimesNewRoman_40 = new Font("Times New Roman", Font.BOLD, 40);
        TimesNewRoman_30 = new Font("Times New Roman", Font.BOLD, 30);
        try{
            cursorImage = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/command_arrow.png"));
        }catch(IOException e){
            e.printStackTrace();
        }       
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
        drawImage("/resource/ui_res/main_menu_background.png", 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        String[] urls = {"/resource/ui_res/main_menu_command1.png", "/resource/ui_res/main_menu_command2.png"};
        drawMenuImage(urls, GamePanel.TILE_SIZE, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        if(gp.commandNum == 0) {
            drawCursorMenu(6 * GamePanel.TILE_SIZE, 5 * GamePanel.TILE_SIZE + GamePanel.TILE_SIZE / 2);
        } else {
            drawCursorMenu(7 * GamePanel.TILE_SIZE, 6 * GamePanel.TILE_SIZE + 4 * GamePanel.TILE_SIZE / 5);
        }
    }
    
    private void drawTutorialMenu(){
        
        drawImage("/resource/ui_res/background.jpg", 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        drawCenteredText("Controls", TimesNewRoman_80, Color.white, GamePanel.TILE_SIZE * 2);
        drawCenteredText(new String[]{"SHOOT : ENTER / SPACE", "MOVE UP : ^", "MOVE DOWN : v", 
                            "Type words to shoot enemies according to your row."}, 
                            TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 5);  
        drawMenu(new String[] {"CONTINUE", "EXIT"}, TimesNewRoman_30, Color.white, GamePanel.TILE_SIZE * 10);
    }
    
    private void drawDifficultySelectionMenu() {
        drawImage("/resource/ui_res/difficulty_menu_background.png", 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        String[] urls = {"/resource/ui_res/difficulty_menu_command1.png", "/resource/ui_res/difficulty_menu_command2.png", 
                        "/resource/ui_res/difficulty_menu_command3.png"};
        drawMenuImage(urls, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        switch (gp.commandNum) {
            case 0 -> drawCursorMenu(7 * GamePanel.TILE_SIZE, 5 * GamePanel.TILE_SIZE);
            case 1 -> drawCursorMenu(7 * GamePanel.TILE_SIZE, 6 * GamePanel.TILE_SIZE + GamePanel.TILE_SIZE / 4);
            default -> drawCursorMenu(7 * GamePanel.TILE_SIZE, 7 * GamePanel.TILE_SIZE + GamePanel.TILE_SIZE / 2);
        }
        setDimBackGround(Color.black);
    }
    
    private void drawText(String text, Font font, Color color, int x, int y) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }
    
    private void drawImage(String url, int x, int y, int width, int height) {
        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(url));
            g2.drawImage(image, x, y, width, height, null); 
        }catch(IOException e){
            e.printStackTrace();
        }       
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
    
    private void drawMenuImage(String[] urls, int y, int width, int height) {
        
        for(int i = 0; i < urls.length; i++) {
            drawImage(urls[i], 0, y, width, height);
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
        g2.drawImage(cursorImage, x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
    
    private void setBackground(Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
    }
    
    private void setDimBackGround(Color color) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); //dim screen by 50%
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
