package com.example.movie_app.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.movie_app.DetailActivity;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<Movie> movieSliders;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<Movie> movieSliders, ViewPager2 viewPager2) {
        this.movieSliders = movieSliders;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container, parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        final Movie movie = movieSliders.get(position);
        Glide.with(holder.imageView).load(movie.getFtrailer()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return movieSliders.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
    }
}