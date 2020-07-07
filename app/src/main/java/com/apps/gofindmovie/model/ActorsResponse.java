package com.apps.gofindmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 12 june 2020
*/


public class ActorsResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<Actors> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Actors> getCast() {
        return cast;
    }

    public void setCast(List<Actors> cast) {
        this.cast = cast;
    }
}
