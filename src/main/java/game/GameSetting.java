package game;

import static game.GameSetting.OpenWord.*;

/**
 * Created by Viktor on 07.07.17.
 */
public final class GameSetting {
    public enum TypeSort {
        ALPHABET,
        RANDOM,
    }
    public enum TypeGroup {
        NAN,
        TYPE,
        LETTERS
    }
    public enum OpenWord{
        INFINITIVE,
        THIRD_FORM,
        PRÄTERITUM,
        PARTIZIP_2,
        RANDOM
    }

    private TypeGroup typeGroup;
    private TypeSort typeSort;
    private OpenWord openWord;

    public GameSetting(TypeGroup typeGroup, TypeSort typeSort, OpenWord openWord) {
        this.typeGroup = typeGroup;
        this.typeSort = typeSort;
        this.openWord = openWord;
    }

    public TypeGroup getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(TypeGroup typeGroup) {
        this.typeGroup = typeGroup;
    }

    public TypeSort getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(TypeSort typeSort) {
        this.typeSort = typeSort;
    }

    public OpenWord getOpenWord() {
        return openWord;
    }

    public void setOpenWord(OpenWord openWord) {
        this.openWord = openWord;
    }

    public int openWordtoPosition(){
        if(openWord == INFINITIVE) return 1;
        if(openWord == THIRD_FORM) return 2;
        if(openWord == PRÄTERITUM) return 3;
        if(openWord == PARTIZIP_2) return 4;
        return -1;
    };
}