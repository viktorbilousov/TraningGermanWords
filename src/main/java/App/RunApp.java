package App;

import App.model.StageRootModel;
import AppBilder.util.StageUtil;
import game.Dictionary;
import game.Game;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;



public class RunApp extends Application {
    private Stage primaryStage = null;
    private StageRootModel rootStageModel;
    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start");
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Verbs Training");
            initialize();
            rootStageModel.show();
        }catch (Exception e){
            StageUtil.showExceptionDialog(e);
        }
        System.out.println("ok start");
    }

    private void initialize() {
        System.out.println("start init");
        Dictionary d = new Dictionary();
        try {
            d.loadFromResource(RunApp.class.getResource("/dict.data").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        game = new Game(d);
        rootStageModel = new StageRootModel(
                game,
                primaryStage,
                null,
                this.getClass().getResource("/fxml/StageRoot.fxml")
        );

        System.out.println("end init");

    }

    public static void main(String[] args) {
        try {
            launch(args);
        }catch (Exception e){
            System.out.println(e);
            StageUtil.showExceptionDialog(e);

        }
    }
}
