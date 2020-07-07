package com.apps.gofindmovie.api;

import com.apps.gofindmovie.model.ActorsResponse;
import com.apps.gofindmovie.model.MovieResponse;
import com.apps.gofindmovie.model.ReviewsResponse;
import com.apps.gofindmovie.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 08 june 2020
*/

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<ActorsResponse> getActorsDetails(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMoviesTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
