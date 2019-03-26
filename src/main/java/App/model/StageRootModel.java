package App.model;

import App.RunApp;
import App.controller.StageRootController;
import AppBilder.Model;
import AppBilder.StageModel;
import AppBilder.util.StageUtil;
import game.Dictionary;
import game.Game;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Random;


public class StageRootModel extends StageModel {

    private SceneStartModel startModel;
    private SceneSettingModel settingModel;
    private StageSelectGroupModel selectModel;
    private SceneGameModel gameModel;
    private Game game = null;
    private StageRootController myController = (StageRootController)controller;

    public StageRootModel(Game game, Stage primaryStage, Model parent, URL FXMLLocation) {
        super(primaryStage, parent, FXMLLocation);
        this.game = game;
        init();
        showStartScene();
    }

    public void init(){
        startModel   =  new SceneStartModel(    (BorderPane) this.rootLayout, this,  RunApp.class.getResource("/fxml/SceneStart.fxml"));
        settingModel =  new SceneSettingModel(  (BorderPane) this.rootLayout, this,  RunApp.class.getResource("/fxml/SceneSetting_.fxml"));
        gameModel    =  new SceneGameModel(     (BorderPane) this.rootLayout,  this, RunApp.class.getResource("/fxml/SceneGame.fxml"));
        selectModel  =  new StageSelectGroupModel(
                StageUtil.makeNewStage("Select Group", primaryStage),
                this,
                RunApp.class.getResource("/fxml/StageSelectGroup.fxml") );

        settingModel.setSetting(game.getSetting());
        selectModel.setGame(game);
        gameModel.setGame(game);
    }

    public void showStartScene(){
        startModel.show();
    }

    public void showSettingScene(){
        myController.setDisableOpenFile(false);
        this.show();
        settingModel.show();
    }

    public void showSelectGroup(){
        myController.setDisableOpenFile(true);
        this.primaryStage.close();
        selectModel.updateData();
        selectModel.show();
    }

    public void showGame(){
        myController.setDisableOpenFile(true);
        this.show();
        gameModel.updateData();
        gameModel.show();
    }

    public void loadFromFile(File file){
        Dictionary dictionary = new Dictionary();
        try {
            dictionary.loadFromFile(file.getPath());
            game.setDictionary(dictionary);
            StageUtil.showInfoMessage("Message", "Success!", "File is successfully loaded", this.primaryStage);
        } catch (Exception e){
            StageUtil.showAlertMessage("Open File Error", "Error File", e.getMessage(), this.primaryStage);
        }
    }

    public void inputFile(File inputFile) {
        try {
            game.getClonedDictinary().saveToFile(inputFile);
            StageUtil.showInfoMessage("Message", "Success!", "File is successfully saved", this.primaryStage);
        } catch (IOException e) {
            StageUtil.showAlertMessage("Save File Error", "Error File", e.getMessage(), this.primaryStage);
        }
    }
}
