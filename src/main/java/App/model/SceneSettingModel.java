package App.model;

import App.controller.SceneSettingController;
import AppBilder.SceneModel;
import AppBilder.StageModel;
import game.GameSetting;
import javafx.scene.layout.BorderPane;

import java.net.URL;


public class SceneSettingModel extends SceneModel {

    private StageRootModel rootParent;
    private SceneSettingController myController = (SceneSettingController) controller;

    public SceneSettingModel(BorderPane rootLayout, StageModel parent, URL FXMLLocation) {
        super(rootLayout, parent, FXMLLocation);
        rootParent = (StageRootModel) super.parent;
    }

    public void showSelectStage() {
        rootParent.showSelectGroup();
    }

    public void setSetting(GameSetting setting) {
        myController.setGameSetting(setting);
    }


    public void showStartScene() {
        rootParent.showStartScene();
    }
}
