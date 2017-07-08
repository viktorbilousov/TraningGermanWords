package game;

import java.util.*;

/**
 * Created by Viktor on 07.07.17.
 */
public class Game {
    private Dictionary dictionary;
    private GameSetting setting;
    private ArrayList<Verb> listVerbs = new ArrayList<>();
    private boolean[] selectedThemes;
    private String selectedLeters;
    private final String APLH = " abcdefghijklmnopqrstuvwxyzäöüß";

    private boolean isReady = false;
    private boolean waitAnswer = false;
    private boolean isChecked = false;


    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        selectedThemes = new boolean[dictionary.nameTypes.size()];
        Arrays.fill(selectedThemes, true);
    }

    public void setSetting(GameSetting setting) {
        this.setting = setting;
    }

    public GameSetting getSetting() {
        return setting;
    }

    public Game(Dictionary dictionary, GameSetting setting) {
        this.dictionary = dictionary;
        this.setting = setting;
    }

    public Game(Dictionary dictionary) {
        this.dictionary = dictionary;
        loadDefSett();
    }


    private void loadDefSett(){
        this.setting = new GameSetting(
                GameSetting.TypeGroup.NAN,
                GameSetting.TypeSort.RANDOM,
                GameSetting.OpenWord.RANDOM
        );

        selectedLeters = new String(APLH);
        selectedThemes = new boolean[dictionary.nameTypes.size()];
        Arrays.fill(selectedThemes, true);
    }

    public String[] getThemesNamesArr(){
        return Arrays.copyOf(dictionary.nameTypes.toArray(), dictionary.nameTypes.size(), String[].class);
    }

    public void selectThemes(boolean[] selectedNumThemes){
        if(selectedNumThemes.length != selectedThemes.length){
            throw new IllegalArgumentException("Error select Themes: wrong input lenght: " + selectedNumThemes.length);
        }
        this.selectedThemes = selectedNumThemes;
    }

    public void selectLeters(String selectedLeters) {
        this.selectedLeters = selectedLeters;
    }
    public void unselectLeters(String unselectedLeters){
        for (int i = 0; i < unselectedLeters.length(); i++) {
            selectedLeters.replace(((Character)unselectedLeters.charAt(i)).toString(), "");
        }
    }

    public void selectAllThemes(){
        Arrays.fill(selectedThemes, true);
    }

    public void selectAllLeters(){
        selectedLeters = new String(APLH);
    }

    public void selectAll(){
        selectAllLeters();
        selectAllThemes();
    }

    public void startGame(){
        cursor = 0;
        gameCursor = 0;
        if(dictionary.getSummarySize() == 0){
            throw new IllegalArgumentException("ERROR START GAME: EMPTY DICTIONARY");
        }
        ArrayList<Verb> group = new ArrayList<>();
        while (getNextGroup(group)){
            sortGroup(group);
            listVerbs.addAll(group);
        }
        isReady = true;
        waitAnswer = false;
    }

    private int gameCursor = -1;
    public Verb nextVerb() throws IllegalAccessException {
        if(!isReady) throw new IllegalAccessException("ERROR GAME: NEED FIRST STARTING!");
        if(waitAnswer) throw new IllegalAccessException("ERROR GAME: WAIT ANSWER!");
        gameCursor ++ ;

        Verb nextVerb = new Verb("","","", "");
        int position = setting.openWordtoPosition();
        if(setting.getOpenWord() == GameSetting.OpenWord.RANDOM){
            position = new Random().nextInt(3) + 1;
        }
        copyPartOfVerb(listVerbs.get(gameCursor), nextVerb, position);
        waitAnswer = true;
        isChecked = false;
        return nextVerb;
    }

    public boolean checkVerb(Verb inputVerb){
        Verb rightVerb = listVerbs.get(gameCursor);
        if(!inputVerb.getInf().equals(rightVerb.getInf())) return false;
        if(!inputVerb.getThird().equals(rightVerb.getThird())) return false;
        if(!inputVerb.getPrat().equals(rightVerb.getPrat())) return false;
        if(!inputVerb.getP2().equals(rightVerb.getP2())) return false;
        waitAnswer = false;
        isChecked = true;
        return true;
    }

    public boolean[] getMarks(Verb inputVerb){
        boolean[] marks = new boolean[4];
        Arrays.fill(marks,true);
        Verb rightVerb = listVerbs.get(gameCursor);

        if(!inputVerb.getInf().equals(rightVerb.getInf())) marks[0] = false;
        if(!inputVerb.getThird().equals(rightVerb.getThird())) marks[1] = false;
        if(!inputVerb.getPrat().equals(rightVerb.getPrat())) marks[2] = false;
        if(!inputVerb.getP2().equals(rightVerb.getP2())) marks[3] = false;

        waitAnswer = false;
        isChecked = true;
        return marks;
    }

    public Verb getRightVerb() throws IllegalAccessException {
        if(!isChecked) throw new IllegalAccessException("FIRST CHECK VERB!");
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
        }

        return false;
    }
    private void sortGroup(ArrayList<Verb> sortedList){
        if(setting.getTypeSort() == GameSetting.TypeSort.RANDOM){
            Collections.shuffle(sortedList);
        }
        if(setting.getTypeSort() == GameSetting.TypeSort.ALPHABET){
            sortedList.sort((v1, v2) ->  v1.getInf().compareTo(v2.toString()));
        }
    }

    private void copyPartOfVerb(Verb src, Verb op, int position){
        if( position == 1)  op.setInf(src.getInf());
        if( position == 2)  op.setThird(src.getThird());
        if( position == 3)  op.setPrat(src.getPrat());
        if( position == 4)  op.setP2(src.getP2());
    }

}


