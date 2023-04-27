package com.example.movie_app.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movie_app.Model.Movies;
import com.example.movie_app.MovieDetailActivity;
import com.example.movie_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllMovieAdapter extends RecyclerView.Adapter<AllMovieAdapter.MovieVH> {

    private FirebaseFirestore db;

    Boolean checkIsInFavorite;
    Context context;
    List<Movies> moviesList;
    List<String> favoriteIds;
    CollectionReference favoriteCollectionRef;
    boolean isFavorite;

    public AllMovieAdapter(Context context, List<Movies> moviesList,List<String> favoriteIds) {
        this.context = context;
        this.moviesList = moviesList;
        this.favoriteIds = favoriteIds;
    }

    @NonNull
    @Override
    public AllMovieAdapter.MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new AllMovieAdapter.MovieVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMovieAdapter.MovieVH holder, int position) {
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String currentUserId = sharedPreferences.getString("userId", "");
        favoriteCollectionRef = db.collection("users").document(currentUserId).collection("favorite");
        Movies movies = moviesList.get(position);
        holder.nameMovie.setText(movies.getMovieName());

        // Use Glide library to load the image from the URL into the ImageView
        Picasso.get().load(movies.getMovieImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieId",movies.getMovieId());
                v.getContext().startActivity(intent);
            }
        });

        isFavorite = favoriteIds.contains(movies.getMovieId());

        if (isFavorite) {
            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_filled_24);
        } else {
            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_24);
        }

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteCollectionRef
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                favoriteIds = new ArrayList<>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    String id = document.getId();
                                    favoriteIds.add(id);
                                }
                                isFavorite = favoriteIds.contains(movies.getMovieId());
                                if (isFavorite){
                                    favoriteCollectionRef.document(movies.getMovieId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                                            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_24);
                                        }
                                    });
                                }else {
                                    Map<String, Object> movie = new HashMap<>();
                                    movie.put("favName", movies.getMovieName());
                                    movie.put("favImage", movies.getMovieImage());
                                    movie.put("favDescription", movies.getMovieDescription());
                                    movie.put("movieId", movies.getMovieId());

                                    favoriteCollectionRef.document(movies.getMovieId())
                                            .set(movie)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_filled_24);
                                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MovieVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameMovie;
        ImageButton favBtn;
        public MovieVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_fav);
            nameMovie = itemView.findViewById(R.id.name_fav);
            favBtn = itemView.findViewById(R.id.fav_button);
        }
    }
}
