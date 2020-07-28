package com.apps.gofindmovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.gofindmovie.Activity.DetailActors;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.model.Actors;
import com.apps.gofindmovie.model.InfoActors;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 12 june 2020
*/


public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {
    private Context context;
    private List<Actors> actorsList;

    public ActorAdapter(Context context, List<Actors> actorsList) {
        this.context = context;
        this.actorsList = actorsList;
    }

    @NonNull
    @Override
    public ActorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorAdapter.ViewHolder holder, int position) {
        holder.actorName.setText(actorsList.get(position).getName());

        holder.character.setText(actorsList.get(position).getCharacter());

        Glide.with(context)
                .load(actorsList.get(position).getProfile_path())
                .placeholder(R.drawable.load)
                .into(holder.actorImage);
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView actorName,character;
        CircularImageView actorImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            actorName = itemView.findViewById(R.id.text_actor_name);
            actorImage = itemView.findViewById(R.id.image_actor);
            character = itemView.findViewById(R.id.text_character);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Actors selectedDataItem = actorsList.get(pos);
                        Intent intent = new Intent(context, DetailActors.class);
                        intent.putExtra("id", actorsList.get(pos).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), selectedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
