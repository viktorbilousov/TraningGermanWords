package App.model;

import AppBilder.SceneModel;
import AppBilder.StageModel;
import AppBilder.util.StageUtil;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.net.URL;


public class SceneStartModel extends SceneModel {

    private StageRootModel model;

    public SceneStartModel(BorderPane rootLayout, StageModel parent, URL FXMLLocation) {
        super(rootLayout, parent, FXMLLocation);
        init();
    }

    private void init(){
        model = (StageRootModel)parent;
    }

    public void showSetting() {
        model.showSettingScene();
    }

    public void loadFile(){
        File input = StageUtil.FileChooser(model.getPrimaryStage(), StageUtil.TXTFiler);
        System.out.println(input);
        model.loadFromFile(input);
    }

    public void inputDictFile(){
        File inputFile = StageUtil.FileSaver(model.getPrimaryStage(), "Dictionary",StageUtil.TXTFiler);
        model.inputFile(inputFile);
    }
}
