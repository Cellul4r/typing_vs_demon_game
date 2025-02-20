/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import game_2d.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author cellul4r
 */
public class TileManager {
    
    private final GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    
    public TileManager(GamePanel gp) {
        
        this.gp = gp;
        tile = new Tile[4];
        mapTileNum = new int [gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/resource/map_res/map01.txt");
    }
    
    private void getTileImage() {
        
        try {
            tile[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/item_res/soil1.png")), false);
            tile[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/item_res/soil2.png")), false);
            tile[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/item_res/forest.png")), true);
            tile[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/item_res/wall1.png")), true);
            
        } catch (IOException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadMap(String filePath) {
        
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int i = 0; i < gp.maxScreenRow; i++) {

                String line = br.readLine();
                String numbers[] = line.split(" ");

                for(int j = 0; j < gp.maxScreenCol; j++) {

                    int num = Integer.parseInt(numbers[j]);
                    mapTileNum[j][i] = num;
                }
            }
            br.close();
        } catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void draw(Graphics2D g2) {
        
        for(int i = 0, x = 0; i < gp.maxScreenCol; i++) {
            for(int j = 0, y = 0; j < gp.maxScreenRow; j++) {
                
                int tileNum = mapTileNum[i][j];
                g2.drawImage(tile[tileNum].getImage(), x, y, gp.tileSize, gp.tileSize, null);
                
                y += gp.tileSize;
            }
            
            x += gp.tileSize;
        }
    }
    
    public Tile getTile(int i) {
        return this.tile[i];
    }
    
    public int getMapTileNum(int x, int y) {
        return this.mapTileNum[x][y];
    }
}
