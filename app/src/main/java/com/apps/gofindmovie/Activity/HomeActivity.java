package com.apps.gofindmovie.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.gofindmovie.Adapter.HomeAdapter;
import com.apps.gofindmovie.Adapter.HomeAdapter2;
import com.apps.gofindmovie.Adapter.HomeAdapter3;
import com.apps.gofindmovie.BuildConfig;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.api.Client;
import com.apps.gofindmovie.api.Service;
import com.apps.gofindmovie.model.Movie;
import com.apps.gofindmovie.model.MovieResponse;
import com.apps.gofindmovie.utils.PaginationScrollListener;
import com.apps.gofindmovie.utils.SharedPreference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 07 june 2020
*/

public class HomeActivity extends AppCompatActivity{

    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    private HomeAdapter homeAdapter;
    private HomeAdapter2 homeAdapter2;
    private HomeAdapter3 homeAdapter3;
    private ProgressDialog progressDialog;
    private List<Movie> movieList, movieList2, movieList3;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGES = 10;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        initViews();
    }

    private void initViews(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Movies");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView3 = findViewById(R.id.recycler_view3);

        movieList = new ArrayList<>();
        movieList2 = new ArrayList<>();
        movieList3 = new ArrayList<>();
        homeAdapter = new HomeAdapter(this, movieList);
        homeAdapter2 = new HomeAdapter2(this, movieList2);
        homeAdapter3 = new HomeAdapter3(this, movieList3);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        recyclerView2.addOnScrollListener(new PaginationScrollListener(linearLayoutManager2) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage2();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        recyclerView3.addOnScrollListener(new PaginationScrollListener(linearLayoutManager3) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage3();
                    }
                },1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadJSON();
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadJSON(){
        try {
            Service apiService = Client.getClient(getApplicationContext()).create(Service.class);

            // JSON Popular Movies
            Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY, currentPage);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<Movie> movies = response.body().getResults();

                    recyclerView.setAdapter(homeAdapter);
                    homeAdapter.addAll(movies);
                    if (currentPage <= TOTAL_PAGES) {
                        homeAdapter.addLoadingFooter();
                    }
                    else {
                        isLastPage = true;
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

            // JSON Top Rated Movies
            Call<MovieResponse> call2 = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY,currentPage);
            call2.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<Movie> movies2 = response.body().getResults();

                    recyclerView2.setAdapter(homeAdapter2);
                    homeAdapter2.addAll(movies2);
                    if (currentPage <= TOTAL_PAGES) {
                        homeAdapter2.addLoadingFooter();
                    }
                    else {
                        isLastPage = true;
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

            // JSON UpComing Movies
            Call<MovieResponse> call3 = apiService.getUpcomingMovies(BuildConfig.THE_MOVIE_DB_API_KEY,currentPage);
            call3.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<Movie> movies3 = response.body().getResults();

                    recyclerView3.setAdapter(homeAdapter3);
                    homeAdapter3.addAll(movies3);
                    if (currentPage <= TOTAL_PAGES){
                        homeAdapter3.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNextPage(){
        if (isNetworkAvailable()){
            try {
                Service apiService = Client.getClient(getApplicationContext()).create(Service.class);
                Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY, currentPage);

                // JSON MOST POPULAR MOVIES
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                        homeAdapter.removeLoadingFooter();
                        isLoading = false;

                        assert response.body() != null;
                        List<Movie> movies = response.body().getResults();
                        homeAdapter.addAll(movies);
                        if (currentPage <= TOTAL_PAGES) {
                            homeAdapter.addLoadingFooter();
                        }
                        else {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                        Log.e("Error", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){
                Log.d("Error", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("Error", "No Internet Connection");
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNextPage2(){
        if (isNetworkAvailable()){
            try {
                Service apiService = Client.getClient(getApplicationContext()).create(Service.class);
                Call<MovieResponse> call2 = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY,currentPage);

                // JSON MOST POPULAR MOVIES
                call2.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                        homeAdapter2.removeLoadingFooter();
                        isLoading = false;

                        assert response.body() != null;
                        List<Movie> movies = response.body().getResults();
                        homeAdapter2.addAll(movies);
                        if (currentPage <= TOTAL_PAGES) {
                            homeAdapter2.addLoadingFooter();
                        }
                        else {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                        Log.e("Error", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){
                Log.d("Error", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("Error", "No Internet Connection");
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNextPage3(){
        if (isNetworkAvailable()){
            try {
                Service apiService = Client.getClient(getApplicationContext()).create(Service.class);
                Call<MovieResponse> call2 = apiService.getUpcomingMovies(BuildConfig.THE_MOVIE_DB_API_KEY,currentPage);

                // JSON MOST POPULAR MOVIES
                call2.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                        homeAdapter3.removeLoadingFooter();
                        isLoading = false;

                        assert response.body() != null;
                        List<Movie> movies = response.body().getResults();
                        homeAdapter3.addAll(movies);
                        if (currentPage <= TOTAL_PAGES) {
                            homeAdapter3.addLoadingFooter();
                        }
                        else {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                        Log.e("Error", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){
                Log.d("Error", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("Error", "No Internet Connection");
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_exit) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar aplikasi");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda Yakin Ingin Keluar?")
                .setIcon(R.mipmap.ic_launcher_round)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    SharedPreference.setLogOut(getBaseContext());
                    finish();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    dialog.cancel();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}
