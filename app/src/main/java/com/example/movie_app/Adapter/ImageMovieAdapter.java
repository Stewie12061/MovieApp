package com.example.movie_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_app.DetailActivity;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.R;

import java.util.List;

public class ImageMovieAdapter extends  RecyclerView.Adapter<ImageMovieAdapter.ImageMovieViewHolder>{
    Context context;
    List<Movie> imageMovies;

    public void setData(List<Movie> list){
        this.imageMovies = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new ImageMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageMovieViewHolder holder, int position) {
        final Movie movie = imageMovies.get(position);
        if(movie == null) return;
        // Load image url
        Glide.with(holder.imageMovie).load(movie.getFthumb()).into(holder.imageMovie);
        holder.titleMovie.setText(movie.getFname());
        holder.genreMovie.setText(movie.toStringCategory());
        holder.imageMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("Fuid", movie.getFuid());
                intent.putExtra("Fname", movie.getFname());
                intent.putExtra("Ftrailer", movie.getFtrailer());
                intent.putExtra("Ffavorite", movie.toStringCategory());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
        if(position == getItemCount() - 1) {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.rightMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 25, holder.itemView.getResources().getDisplayMetrics());
        }
    }

    @Override
    public int getItemCount() {
        return imageMovies != null ? imageMovies.size() : 0;
    }

    public class ImageMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageMovie;
        TextView titleMovie, genreMovie;
        public ImageMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.image_movie);
            titleMovie = itemView.findViewById(R.id.title_item_movie);
            genreMovie = itemView.findViewById(R.id.genre_item_movie);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
