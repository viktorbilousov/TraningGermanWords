package App.model;

import App.controller.StageSelectGroupController;
import AppBilder.Model;
import AppBilder.StageModel;
import game.Game;
import javafx.stage.Stage;

import java.net.URL;


public class StageSelectGroupModel extends StageModel {

    private StageSelectGroupController myController = (StageSelectGroupController)controller;
    private StageRootModel myParent = (StageRootModel) parent;


    public StageSelectGroupModel(Stage primaryStage, Model parent, URL FXMLLocation) {
        super(primaryStage, parent, FXMLLocation);
    }

    public void setGame(Game game) {
        myController.setGame(game);
    }

    public void showGame() {
        this.close();
        myParent.showGame();
    }

    public void showSetting() {
        this.close();
        myParent.showSettingScene();
    }
}
