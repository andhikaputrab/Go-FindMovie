package com.apps.gofindmovie.model;

import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("id")
    private int idReviews;
    @SerializedName("url")
    private String url;

    public Reviews(String author, String content, int idReviews, String url) {
        this.author = author;
        this.content = content;
        this.idReviews = idReviews;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return idReviews;
    }

    public void setId(int idReviews) {
        this.idReviews = idReviews;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
