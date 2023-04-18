package com.example.movie_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_app.DetailActivity;
import com.example.movie_app.Model.Favorite;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.MovieDetailActivity;
import com.example.movie_app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {
    private FirebaseFirestore db;
    private CollectionReference favoriteCollectionRef;
    private List<Favorite> favoriteList;

    public FavoriteAdapter(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent,false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        favoriteCollectionRef = db.collection("users").document(currentUserId).collection("favorite");

        Favorite favorite = favoriteList.get(position);
        holder.nameMovie.setText(favorite.getFavName());
        Picasso.get().load(favorite.getFavImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieId",favorite.getMovieId());
                view.getContext().startActivity(intent);
            }
        });
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteCollectionRef.document(favorite.getMovieId())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                favoriteList.remove(favorite);
                                notifyDataSetChanged();
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameMovie;
        ImageButton favBtn;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_fav);
            nameMovie = itemView.findViewById(R.id.name_fav);
            favBtn = itemView.findViewById(R.id.fav_button);
        }
    }
}
