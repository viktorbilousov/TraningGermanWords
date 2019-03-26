package App.controller;

import App.model.StageRootModel;
import AppBilder.Model;
import AppBilder.interf.Controller;
import AppBilder.util.StageUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;


public class StageRootController implements Controller {


    public MenuItem mItemOpenFile;
    StageRootModel myModel;
    @Override
    public void initialize() {

    }

    @Override
    public void updateElementsData() {

    }

    @Override
    public void setMyModel(Model model) {
        myModel = (StageRootModel) model;
    }

    public void onActionOpen(ActionEvent actionEvent) {
        File input = StageUtil.FileChooser(myModel.getPrimaryStage(), StageUtil.TXTFiler);
        System.out.println(input);
        myModel.loadFromFile(input);
    }

    public void onActionExit(ActionEvent actionEvent) {
        myModel.close();
    }

    public void onActionAbout(ActionEvent actionEvent) {
        StageUtil.showAboutMessage();
    }

    public void setDisableOpenFile(boolean isDisable){
        mItemOpenFile.setDisable(isDisable);
    }
}
