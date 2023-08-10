package com.example.demo.Model;

public class CurrentCondition {

    /* Get image url for the corresponding icon & weather text */
    private String icon;
    private String text;

    /* Getter and Setter */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
