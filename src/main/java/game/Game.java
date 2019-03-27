package game;

import game.Exception.*;

import java.util.*;


public class Game {
    private Dictionary dictionary;
    private GameSetting setting;
    private ArrayList<Verb> listVerbs = new ArrayList<>();
    private ArrayList<Verb> nextPortion = new ArrayList<>();
    private boolean[] selectedThemes;
    private String selectedLeters;
    private final String APLH = "abcdefghijklmnopqrstuvwxyzäöüß";

    private boolean isReady = false;
    private boolean waitAnswer = false;

    private int gameCursor = 0;

    public Game(Dictionary dictionary, GameSetting setting) {
        this.dictionary = dictionary;
        this.setting = setting;
    }

    public Game(Dictionary dictionary) {
        this.dictionary = dictionary;
        loadDefSett();
    }


    public boolean isEnd() {
        return  gameCursor >= listVerbs.size();
    }

    public int getCntVerbsList(){
        return listVerbs.size();
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setSetting(GameSetting setting) {
        this.setting = setting;
    }

    public GameSetting getSetting() {
        return setting;
    }

    public Dictionary getClonedDictinary(){
        return dictionary.clone();
    }

    private void loadDefSett(){
        this.setting = new GameSetting(
                GameSetting.TypeGroup.NAN,
                GameSetting.TypeSort.RANDOM,
                GameSetting.OpenWord.RANDOM
        );

        selectedLeters = new String(APLH);
        selectedThemes = new boolean[dictionary.getNameTypes().size()];
        Arrays.fill(selectedThemes, true);
    }

    public String[] getThemesNamesArr(){
        return Arrays.copyOf(dictionary.getNameTypes().toArray(), dictionary.getNameTypes().size(), String[].class);
    }

    public void selectThemes(boolean[] selectedNumThemes){
        if(selectedNumThemes.length != selectedThemes.length){
            throw new IllegalArgumentException("Error select Themes: wrong input lenght: " + selectedNumThemes.length);
        }
        this.selectedThemes = selectedNumThemes;
    }
    public void selectLeters(String selectedLetters) {
        this.selectedLeters = selectedLetters;
    }
    public void deselectLetters(String unselectedLetters){
        for (int i = 0; i < unselectedLetters.length(); i++) {
            String letter = ((Character)unselectedLetters.charAt(i)).toString();
            selectedLeters = selectedLeters.replace(letter, "");
        }
    }

    public void selectLetter(char letter, boolean isSelect){

        letter = Character.toLowerCase(letter);

        if(!isSelect) {
            deselectLetters(((Character)letter).toString());
            return;
        }
        if(isSelect){
            for (int i = 0; i < selectedLeters.length(); i++) {
                if(selectedLeters.charAt(i) == letter) return;
            }
            selectedLeters += letter;
        }
    }
    public void selectTheme(String nameTheme, boolean isSelect){
        int index = dictionary.getNameTypes().indexOf(nameTheme);
        if(index == -1) throw new IllegalArgumentException("Error name of Theme");
        selectedThemes[index] = isSelect;
    }


    public void selectAllThemes(){
        Arrays.fill(selectedThemes, true);
    }

    public void selectAllLetters(){
        selectedLeters = new String(APLH);
    }

    public void selectAll(){
        selectAllLetters();
        selectAllThemes();
    }

    public void startGame() throws GameArgumentException {

        if(dictionary.getSummarySize() == 0){
            throw new GameArgumentException("ERROR START GAME: EMPTY DICTIONARY");
        }

        cursor = 0;
        gameCursor = -1;
        listVerbs.clear();
        updateSetting();
        ArrayList<Verb> group = new ArrayList<>();
        while (getNextGroup(group)){
            sortList(group);
            listVerbs.addAll(group);
        }
        removeUnselectedVerbs();
       // fillCurrentPortion();
        isReady = true;
        waitAnswer = false;

    }

    public void resumeGame() throws GameArgumentException {
        if(nextPortion.size() == 0) {
            startGame();
            return;
        }

        cursor = 0;
        gameCursor = -1;
        updateSetting();
        listVerbs.clear();

        ArrayList<Verb> group = new ArrayList<>();
        while (getNextGroup(group)){
            for (int i = 0; i < group.size(); i++) {
                if(!nextPortion.contains(group.get(i))){
                    group.remove(group.get(i));
                    i--;
                }
            }
            sortList(group);
            listVerbs.addAll(group);
        }

        nextPortion.clear();
        isReady = true;
        waitAnswer = false;

    }

    private void updateSetting() {
    }

    public Verb nextVerb() throws GameNextVerbException, EndGame {
        if(!isReady) throw new GameNextVerbException("ERROR GAME: NEED FIRST STARTING!");
        if(waitAnswer) throw new GameNextVerbException("ERROR GAME: WAIT ANSWER!");

        gameCursor++;

        if(gameCursor >= listVerbs.size()) {
           throw new EndGame("game ended");
        }

        Verb nextVerb = Verb.createEmptyVerb();
        int positionOpenWord = setting.openWordtoPosition();
        if(setting.getOpenWord() == GameSetting.OpenWord.RANDOM){
            positionOpenWord = new Random().nextInt(3) + 1;
        }
        copyPartOfVerb(
                listVerbs.get(gameCursor),
                nextVerb,
                positionOpenWord);

        waitAnswer = true;
        return nextVerb;
    }

    public boolean checkVerb(Verb inputVerb){

        Verb rightVerb = listVerbs.get(gameCursor);
        waitAnswer = false;

        if(!inputVerb.equals(rightVerb)) {
            nextPortion.add(rightVerb);
            return false;
        }
        return true;
    }
    public boolean[] getMarks(Verb inputVerb){
        boolean[] marks = new boolean[4];
        Arrays.fill(marks,true);
        Verb rightVerb = listVerbs.get(gameCursor);

        if(!inputVerb.getInf()  .equals(rightVerb.getInf()))      marks[0] = false;
        if(!inputVerb.getThird().equals(rightVerb.getThird()))  marks[1] = false;
        if(!inputVerb.getPrat() .equals(rightVerb.getPrat()))    marks[2] = false;
        if(!inputVerb.getP2()   .equals(rightVerb.getP2()))        marks[3] = false;

        waitAnswer = false;
        return marks;
    }
    public Verb getRightVerb() throws GameCheckVerbException {
        if(waitAnswer) throw new GameCheckVerbException("FIRST CHECK VERB!");
        return listVerbs.get(gameCursor);
    }

    private int cursor = 0;
    private boolean getNextGroup(ArrayList<Verb> nextGroup){


        if(setting.getTypeGroup() == GameSetting.TypeGroup.LETTERS
                && cursor == APLH.length()){
            return false;
        }
        if(cursor == dictionary.getSummarySize()) return false;

        nextGroup.clear();
        if(setting.getTypeGroup() == GameSetting.TypeGroup.NAN){
            for (Verb verb : dictionary) {
                nextGroup.add(verb);
                cursor++;
            }
            return true;
        }

        if(setting.getTypeGroup() == GameSetting.TypeGroup.TYPE){
            String nameType = dictionary.getTypeByIndex(cursor);
            dictionary.getListByType(nameType).forEach(verb -> {
                nextGroup.add(verb);
                cursor++;
            });
            return true;
        }
        if(setting.getTypeGroup() == GameSetting.TypeGroup.LETTERS){
            Character letter = APLH.charAt(cursor);
            dictionary.forEach(verb -> {
                if(verb.getInf().charAt(0) == letter) {
                    nextGroup.add(verb);
                }
            });
            cursor++;

            return true;
        }

        return false;
    }

    private void sortList(ArrayList<Verb> sortedList){
        if(setting.getTypeSort() == GameSetting.TypeSort.RANDOM){
            Collections.shuffle(sortedList);
        }
        if(setting.getTypeSort() == GameSetting.TypeSort.ALPHABET){
            sortedList.sort((v1, v2) ->  v1.getInf().compareTo(v2.toString()));
        }
    }
    private void removeUnselectedVerbs(){
        for (int i = 0; i < listVerbs.size(); i++) {
            Verb candidat = listVerbs.get(i);
            int firstLetter = candidat.getInf().charAt(0);
            int indexThema = dictionary.getNameTypes().indexOf(
                    dictionary.getTypeByIndex(dictionary.indexOf(candidat))
            );

            if( !selectedThemes[indexThema] ||  selectedLeters.indexOf(firstLetter) == -1)  {
                listVerbs.remove(i);
                i--;
            }
        }
    }

    private void copyPartOfVerb(Verb src, Verb op, int position){
        if( position == 1)  op.setInf(src.getInf());
        if( position == 2)  op.setThird(src.getThird());
        if( position == 3)  op.setPrat(src.getPrat());
        if( position == 4)  op.setP2(src.getP2());
    }

    public Dictionary[] getStatistic() throws GameProcessException {
        if(!isEnd()) throw new GameProcessException("First end game to get Statistic");

        Dictionary right = new Dictionary();
        Dictionary wrong = new Dictionary();

        for (Verb verb : listVerbs) {
            String type = dictionary.getTypeByIndex(dictionary.indexOf(verb));
            if(nextPortion.contains(verb)){
                wrong.addVerb(verb, type);
            }else{
                right.addVerb(verb,type);
            }
        }

        return new Dictionary[] {right, wrong};
    }


}


