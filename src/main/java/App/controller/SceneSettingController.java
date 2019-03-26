package App.controller;

import App.model.SceneSettingModel;
import AppBilder.Model;
import AppBilder.interf.Controller;
import game.Game;
import game.GameSetting;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class SceneSettingController implements Controller {
    public ChoiceBox<GameSetting.TypeGroup> chBoxGroup;
    public ChoiceBox<GameSetting.TypeSort> chBoxSort;
    public ChoiceBox<GameSetting.OpenWord> chBoxOpen;
    private GameSetting setting  = null;

    private SceneSettingModel myModel;

    @Override
    public void initialize() {

        chBoxGroup.setItems(FXCollections.observableArrayList(
                GameSetting.TypeGroup.TYPE,
                GameSetting.TypeGroup.LETTERS,
                GameSetting.TypeGroup.NAN
        ));
        chBoxSort.setItems(FXCollections.observableArrayList(
                GameSetting.TypeSort.ALPHABET,
                GameSetting.TypeSort.RANDOM
        ));
        chBoxOpen.setItems(FXCollections.observableArrayList(
                GameSetting.OpenWord.INFINITIVE,
                GameSetting.OpenWord.THIRD_FORM,
                GameSetting.OpenWord.PRÄTERITUM,
                GameSetting.OpenWord.PARTIZIP_2,
                GameSetting.OpenWord.RANDOM
        ));

    }

    @Override
    public void updateElementsData() {
        if(setting == null) return;

        chBoxOpen   .setValue(setting.getOpenWord());
        chBoxSort   .setValue(setting.getTypeSort());
        chBoxGroup  .setValue(setting.getTypeGroup());

    }

    @Override
    public void setMyModel(Model model) {
        myModel = (SceneSettingModel)model;
    }

    public void onActionNext(ActionEvent actionEvent) {
        updateSetting();
        myModel.showSelectStage();
    }

    public void onActionClose(ActionEvent actionEvent) {
        updateSetting();
        myModel.showStartScene();
    }

    public void setGameSetting(GameSetting setting) {
        this.setting = setting;
        updateElementsData();
    }

    public void updateSetting(){
        setting.setOpenWord(chBoxOpen.getValue());
        setting.setTypeGroup(chBoxGroup.getValue());
        setting.setTypeSort(chBoxSort.getValue());
    }
}
