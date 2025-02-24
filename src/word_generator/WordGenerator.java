package word_generator;

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
    
    public static String randomWord(int type) {
        Random rm = new Random();
        return switch(type) {
            case EASY_WORD -> EASY_WORDs.get(rm.nextInt(EASY_WORDs.size()));
            case MEDIUM_WORD -> MEDIUM_WORDs.get(rm.nextInt(MEDIUM_WORDs.size()));
            case HARD_WORD -> HARD_WORDs.get(rm.nextInt(HARD_WORDs.size()));
            default -> "";
        };
    }
}
