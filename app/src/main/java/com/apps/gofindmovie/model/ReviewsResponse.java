package com.apps.gofindmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("id")
    private Integer id_movies;
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<Reviews> results;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public int getId_movies() {
        return id_movies;
    }

    public void setId_movies(int id_movies) {
        this.id_movies = id_movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Reviews> getResults() {
        return results;
    }

    public void setResults(List<Reviews> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
