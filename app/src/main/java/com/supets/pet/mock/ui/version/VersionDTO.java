package com.supets.pet.mock.ui.version;

public class VersionDTO {

    public int code;
    public String alert;
    public Version  content;

    public static class Version{
        public String url;
        public String text;
        public int version;
    }

}
