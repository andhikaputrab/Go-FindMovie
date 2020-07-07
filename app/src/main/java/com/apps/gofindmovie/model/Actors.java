package com.apps.gofindmovie.model;

import com.google.gson.annotations.SerializedName;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 12 june 2020
*/


public class Actors {

    @SerializedName("id")
    private int id;
    @SerializedName("cast_id")
    private int cast_id;
    @SerializedName("character")
    private String character;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profile_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
