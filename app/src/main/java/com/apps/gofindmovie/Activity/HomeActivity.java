package com.apps.gofindmovie.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.apps.gofindmovie.Adapter.HomeAdapter;
import com.apps.gofindmovie.BuildConfig;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.SharedPreferences.SharedPreference;
import com.apps.gofindmovie.api.Client;
import com.apps.gofindmovie.api.Service;
import com.apps.gofindmovie.model.Movie;
import com.apps.gofindmovie.model.MovieResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private HomeAdapter homeAdapter;
    private List<Movie> movieList;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String LOG_TAG = HomeAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        swipeRefreshLayout = findViewById(R.id.main_content);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(HomeActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Movies");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView3 = findViewById(R.id.recycler_view3);

        /*movieList = new ArrayList<>();
        homeAdapter = new HomeAdapter(this, movieList);
        recyclerViewTopRatedAdapter = new RecyclerViewTopRatedAdapter(this, movieList);

        if (getBaseContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }

        /*if (getBaseContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
        loadJSONMostPopular();

        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(recyclerViewTopRatedAdapter);
        recyclerViewTopRatedAdapter.notifyDataSetChanged();
        loadJSONTopRated();*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadJSONMostPopular();
        loadJSONTopRated();
        loadJSONUpcoming();
    }

    private void loadJSONMostPopular(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new HomeAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadJSONTopRated(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<Movie> movies = response.body().getResults();
                    recyclerView2.setAdapter(new HomeAdapter(getApplicationContext(), movies));
                    recyclerView2.smoothScrollToPosition(0);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONUpcoming(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getUpcomingMovies(BuildConfig.THE_MOVIE_DB_API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<Movie> movies = response.body().getResults();
                    recyclerView3.setAdapter(new HomeAdapter(getApplicationContext(), movies));
                    recyclerView3.smoothScrollToPosition(0);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;*/
            case R.id.menu_exit:
                showDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
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

    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG, "Preferences Updated");
        checkSortOrder();
    }

    private void checkSortOrder(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = sharedPreferences.getString(
                this.getString(R.string.sort_order_key),
                this.getString(R.string.sort_by_most_popular)
        );

        if (sortOrder.equals(this.getString(R.string.sort_by_most_popular))){
            Log.d(LOG_TAG, "Sorting by Most Popular Movies");
            loadJSON();

        } else if (sortOrder.equals(this.getString(R.string.sort_by_top_rated))){
            Log.d(LOG_TAG, "Sorting by Top Rated Movies");
            loadJSONTopRated();

        } else {
            Log.d(LOG_TAG, "Sorting by Upcoming Movies");
            loadJSONUpcoming();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (movieList.isEmpty()){
            checkSortOrder();
        }
    }*/
}
