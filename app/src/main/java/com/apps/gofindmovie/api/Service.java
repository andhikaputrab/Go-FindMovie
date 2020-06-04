package com.apps.gofindmovie.api;

import com.apps.gofindmovie.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 30 may 2020
 */

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);



}
