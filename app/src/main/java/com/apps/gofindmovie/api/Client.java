package com.apps.gofindmovie.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apps.gofindmovie.Activity.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 08 june 2020
 */

public class Client {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;
    private static int cacheSize = 10 * 1024 * 1024;

    public static Retrofit getClient(Context context){

        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        if (!isNetworkAvailable(context)){
                            int maxStale = 60 * 60 * 24 * 28;
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "Public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                }).build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
