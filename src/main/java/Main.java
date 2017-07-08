import game.Dictionary;
import game.Game;
import game.Verb;

import java.util.Arrays;
import java.util.Scanner;

import static com.apple.eio.FileManager.getResource;

/**
 * Created by Viktor on 07.07.17.
 */
public class Main {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        dictionary.loadFromFile(Main.class.getResourceAsStream("dictionary.txt"));
        Game game = new Game(dictionary);
        game.startGame();
        try {
            System.out.println(game.nextVerb());

            Scanner scanner = new Scanner(System.in);
            String inLine = scanner.nextLine();
            Verb candidat = Verb.parse(inLine);
            System.out.println(game.checkVerb(candidat));
            System.out.println(Arrays.toString(game.getMarks(candidat)));
            System.out.println(game.getRightVerb());


        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

    }
}
