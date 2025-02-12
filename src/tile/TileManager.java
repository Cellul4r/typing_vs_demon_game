/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import game_2d.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author cellul4r
 */
public class TileManager {
    
    private GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    
    public TileManager(GamePanel gp) {
        
        this.gp = gp;
        tile = new Tile[3];
        mapTileNum = new int [gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/map_res/map01.txt");
    }
    
    private void getTileImage() {
        
        tile[0] = new Tile(Color.darkGray, false);
        tile[1] = new Tile(Color.black, true);
        tile[2] = new Tile(Color.lightGray, true);
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
                Color color = tile[tileNum].getColor();
                
                g2.setColor(color);
                g2.fillRect(x, y, gp.tileSize, gp.tileSize);
                
                y += gp.tileSize;
            }
            
            x += gp.tileSize;
        }
    }
}
