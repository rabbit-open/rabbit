package com.supets.pet.mock.ui.translate;

import android.support.annotation.Keep;

@Keep
public class SortModel  {

    public String name;//名字

    public String sortLetters;//字母

    public boolean isLast = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
