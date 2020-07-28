package com.apps.gofindmovie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.gofindmovie.BuildConfig;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.api.Client;
import com.apps.gofindmovie.api.Service;
import com.apps.gofindmovie.model.InfoActors;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActors extends AppCompatActivity {

    CircularImageView imageActors;
    TextView txtName, txtBirthday, txtBio, txtPlace;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_actors);

        int id_actors = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).getInt("id"));

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar2);
        imageActors = findViewById(R.id.image_actors);
        txtName = findViewById(R.id.txtNameActors);
        txtBirthday = findViewById(R.id.txtBirthday);
        txtBio = findViewById(R.id.txtBio);
        txtPlace = findViewById(R.id.txtPlace);

        try {
            Service apiService = Client.getClient(getApplicationContext()).create(Service.class);
            Call<InfoActors> call = apiService.getInfoActors(id_actors, BuildConfig.THE_MOVIE_DB_API_KEY);
            call.enqueue(new Callback<InfoActors>() {
                @Override
                public void onResponse(@NotNull Call<InfoActors> call, @NotNull Response<InfoActors> response) {
                    assert response.body() != null;
                    String image_actors = response.body().getProfile_path();
                    String name = response.body().getName();
                    String birthday = response.body().getBirthday();
                    String biography = response.body().getBiography();
                    String place = response.body().getPlace_of_birth();

                    Glide.with(getApplicationContext())
                            .load(image_actors)
                            .placeholder(R.drawable.load)
                            .into(imageActors);

                    txtName.setText(name);

                    SimpleDateFormat caledarFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

                    try {
                        String date = newDateFormat.format(Objects.requireNonNull(caledarFormat.parse(birthday)));
                        if (birthday.equals("")){
                            txtBirthday.setText("-");
                            txtBirthday.setGravity(Gravity.CENTER);
                        } else {
                            txtBirthday.setText(date);
                        }
                    } catch (Exception e){
                        Log.d("Error", Objects.requireNonNull(e.getMessage()));
                    }

                    if (biography.equals("")){
                        txtBio.setText("-");
                        txtBio.setGravity(Gravity.CENTER);
                    } else {
                        txtBio.setText(biography);
                    }

                    if (txtPlace.equals("")){
                        txtPlace.setText("-");
                    } else {
                        txtPlace.setText(place);
                    }

                    collapsingToolbarLayout.setTitle(name);
                }

                @Override
                public void onFailure(@NotNull Call<InfoActors> call, @NotNull Throwable t) {
                    Log.e("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(DetailActors.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e){
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}