package App.controller;

import App.model.SceneGameModel;
import AppBilder.Model;
import AppBilder.interf.Controller;
import game.Exception.EndGame;
import game.Exception.GameArgumentException;
import game.Exception.GameCheckVerbException;
import game.Exception.GameNextVerbException;
import game.Game;
import game.Verb;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class SceneGameController implements Controller {

    public TextField textFieldInf;
    public TextField textFieldThird;
    public TextField textFieldPrat;
    public TextField textFieldP2;

    public Button btnStop;
    public Button btnNext;
    public Label labelCnt;
    public Label labelAnswerCnt;
    public Label labelMessage;

    private final String styleCorrect = "-fx-effect: dropshadow( gaussian , rgba(0,255,0,0.8) , 1,1,0,0 );";
    private final String styleIncorrect = "-fx-effect: dropshadow( gaussian , rgba(255,0,0,0.8) , 1,1,0,0 );";
    private final String styleRedText = "-fx-background-color: red;";
    private final String styleGreenText = "-fx-background-color: green;";

    private final String GOOT_MESSAGE = "RIGHT";
    private final String BAD_MESSAGE = "WRONG";

    private final String Szet = "\u00DF";
    private final String A_uml = "\u00E4";
    private final String U_uml = "\u00FC";
    private final String O_uml = "\u00F6";

    private int indexLastFocusedField = -1;
    private int lastPosCorret = 0;

    public Button btn_S;
    public Button btn_U;
    public Button btn_A;
    public Button btn_O;

    private boolean isRightVerb = false;
    private boolean isNeedCheck = false;
    private boolean isEndGame = false;
    private SceneGameModel myModel;
    private Game game;
    private int globalCnt = 0;
    private int answerCnt = 0;
    private int wrongCnt = 0;

    private void setNeedCheck(boolean isNeeCheck){
        if(isNeeCheck){
            btnNext.setText("Check");
        }else{
            btnNext.setText("Next");
        }
        this.isNeedCheck = isNeeCheck;
    }

    private void setEndGame(boolean isEndGame){
        if(isEndGame){
            btnNext.setText("Continue game");
            globalCnt = 0;
            answerCnt = 0;
        }
        this.isEndGame = isEndGame;
    }



    @Override
    public void initialize() {
        btnNext.setOnAction(event -> onActionNewWord());
        btnStop.setOnAction(event -> onActionStop());

        btn_A.setOnAction(event -> addAUmlautToText());
        btn_U.setOnAction(event -> addUUmlautToText());
        btn_O.setOnAction(event -> addOUmlautToText());
        btn_S.setOnAction(event -> addSzetToText());

        textFieldInf.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                indexLastFocusedField = 1;
            }if(oldValue){
                lastPosCorret = textFieldInf.getCaretPosition();
            }
        });

        textFieldThird.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                indexLastFocusedField = 2;
            }if(oldValue){
                lastPosCorret = textFieldThird.getCaretPosition();
            }

        });
        textFieldPrat.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                indexLastFocusedField = 3;
            }if(oldValue){
                lastPosCorret = textFieldPrat.getCaretPosition();
            }

        });
        textFieldP2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                indexLastFocusedField = 4;
            }if(oldValue){
                lastPosCorret = textFieldP2.getCaretPosition();
            }

        });
    }

    private void addOUmlautToText() {
        addToFocusedFieldText(O_uml);
    }

    private void addUUmlautToText() {
        addToFocusedFieldText(U_uml);
    }

    private void addSzetToText() {
        addToFocusedFieldText(Szet);
    }

    private void addAUmlautToText() {
        addToFocusedFieldText(A_uml);
    }

    private void addToFocusedFieldText(String text){

        if(indexLastFocusedField == 1){
            addTextInFielAtCorretIndex(lastPosCorret, text, textFieldInf);
        }else if(indexLastFocusedField == 2){
            addTextInFielAtCorretIndex(lastPosCorret, text, textFieldThird);
        }else if (indexLastFocusedField == 3){
            addTextInFielAtCorretIndex(lastPosCorret, text, textFieldPrat);
        }else if(indexLastFocusedField == 4){
            addTextInFielAtCorretIndex(lastPosCorret, text, textFieldP2);
        }
    }

    private void addTextInFielAtCorretIndex(int index , String text,  TextField field){
        field.setText(addTextAt(index,field.getText(), text));
        field.requestFocus();
        field.positionCaret(index + 1);
    }

    private String addTextAt(int index, String src, String text){
        if(index < 0 || index >= src.length()) return src + text;
        return src.substring(0, index) + text + src.substring(index);
    }

    private void onActionNewWord() {
        if(isNeedCheck) checkWord();
        else if(isEndGame) {
            resumeGame();
        }
        else newWord();
    }

    private void newGame() {
        try {
            game.startGame();
            postInitGame();

        } catch (GameArgumentException e) {
            e.printStackTrace();
        }
    }

    private void postInitGame(){
        globalCnt = 0;
        answerCnt = 0;
        setEndGame(false);
        setNeedCheck(false);
        newWord();
        updateCnts();
    }

    private void resumeGame() {
        try {
            game.resumeGame();
            postInitGame();

        } catch (GameArgumentException e) {
            e.printStackTrace();
        }
    }

    private void onActionStop(){
        setEndGame(true);
        myModel.showSetting();
    }


    @Override
    public void updateElementsData() {
        if(game == null) return;
        newGame();
    }

    @Override
    public void setMyModel(Model model) {
        myModel =  (SceneGameModel) model;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void checkWord(){
        Verb v = readVerb();
       isRightVerb = game.checkVerb(v);
       boolean marks[] = game.getMarks(v);
       coloringTextFill(marks);
        try {
            writeRightVerbs(marks, game.getRightVerb());
        } catch (GameCheckVerbException e) {
            e.printStackTrace();
        }
        if(isRightVerb) {
            answerCnt++;
            setLabelMessage(GOOT_MESSAGE, styleGreenText);
        }
        else{
            setLabelMessage(BAD_MESSAGE, styleRedText);
            wrongCnt ++;
        }

        setNeedCheck(false);
       setEndGame(game.isEnd());
       updateCnts();
    }

    private void writeRightVerbs(boolean[] marks, Verb rightVerb){

        if(!marks[0]) textFieldInf.setText(textFieldInf.getText() +"\\" + rightVerb.getInf());

        if(!marks[1]) textFieldThird.setText(textFieldThird.getText() +"\\" + rightVerb.getThird());

        if(!marks[2]) textFieldPrat.setText(textFieldPrat.getText() +"\\" + rightVerb.getPrat());

        if(!marks[3]) textFieldP2.setText(textFieldP2.getText() +"\\" + rightVerb.getP2());

    }


    private void newWord(){
        Verb v = null;
        try {
            v = game.nextVerb();
            fillVerb(v);
            resetTextFillStyle();
            globalCnt ++;
            setNeedCheck(true);
        } catch (GameNextVerbException e) {
            e.printStackTrace();
        } catch (EndGame endGame) {
            System.out.println(endGame.getMessage());
            setEndGame(true);
            openStatistic();
        }
        finally {
            updateCnts();
            setLabelMessage("");
        }
    }

    private void fillVerb(Verb verb){
        textFieldInf.setText(verb.getInf());
        textFieldThird.setText(verb.getThird());
        textFieldPrat.setText(verb.getPrat());
        textFieldP2.setText(verb.getP2());
    }

    private Verb readVerb(){
        String inf = textFieldInf.getText().toLowerCase().replace(" ", "");
        String thF = textFieldThird.getText().toLowerCase().replace(" ", "");
        String prat = textFieldPrat.getText().toLowerCase().replace(" ","");
        String p2 = textFieldP2.getText().toLowerCase().replace(" ","");

        return new Verb(inf,thF,prat,p2);
    }

    private void coloringTextFill(boolean[] marks){

        if(marks[0]) textFieldInf.setStyle(styleCorrect);
        else  textFieldInf.setStyle(styleIncorrect);

        if(marks[1]) textFieldThird.setStyle(styleCorrect);
        else textFieldThird.setStyle(styleIncorrect);

        if(marks[2]) textFieldPrat.setStyle(styleCorrect);
        else  textFieldPrat.setStyle(styleIncorrect);

        if(marks[3]) textFieldP2.setStyle(styleCorrect);
        else  textFieldP2.setStyle(styleIncorrect);

    }

    private void resetTextFillStyle(){
        textFieldInf.setStyle(null);
        textFieldThird.setStyle(null);
        textFieldPrat.setStyle(null);
        textFieldP2.setStyle(null);
    }

    private void updateCnts(){
        int summarySize = game.getCntVerbsList();
        labelCnt.setText(globalCnt + "\\" + summarySize);
        labelAnswerCnt.setText(answerCnt + "\\" + (wrongCnt));
    }

    private void setLabelMessage(String message){ setLabelMessage(message,null);}
    private void setLabelMessage(String message, String textStyle){
        labelMessage.setText(message);
        labelMessage.setStyle(textStyle);
    }

    private void openStatistic(){
        myModel.openStatistic();
    }

}
