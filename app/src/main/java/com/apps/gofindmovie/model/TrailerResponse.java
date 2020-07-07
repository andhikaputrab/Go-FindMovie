package com.apps.gofindmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 11 june 2020
*/


public class TrailerResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
