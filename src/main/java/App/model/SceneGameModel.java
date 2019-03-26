package App.model;

import App.RunApp;
import App.controller.SceneGameController;
import AppBilder.SceneModel;
import AppBilder.StageModel;
import AppBilder.util.StageUtil;
import game.Game;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.net.URL;


public class SceneGameModel extends SceneModel {

    private SceneGameController myController = (SceneGameController) controller;
    private StageRootModel rootModel = (StageRootModel)parent;
    private StageStatisticModel statisticModel;

    public SceneGameModel(BorderPane rootLayout, StageModel parent, URL FXMLLocation) {
        super(rootLayout, parent, FXMLLocation);
        init();
    }

    public void init(){
        statisticModel = new StageStatisticModel(StageUtil.makeNewStage("Save Statistic", parent.getPrimaryStage()), this,
                RunApp.class.getResource("/fxml/StageStatistic.fxml"));
    }

    public void setGame(Game game) {

        myController.setGame(game);
        statisticModel.setGame(game);

    }

    public void showSetting() {
        rootModel.showSelectGroup();
    }

    public void openStatistic() {
        statisticModel.showAndWait();
    }
}
