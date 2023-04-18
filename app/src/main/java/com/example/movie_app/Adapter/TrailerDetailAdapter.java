package com.example.movie_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.R;
import java.util.List;

public class TrailerDetailAdapter extends RecyclerView.Adapter<TrailerDetailAdapter.TrailerDetailViewHolder>{
    private Context context;
    private List<Movie> movieList;

    public TrailerDetailAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public TrailerDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer_detail,parent,false);
        return new TrailerDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerDetailViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        if(movie == null) return;
        Glide.with(holder.imageMovie).load(movie.getFtrailer()).into(holder.imageMovie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class TrailerDetailViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageMovie;
        public TrailerDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = (ImageView) itemView.findViewById(R.id.detail_trailer_image);
        }
    }
}
