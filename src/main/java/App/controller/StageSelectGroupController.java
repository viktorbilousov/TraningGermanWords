package App.controller;

import App.model.StageSelectGroupModel;
import AppBilder.Model;
import AppBilder.interf.Controller;
import AppBilder.util.StageUtil;
import AppBilder.util.tablecell.ChoiceBoxCell;
import game.Dictionary;
import game.Game;
import game.GameSetting;
import game.Verb;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;


public class StageSelectGroupController implements Controller {
    public TableView<Group> tableView;
    public TableColumn<Group, Boolean> columnChBox;
    public TableColumn<Group, String> columnName;
    public TableColumn<Group, String> columnCount;
    public ChoiceBox<GameSetting.TypeGroup> chBoxTypeGroup;
    private StageSelectGroupModel myModel;
    private Game game = null;
    private final String APLH = "abcdefghijklmnopqrstuvwxyzäöü".toUpperCase();
    private ObservableList<Group> typeGroupsArray = FXCollections.observableArrayList();
    private ObservableList<Group> lettersGroupsArray = FXCollections.observableArrayList();




    @Override
    public void initialize() {

        columnName.setCellValueFactory(value -> value.getValue().name);
        columnCount.setCellValueFactory(value -> value.getValue().cnt);
        columnChBox.setCellValueFactory(value -> value.getValue().isChecked);



        columnCount.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnChBox.setCellFactory(param ->  new CheckBoxTableCell());



        chBoxTypeGroup.setItems(FXCollections.observableArrayList(
                GameSetting.TypeGroup.TYPE,
                GameSetting.TypeGroup.LETTERS
        ));
        chBoxTypeGroup.setValue(GameSetting.TypeGroup.TYPE);
        chBoxTypeGroup.setOnAction(event -> {
            System.out.println("Event");
            if (chBoxTypeGroup.getValue() == GameSetting.TypeGroup.TYPE) {
                tableView.setItems(typeGroupsArray);
            }
            if (chBoxTypeGroup.getValue() == GameSetting.TypeGroup.LETTERS){
                tableView.setItems(lettersGroupsArray);
            }
        });

    }


    @Override
    public void updateElementsData() {
        if(game == null) return;
        Dictionary dictionary = game.getClonedDictinary();

        typeGroupsArray.clear();
        lettersGroupsArray.clear();
        
            for (int i = 0; i < game.getThemesNamesArr().length; i++) {
                String theme = game.getThemesNamesArr()[i];
                typeGroupsArray.add(new Group(theme, dictionary.getListByType(theme).size()));
            }

            for (int i = 0; i < APLH.length(); i++) {
                String name = "" + APLH.charAt(i);
                int cnt = culcVerbWithFirstChar(name.charAt(0), dictionary);
                if (cnt > 0) lettersGroupsArray.add(new Group(name, cnt));
            }
        if (chBoxTypeGroup.getValue() == GameSetting.TypeGroup.TYPE) {
            tableView.setItems(typeGroupsArray);
        }
        if (chBoxTypeGroup.getValue() == GameSetting.TypeGroup.LETTERS){
            tableView.setItems(lettersGroupsArray);
        }

    }

    private int culcVerbWithFirstChar(char c, Dictionary d){
        int cnt = 0;
        c = Character.toLowerCase(c);
        for (Verb verb : d) {
            if(verb.getInf().toLowerCase().charAt(0) == c) cnt ++;
        }
        return cnt;
    }
    @Override
    public void setMyModel(Model model) {
        myModel = (StageSelectGroupModel)model;
    }

    public void setGame(Game game) {
        this.game = game;
        updateElementsData();
    }

    public void onActionSelectAll(ActionEvent actionEvent) {
        for (Group group : tableView.getItems()) {
            group.isChecked.set(true);
        }
    }

    public void onActionDeselectAll(ActionEvent actionEvent) {
        for (Group group : tableView.getItems()) {
            group.isChecked.set(false);
        }
    }

    public void onActionPlay(ActionEvent actionEvent) {
        updateGame();
        myModel.showGame();
    }

    public void updateGame(){
        for (Group group : typeGroupsArray) {
            game.selectTheme(group.getName(), group.isIsChecked());
        }
        for (Group group : lettersGroupsArray) {
            game.selectLetter(group.getName().charAt(0), group.isIsChecked());
        }
    }

    public void onActionBack(ActionEvent actionEvent) {
        updateGame();
        myModel.showSetting();
    }

    public void onActionExit(ActionEvent actionEvent) {
        myModel.close();
    }

    public void onActionAbout(ActionEvent actionEvent) {
        StageUtil.showAboutMessage();
    }
}

final class Group{
    public SimpleStringProperty name;
    public SimpleStringProperty cnt;
    public SimpleBooleanProperty isChecked = new SimpleBooleanProperty(true);

    public Group(String name, Integer cnt) {
        this.name = new SimpleStringProperty(name);
        this.cnt = new SimpleStringProperty(cnt.toString());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCnt() {
        return cnt.get();
    }

    public SimpleStringProperty cntProperty() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt.set(cnt);
    }

    public boolean isIsChecked() {
        return isChecked.get();
    }

    public SimpleBooleanProperty isCheckedProperty() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked.set(isChecked);
    }
}
