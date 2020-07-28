package com.apps.gofindmovie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.gofindmovie.R;
import com.apps.gofindmovie.model.Reviews;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Reviews> reviewsList;
    private Context context;

    public ReviewsAdapter(Context context, List<Reviews> reviewsList){
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {

        holder.textAuthor.setText(reviewsList.get(position).getAuthor());

        holder.textReviews.setText(reviewsList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textAuthor, textReviews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textAuthor = itemView.findViewById(R.id.text_author);
            textReviews = itemView.findViewById(R.id.text_reviews);
        }
    }
}
