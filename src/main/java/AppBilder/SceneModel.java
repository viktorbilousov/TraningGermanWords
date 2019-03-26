package AppBilder;

import AppBilder.interf.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.net.URL;

public class SceneModel implements Model {

    protected Controller controller;
    protected BorderPane rootLayout;
    protected AnchorPane thisLayout;
    protected StageModel parent;


    public SceneModel(BorderPane rootLayout, StageModel parent, URL FXMLLocation) {
        this.rootLayout = rootLayout;
        this.parent = parent;
        init(FXMLLocation);
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }


    public void init(URL pathToFXML){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(pathToFXML);
            thisLayout =  loader.load();
            // Даём контроллеру доступ к главному прилодению.
            controller = loader.getController();
            controller.setMyModel(this);

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void updateData(){
        if(controller == null){
            System.out.println("error: controller == null in " + getClass().getSimpleName());
            return;
        }
        controller.updateElementsData();
    }

    @Override
    public void updateDataParent() {
        parent.parent.updateData();
    }

    public void updateParentsStage(){
        parent.updateData();
    }

    public void closeParentStage(){
        parent.close();
    }


    protected void preShowInit(){};
    @Override
    public void show(){
        preShowInit();
        rootLayout.setCenter(thisLayout);
    }
    @Override
    public void close() {
        thisLayout.setVisible(false);
    }
}

