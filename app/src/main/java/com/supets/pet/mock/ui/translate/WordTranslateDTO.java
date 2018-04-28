package com.supets.pet.mock.ui.translate;

import java.util.ArrayList;

public class WordTranslateDTO {

    public String word_name;
    public int is_CRI;
    public exchange exchange;
    public ArrayList<symbols> symbols;
    public String[] items;

    public static class exchange {
        public String[] word_pl;
        public String word_past;
        public String word_done;
        public String word_ing;
        public String word_third;
        public String word_er;
        public String word_est;
    }


    public static class symbols {
        public String ph_en;
        public String ph_am;
        public String ph_other;
        public String ph_en_mp3;
        public String ph_am_mp3;
        public String ph_tts_mp3;
        public ArrayList<parts> parts;

    }

    public static class parts {
        public String part;
        public String[] means;

    }

    public String getsymbols() {

        StringBuffer sb = new StringBuffer();

        if (symbols != null) {
            for (int i = 0; i < symbols.size(); i++) {

                symbols ss = symbols.get(i);
                sb.append("");
                sb.append("美式发音 [");
                sb.append(ss.ph_am);
                sb.append("]  ");
                sb.append("<a href='" + ss.ph_am_mp3 + "'>播放</a> <br/><br/>");
                sb.append("英式发音 [");
                sb.append(ss.ph_am);
                sb.append("]  ");
                sb.append("<a href='" + ss.ph_am_mp3 + "'>播放</a> <br/><br/>");

                if (ss.parts != null) {

                    for (int m = 0; m < ss.parts.size(); m++) {

                        parts pa = ss.parts.get(m);

                        sb.append(pa.part).append("  ");

                        if (pa.means != null) {
                            for (int j = 0; j < pa.means.length; j++) {
                                sb.append(pa.means[j]);
                                if (pa.means.length != j + 1) {
                                    sb.append(";");
                                }
                            }
                        }
                        sb.append("<br/>");
                    }

                }
            }
        }

        return sb.toString();
    }


}
