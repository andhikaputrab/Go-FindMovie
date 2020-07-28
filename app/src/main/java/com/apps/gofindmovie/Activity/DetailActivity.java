package com.apps.gofindmovie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.gofindmovie.Adapter.ActorAdapter;
import com.apps.gofindmovie.Adapter.ReviewsAdapter;
import com.apps.gofindmovie.Adapter.TrailerAdapter;
import com.apps.gofindmovie.BuildConfig;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.api.Client;
import com.apps.gofindmovie.api.Service;
import com.apps.gofindmovie.model.Actors;
import com.apps.gofindmovie.model.ActorsResponse;
import com.apps.gofindmovie.model.Reviews;
import com.apps.gofindmovie.model.ReviewsResponse;
import com.apps.gofindmovie.model.Trailer;
import com.apps.gofindmovie.model.TrailerResponse;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 10 june 2020
*/

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitle, movieSynopsis, userRating, movieReleaseDate;
    private ImageView imageView;
    private RecyclerView recyclerViewTrailer, recyclerViewActor, recyclerViewReviews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.thumbnail_image_header);
        movieTitle = findViewById(R.id.title);
        movieSynopsis = findViewById(R.id.plotSynopsis);
        userRating = findViewById(R.id.user_rating);
        movieReleaseDate = findViewById(R.id.release_date);

        Intent intent = getIntent();
        if (intent.hasExtra("original_title")){
            String thumbnail = Objects.requireNonNull(getIntent().getExtras()).getString("poster_path");
            String movieName = getIntent().getExtras().getString("original_title");
            String synopsis = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String releaseDate = getIntent().getExtras().getString("release_date");

            Glide.with(this)
                    .load(thumbnail)
                    .placeholder(R.drawable.load)
                    .into(imageView);
            movieTitle.setText(movieName);
            movieSynopsis.setText(synopsis);
            userRating.setText(rating);

            SimpleDateFormat calendarFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());

            try {
                assert releaseDate != null;
                String date = newDateFormat.format(Objects.requireNonNull(calendarFormat.parse(releaseDate)));
                movieReleaseDate.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setTitle(movieName);
        } else {
            Toast.makeText(this, "No Api Data", Toast.LENGTH_SHORT).show();
        }

        initViews();
    }

    private void initViews(){
        recyclerViewTrailer = findViewById(R.id.recycler_view_trailer);
        recyclerViewActor = findViewById(R.id.recycler_view_actors);
        recyclerViewReviews = findViewById(R.id.recycler_view_reviews);

        recyclerViewTrailer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewActor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loadJSON();
    }

    private void loadJSON(){
        int movie_id = Objects.requireNonNull(getIntent().getExtras()).getInt("id");

        try {
            Service apiService = Client.getClient(getApplicationContext()).create(Service.class);

            // JSON Trailer Movies
            Call<TrailerResponse> call = apiService.getMoviesTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_KEY);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(@NotNull Call<TrailerResponse> call, @NotNull Response<TrailerResponse> response) {
                    assert response.body() != null;
                    List<Trailer> trailer = response.body().getResults();
                    recyclerViewTrailer.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                    recyclerViewTrailer.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NotNull Call<TrailerResponse> call, @NotNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(DetailActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

            // JSON Movies Actors
            Call<ActorsResponse> call2 = apiService.getActorsDetails(movie_id, BuildConfig.THE_MOVIE_DB_API_KEY);
            call2.enqueue(new Callback<ActorsResponse>() {
                @Override
                public void onResponse(@NotNull Call<ActorsResponse> call2, @NotNull Response<ActorsResponse> response) {
                    assert response.body() != null;
                    List<Actors> actors = response.body().getCast();
                    recyclerViewActor.setAdapter(new ActorAdapter(getApplicationContext(), actors));
                    recyclerViewActor.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NotNull Call<ActorsResponse> call2, @NotNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(DetailActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

            // JSON Movies Reviews
            Call<ReviewsResponse> call3 = apiService.getMoviesReviews(movie_id, BuildConfig.THE_MOVIE_DB_API_KEY);
            call3.enqueue(new Callback<ReviewsResponse>() {
                @Override
                public void onResponse(@NotNull Call<ReviewsResponse> call, @NotNull Response<ReviewsResponse> response) {
                    assert response.body() != null;
                    List<Reviews> reviews = response.body().getResults();
                    recyclerViewReviews.setAdapter(new ReviewsAdapter(getApplicationContext(), reviews));
                    recyclerViewReviews.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NotNull Call<ReviewsResponse> call, @NotNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}