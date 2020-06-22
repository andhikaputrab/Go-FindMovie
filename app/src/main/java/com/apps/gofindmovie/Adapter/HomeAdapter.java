package com.apps.gofindmovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.gofindmovie.Activity.DetailActivity;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.model.Movie;
import com.bumptech.glide.Glide;

import java.util.List;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 07 june 2020
*/

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public HomeAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.title.setText(movieList.get(position).getOriginalTitle());

        String vote = Double.toString(movieList.get(position).getVoteAverage());
        holder.userRating.setText(vote);

        Glide.with(context)
                .load(movieList.get(position).getPosterPath())
                .placeholder(R.drawable.load)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, userRating;
        ImageView thumbnail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            userRating = itemView.findViewById(R.id.user_rating);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            Movie selectedDataItem = movieList.get(pos);
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra("id", movieList.get(pos).getId());
                            intent.putExtra("original_title", movieList.get(pos).getOriginalTitle());
                            intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                            intent.putExtra("overview", movieList.get(pos).getOverview());
                            intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
                            intent.putExtra("release_date", movieList.get(pos).getReleaseDate());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            Toast.makeText(v.getContext(), selectedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        private boolean isNetworkAvailable(){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}
