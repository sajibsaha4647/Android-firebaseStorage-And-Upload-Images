package com.example.firebasestoragewithuploadimage.Modelclass;

public class ImageRetrive {

    private String key;
    private String value;

    public ImageRetrive(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
