package word_generator;

import game_2d.GamePanel;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cellul4r
 */
public class WordGenerator {
    
    public static final int EASY_WORD = 0;
    public static final int MEDIUM_WORD = 1;
    public static final int HARD_WORD = 2;
    public static final ArrayList<String> EASY_WORDs = new ArrayList<>();
    public static final ArrayList<String> MEDIUM_WORDs = new ArrayList<>();
    public static final ArrayList<String> HARD_WORDs = new ArrayList<>();
    public static final Random rm = new Random();
    
    public static void loadWordList() {
        loadWord(EASY_WORD, "/resource/word_res/easy_word.txt");
        loadWord(MEDIUM_WORD, "/resource/word_res/medium_word.txt");
        loadWord(HARD_WORD, "/resource/word_res/hard_word.txt");
    }
    
    private static void loadWord(int type, String filePath) {
        try {
            InputStream is = WordGenerator.class.getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word = br.readLine();
            while(word != null){
               word = word.toLowerCase();
               switch(type) {
                   case EASY_WORD -> EASY_WORDs.add(word);
                   case MEDIUM_WORD -> MEDIUM_WORDs.add(word);
                   case HARD_WORD -> HARD_WORDs.add(word);
               }
               word = br.readLine();
            }
            br.close();
        } catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public static String randomWord(int difficulty) {
        int type = randomDifficulty(difficulty);
        return switch(type) {
            case EASY_WORD -> EASY_WORDs.get(rm.nextInt(EASY_WORDs.size()));
            case MEDIUM_WORD -> MEDIUM_WORDs.get(rm.nextInt(MEDIUM_WORDs.size()));
            case HARD_WORD -> HARD_WORDs.get(rm.nextInt(HARD_WORDs.size()));
            default -> "";
        };
    }
    
    private static int randomDifficulty(int difficulty) {
        int easyChance = switch(difficulty) {
            case GamePanel.EASY -> 80;
            case GamePanel.MEDIUM -> 60;
            case GamePanel.HARD -> 40;
            default -> 100;
        };
        int medHardChance = (100 - easyChance) / 2;
        int chance = rm.nextInt(100);
        int type;
        if(chance <= easyChance) type = EASY_WORD;
        else if(chance <= easyChance + medHardChance) type = MEDIUM_WORD;
        else type = HARD_WORD;
        return type;
    }
}
