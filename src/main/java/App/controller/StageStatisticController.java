package App.controller;

import App.model.StageStatisticModel;
import AppBilder.Model;
import AppBilder.interf.Controller;
import AppBilder.util.StageUtil;
import game.Dictionary;
import game.Exception.GameProcessException;
import game.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.IOException;


public class StageStatisticController implements Controller {

    private enum ChBoVar {
        SaveAll,
        SaveWrong,
        SaveRight
    }
    public ChoiceBox<ChBoVar> chBoxSaveVar;
    private StageStatisticModel myModel;
    private Game game = null;

    @Override
    public void initialize() {
        chBoxSaveVar.getItems().add(ChBoVar.SaveAll);
        chBoxSaveVar.getItems().add(ChBoVar.SaveWrong);
        chBoxSaveVar.getItems().add(ChBoVar.SaveRight);
        chBoxSaveVar.setValue(ChBoVar.SaveAll);
    }

    @Override
    public void updateElementsData() {

    }

    @Override
    public void setMyModel(Model model) {
        myModel = (StageStatisticModel)model;
    }

    public void bntOnActionCancel(ActionEvent actionEvent) {
        myModel.close();
    }

    public void bntOnActionSave(ActionEvent actionEvent) {
        ChBoVar variantSave = chBoxSaveVar.getValue();
        try {
            Dictionary[] dictionaries = game.getStatistic();

            if(variantSave == ChBoVar.SaveAll){
                File file = StageUtil.FileSaver(myModel.getPrimaryStage(), "Right Verbs Statistic.txt", StageUtil.TXTFiler);
                dictionaries[0].saveToFile(file);

                file = StageUtil.FileSaver(myModel.getPrimaryStage(), "Wrong Verbs Statistic.txt", StageUtil.TXTFiler);
                dictionaries[1].saveToFile(file);
            }
            if(variantSave == ChBoVar.SaveRight){
                File file = StageUtil.FileSaver(myModel.getPrimaryStage(), "Right Verbs Statistic.txt", StageUtil.TXTFiler);
                dictionaries[0].saveToFile(file);
            }
            if(variantSave == ChBoVar.SaveWrong){
                File file = StageUtil.FileSaver(myModel.getPrimaryStage(), "Wrong Verbs Statistic.txt", StageUtil.TXTFiler);
                dictionaries[1].saveToFile(file);
            }

        } catch (GameProcessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bntOnActionCancel(null);

    }

    public void setGame(Game game) {
        this.game = game;
    }
}
