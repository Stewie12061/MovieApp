package com.example.movie_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_app.Model.Movies;
import com.example.movie_app.MovieDetailActivity;
import com.example.movie_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private List<Movies> moviesList;
    Context context;

    public MovieAdapter(List<Movies> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movies movies = moviesList.get(position);
        holder.movieName.setText(movies.getMovieName());

        // Use Glide library to load the image from the URL into the ImageView
        Picasso.get().load(movies.getMovieImage()).into(holder.movieImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieId",movies.getMovieId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieName;

        MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);
        }
    }
}
