package game;

import game.Exception.EndGame;
import game.Exception.GameArgumentException;
import game.Exception.GameCheckVerbException;
import game.Exception.GameNextVerbException;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Run {

    public static void main(String[] args) throws FileNotFoundException, IllegalAccessException {
        Dictionary dictionary = new Dictionary();

        String parser[] = Run.class.getResource("dict.data").getPath().split("/");
        String input = "/" + parser[parser.length-1];
        dictionary.loadFromResource(Run.class.getClass().getResource(input).getPath());
        Game game = new Game(dictionary);
        try {
            game.startGame();
            Verb nextVerb = new Verb("","","","");
            int cnt = 0;
            while (nextVerb != null) {
                nextVerb = game.nextVerb();
                System.out.println(nextVerb);

                Scanner scanner = new Scanner(System.in);
                String inLine = scanner.nextLine();

                Verb candidat = Verb.parse(inLine);

                System.out.println(game.checkVerb(candidat));
                System.out.println(Arrays.toString(game.getMarks(candidat)));
                try {
                    System.out.println(game.getRightVerb());
                } catch (GameCheckVerbException e) {
                    e.printStackTrace();
                }
                cnt++;
            }

        } catch (GameArgumentException | GameNextVerbException | EndGame e) {
            e.printStackTrace();
        }

    }
}
