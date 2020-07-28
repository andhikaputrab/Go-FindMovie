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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public HomeAdapter2(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public HomeAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View view = inflater.inflate(R.layout.movie_card, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            case LOADING:
                View view2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new ViewHolder(view2);
                break;
        }
        assert viewHolder != null;
        return viewHolder;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter2.ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case ITEM:
                holder.title.setText(movieList.get(position).getOriginalTitle());

                String vote = Double.toString(movieList.get(position).getVoteAverage());
                holder.userRating.setText(vote);

                Glide.with(context)
                        .load(movieList.get(position).getPosterPath())
                        .placeholder(R.drawable.load)
                        .into(holder.thumbnail);
                break;

            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    /*public void add(Movie r) {
        movieList.add(r);
    }*/

    public void addAll(@NotNull List<Movie> movieResults) {
        /*for (Movie result : movieResults) {
            add(result);
        }*/
        movieList.addAll(movieResults);
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        movieList.add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieList.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView title, userRating;
        final ImageView thumbnail;

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
