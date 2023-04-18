package com.example.movie_app.Adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_app.Model.Movies;
import com.example.movie_app.MovieDetailActivity;
import com.example.movie_app.R;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder> {

    List<Movies> searchMovieList;

    public SearchMovieAdapter(List<Movies> searchMovieList) {
        this.searchMovieList = searchMovieList;
    }

    @NonNull
    @Override
    public SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_movie, parent,false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieViewHolder holder, int position) {
        Movies movie = searchMovieList.get(position);
        Glide.with(holder.imageView).load(movie.getMovieImage()).into(holder.imageView);
        holder.nameMovie.setText(movie.getMovieName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieId",movie.getMovieId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchMovieList.size();
    }

    public class SearchMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView nameMovie;
        public SearchMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_search);
            nameMovie = itemView.findViewById(R.id.item_search_name_movie);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

