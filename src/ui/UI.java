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
    private final Font DIALOG_80;
    private final Font DIALOG_40;
    private final Font DIALOG_30;
    private BufferedImage cursorImage;
    private BufferedImage mainMenuBg;
    private BufferedImage tutorialMenuBg;
    private BufferedImage difficultyMenuBg;
    private BufferedImage gameoverMenu;
    private BufferedImage itemInvBg;
    private BufferedImage healthBar;
    private BufferedImage pauseButton;
    private BufferedImage mainMenuCmd1, mainMenuCmd2;
    private BufferedImage diffcultyMenuCmd1, difficulyMenuCmd2, difficultyMenuCmd3;
    private BufferedImage labelBg;
    private Graphics2D g2;
    private boolean showWaveCompletedMessage;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        DIALOG_80 = new Font("DIALOG", Font.BOLD, 80);
        DIALOG_40 = new Font("DIALOG", Font.BOLD, 40);
        DIALOG_30 = new Font("DIALOG", Font.BOLD, 30);
        try{
            mainMenuBg = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/main_menu_background.png"));
            tutorialMenuBg = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/tutorial_menu_background.png"));
            difficultyMenuBg = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/difficulty_menu_background.png"));
            gameoverMenu = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/game_over_menu.png"));
            cursorImage = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/command_arrow.png"));
            pauseButton = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/pause_button.png"));
            itemInvBg = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/item_inventory_background.png"));
            healthBar = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/health_bar_ui.png"));
            mainMenuCmd1 = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/main_menu_command1.png"));
            mainMenuCmd2 = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/main_menu_command2.png"));
            diffcultyMenuCmd1 = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/difficulty_menu_command1.png"));
            difficulyMenuCmd2 = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/difficulty_menu_command2.png"));
            difficultyMenuCmd3 = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/difficulty_menu_command3.png"));
            labelBg = ImageIO.read(getClass().getResourceAsStream("/resource/ui_res/label_background.png"));
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
		g2.setColor(Color.black);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.fillRect(5 * GamePanel.TILE_SIZE, GamePanel.SCREEN_HEIGHT - 2 * GamePanel.TILE_SIZE + 10, 
					10 * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        drawCenteredText(gp.getPlayer().getUserInput(), DIALOG_40, Color.white, GamePanel.SCREEN_HEIGHT - GamePanel.TILE_SIZE);
        // draw Score of player UI
        drawImage(labelBg, GamePanel.SCREEN_WIDTH - 2 * GamePanel.TILE_SIZE, 0, 2 * GamePanel.TILE_SIZE, 2 * GamePanel.TILE_SIZE);
        drawTopRightText(String.valueOf(gp.getPlayer().getScore()), DIALOG_30,
                    Color.white, GamePanel.TILE_SIZE);
        // draw Wave Completed UI
        if(showWaveCompletedMessage) {
            drawCenteredText("Wave " + gp.getWave().getLevel() + " Completed!", DIALOG_40, Color.black, GamePanel.SCREEN_HEIGHT / 2);
        }
        
        // draw Health UI
        int defaultWidth = 7 * GamePanel.TILE_SIZE / 2 - 3;
        int heartWidth = (int)((double)gp.getPlayer().getHealth() / gp.getPlayer().getMaxHealth() * defaultWidth);
        int heartHeight = GamePanel.TILE_SIZE / 4 - 2;
        g2.setColor(new Color(222,56,70));
        drawImage(healthBar, 10, 9, 5 * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        g2.fillRoundRect(4 * GamePanel.TILE_SIZE / 3 + 5, GamePanel.TILE_SIZE / 2 + 5, heartWidth, heartHeight, 1, 1);
        
        // draw Item inventory UI
        drawImage(itemInvBg, 5 * GamePanel.TILE_SIZE + 20, 0, 2 * GamePanel.TILE_SIZE, 2 * GamePanel.TILE_SIZE);
        if(gp.getPlayer().getItem() != null) {
            drawImage(gp.getPlayer().getItem().getItemImage(), 6 * GamePanel.TILE_SIZE - 5, 40, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        }
    }
    
    private void drawPauseScreen(){ // Pause Menu for the player
        
        // background Color (Dim)
        setDimBackGround(Color.black, 0.5f);
        // draw Text "Pause"
        drawCenteredImage(pauseButton, GamePanel.SCREEN_HEIGHT / 2 - 3 * pauseButton.getHeight() / 2, 
                            20 * GamePanel.TILE_SIZE, 14 * GamePanel.TILE_SIZE);
    }
    
    private void drawGameOver(){ // When player lose -> GameOver Show up
        
        // background Color
        setDimBackGround(Color.black, 0.5f);
        
        drawCenteredImage(gameoverMenu, GamePanel.SCREEN_HEIGHT / 2 - 3 * pauseButton.getHeight() / 2, 
                            20 * GamePanel.TILE_SIZE, 14 * GamePanel.TILE_SIZE);
        drawCursorMenu(GamePanel.SCREEN_WIDTH / 2 - 2 *GamePanel.TILE_SIZE - 25, GamePanel.SCREEN_HEIGHT / 2 + GamePanel.TILE_SIZE + 13);
    }
    
    private void drawMainMenu() {
        drawImage(mainMenuBg, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        if(gp.commandNum == 0) {
            drawCursorMenu(6 * GamePanel.TILE_SIZE, 5 * GamePanel.TILE_SIZE);
        } else {
            drawCursorMenu(8 * GamePanel.TILE_SIZE, 6 * GamePanel.TILE_SIZE + GamePanel.TILE_SIZE / 4);
        }
    }
    
    private void drawTutorialMenu(){
        drawImage(tutorialMenuBg, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        if(gp.commandNum == 0) {
            drawCursorMenu(11 * GamePanel.TILE_SIZE, 10 * GamePanel.TILE_SIZE + 3 * GamePanel.TILE_SIZE / 2);
        } else {
            drawCursorMenu(4 * GamePanel.TILE_SIZE - 15, 10 * GamePanel.TILE_SIZE + 3 * GamePanel.TILE_SIZE / 2);
        }
    }
    
    private void drawDifficultySelectionMenu() {
        drawImage(difficultyMenuBg, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        switch (gp.commandNum) {
            case 0 -> drawCursorMenu(7 * GamePanel.TILE_SIZE, 5 * GamePanel.TILE_SIZE);
            case 1 -> drawCursorMenu(7 * GamePanel.TILE_SIZE, 6 * GamePanel.TILE_SIZE + GamePanel.TILE_SIZE / 4);
            default -> drawCursorMenu(7 * GamePanel.TILE_SIZE, 7 * GamePanel.TILE_SIZE + GamePanel.TILE_SIZE / 2);
        }
    }
    
    private void drawText(String text, Font font, Color color, int x, int y) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }
    
    private void drawImage(BufferedImage image, int x, int y, int width, int height) {
        g2.drawImage(image, x, y, width, height, null);    
    }
    
    private void drawCenteredText(String text, Font font, Color color, int y) {
        g2.setFont(font);
        g2.setColor(color);
        int x = getXforCenteredText(text);
        g2.drawString(text, x, y);
    }
    
    private void drawCenteredImage(BufferedImage image, int y, int width, int height) {
        int x = GamePanel.SCREEN_WIDTH / 2 - 3 * image.getWidth() / 2;
        drawImage(image, x, y, width, height);
    }
    
    private void drawTopRightText(String text, Font font, Color color, int y) {
        g2.setFont(font);
        g2.setColor(color);
        int x = getXTopRightText(text);
        g2.drawString(text, x - GamePanel.TILE_SIZE, y + 35);
    }
    
    private void drawCenteredText(String[] texts, Font font, Color color, int y) {
        
        for(int i = 0; i < texts.length; i++) {
            int newY = y + GamePanel.TILE_SIZE * i;
            drawCenteredText(texts[i], font, color, newY);
        }
    }
    
    private void drawMenuImage(BufferedImage[] images, int y, int width, int height) { 
        for(int i = 0; i < images.length; i++) {
            drawImage(images[i], 0, y, width, height);
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
    
    private void setDimBackGround(Color color, float value) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value)); //dim screen by 50%
        setBackground(color);
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f)); //dim screen by 50%
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    private int getXforCenteredText(String text){
        return GamePanel.SCREEN_WIDTH / 2 - getWidthText(text) / 2;
    }
    
    private int getXTopRightText(String text) {
        return GamePanel.SCREEN_WIDTH - getWidthText(text) / 2;
    }
    
    private int getWidthText(String text) {
        return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }
    public void setShowWaveCompletedMessage(boolean show) {
        showWaveCompletedMessage = show;
    }
}
