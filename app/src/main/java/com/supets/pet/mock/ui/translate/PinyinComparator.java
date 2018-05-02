package com.supets.pet.mock.ui.translate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

    public static void main(String[] args) {
        // 根据a-z进行排序源数据
        String[] date = new String[]{"你好啊", "A"};
        List<SortModel> list = CharacterParser.filledData(date);
        Collections.sort(list, new PinyinComparator());
    }


}
