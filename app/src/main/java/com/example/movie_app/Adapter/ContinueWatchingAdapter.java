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
import com.example.movie_app.Model.ContinueWatching;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.R;

import java.util.List;

public class ContinueWatchingAdapter extends RecyclerView.Adapter<ContinueWatchingAdapter.ContinueWatchingViewHolder>{
    
    private Context context;
    private List<ContinueWatching> continueWatchingLists;

    public ContinueWatchingAdapter(Context context, List<ContinueWatching> continueWatchingLists) {
        this.context = context;
        this.continueWatchingLists = continueWatchingLists;
    }

    @NonNull
    @Override
    public ContinueWatchingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_continue_watching,parent,false);
        return new ContinueWatchingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContinueWatchingViewHolder holder, int position) {
        final ContinueWatching movie = continueWatchingLists.get(position);
        if(movie == null) return;
        // Load image url
        Glide.with(holder.imageMovie).load(movie.getCtrailer()).into(holder.imageMovie);
        holder.titleMovie.setText(movie.getCname());
        holder.episode.setText(movie.getCfavorite());
        holder.imageMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("Fuid", movie.getCuid());
                intent.putExtra("Fname", movie.getCname());
                intent.putExtra("Ftrailer", movie.getCtrailer());
                intent.putExtra("Ffavorite", movie.getCfavorite());
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
        return continueWatchingLists != null ? continueWatchingLists.size() : 0;
    }

    public class ContinueWatchingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageMovie;
        TextView titleMovie, episode, timeMovie;
        public ContinueWatchingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.continue_image_movie);
            timeMovie = itemView.findViewById(R.id.continue_in4);
            titleMovie = itemView.findViewById(R.id.continue_movie_name);
            episode = itemView.findViewById(R.id.continue_in4);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
