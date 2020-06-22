package com.apps.gofindmovie.api;

import com.apps.gofindmovie.Activity.HomeActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 30 may 2020
 */

public class Client {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(HomeActivity.okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
