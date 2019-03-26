package App.model;

import App.controller.StageStatisticController;
import AppBilder.Model;
import AppBilder.StageModel;
import game.Game;
import javafx.stage.Stage;

import java.net.URL;


public class StageStatisticModel extends StageModel {

    private StageStatisticController myController = (StageStatisticController)controller;

    public StageStatisticModel(Stage primaryStage, Model parent, URL FXMLLocation) {
        super(primaryStage, parent, FXMLLocation);
    }

    public void setGame(Game game) {
        myController.setGame(game);
    }
}
