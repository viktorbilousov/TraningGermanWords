package util;

import game.Verb;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Viktor on 07.07.17.
 */
public class WordsWriter {
    public static void WriteWordsToFile(ArrayList<Verb> verbs, String path){
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            for (Verb w: verbs) {
                String line = Arrays.toString(w.getWordArr());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
