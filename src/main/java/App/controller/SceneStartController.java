package App.controller;

import App.model.SceneStartModel;
import AppBilder.Model;
import AppBilder.interf.Controller;
import javafx.event.ActionEvent;


public class SceneStartController implements Controller {

    private SceneStartModel myModel;

    @Override
    public void initialize() {

    }

    @Override
    public void updateElementsData() {

    }

    @Override
    public void setMyModel(Model model) {
        myModel = (SceneStartModel) model;
    }

    public void onActionPlayBtn(ActionEvent actionEvent) {
        myModel.showSetting();
    }

    public void onActionExitBtn(ActionEvent actionEvent) {
        myModel.closeParentStage();
    }

    public void onActionOpenDict(ActionEvent actionEvent) {
        myModel.loadFile();
    }

    public void onActionInputDic(ActionEvent actionEvent) {
        myModel.inputDictFile();
    }
}
