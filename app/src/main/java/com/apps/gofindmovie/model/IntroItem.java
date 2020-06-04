package com.apps.gofindmovie.model;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 27 may 2020
 */

public class IntroItem {

    private String Title, Description;
    private int ScreenImg;

    public IntroItem(String title, String description, int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
