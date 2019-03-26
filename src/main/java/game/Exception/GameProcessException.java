package game.Exception;


public class GameProcessException extends Exception {
    public GameProcessException() {
        super("ERROR GAME PROCESS");
    }

    public GameProcessException(String message) {
        super(message);
    }
}
