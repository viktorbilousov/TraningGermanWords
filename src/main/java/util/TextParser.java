package util;

import game.Verb;

import java.io.*;
import java.util.ArrayList;


public class TextParser {

    public static void textNormalizator(String inputPath, String outputFile) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            writer = new BufferedWriter(new FileWriter(outputFile));

            String line = "";
            String input = "";

            int cnt = 1;
            final int sizeColumn = 4;
            int cntType = 1;
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '*') {
                    if (cnt != 1) {
                        writer.write(input + "\n");
                        input = "";
                        cnt = 1;
                    }
                    input = line.substring(2, line.length());
                    writer.write("\n* Type " + cntType++);
                    writer.write("\n* " + input + "\n\n");
                    input = "";
                    continue;
                }

                if (cnt == 2) {
                    cnt++;
                    continue; // second word is translation to Russian
                }
                input += line + " ";
                if (cnt == sizeColumn) {
                    writer.write(input + "\n");
                    input = "";
                    cnt = 1;
                } else {
                    cnt++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();

                if (reader != null)
                    reader.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
