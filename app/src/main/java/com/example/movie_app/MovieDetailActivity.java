package com.example.movie_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.movie_app.Adapter.CastCrewAdapter;
import com.example.movie_app.Adapter.FavoriteAdapter;
import com.example.movie_app.Adapter.MovieAdapter;
import com.example.movie_app.Model.CastCrew;
import com.example.movie_app.Model.Favorite;
import com.example.movie_app.Model.Movies;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private VideoView videoView;
    private TextView movieName, movieDes;
    private RecyclerView rvCast;
    private MediaController mediaController;
    ImageButton backBtn, favBtn;
    String movieImage;
    List<CastCrew> castCrewList;

    private CollectionReference favoriteCollectionRef;
    Boolean checkIsInFavorite = false;

    String movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getStringExtra("movieId");
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        favoriteCollectionRef = db.collection("users").document(currentUserId).collection("favorite");

        backBtn = findViewById(R.id.button_back_detail);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoView = findViewById(R.id.movie_trailer);
        movieName = findViewById(R.id.movie_name);
        movieDes = findViewById(R.id.movie_description);

        rvCast = findViewById(R.id.rvCast);
        rvCast.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));


        getDataMovieDetail();
        checkIfMovieIsFavorite(movieId);
        favBtn = findViewById(R.id.fav_button);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIsInFavorite==true){
                    favoriteCollectionRef.document(movieId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MovieDetailActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        }
                    });
                    favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_24);
                    checkIsInFavorite=false;
                }else {
                    addMovieToFavorites(movieId);
                    favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_filled_24);
                    checkIsInFavorite=true;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCastData();
    }

    private void getCastData(){
        castCrewList = new ArrayList<>();
        DocumentReference moviesReference = db.collection("casts").document(movieId);
        moviesReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        List<Map<String,String>> listItem = (List<Map<String, String>>) documentSnapshot.get(movieId);
                        for (Map<String,String> item : listItem){
                            String name = item.get("castName");
                            String url = item.get("castUrl");
                            CastCrew cast = new CastCrew(name, url);
                            castCrewList.add(cast);
                        }
                        CastCrewAdapter adapter = new CastCrewAdapter(castCrewList);
                        rvCast.setAdapter(adapter);
                    }
                });
    }
    private void checkIfMovieIsFavorite(String movieId) {
        favoriteCollectionRef.document(movieId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            checkIsInFavorite=true;
                            favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_filled_24);
                        } else {
                            checkIsInFavorite=false;
                            favBtn.setBackgroundResource(R.drawable.ic_baseline_grade_24);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error getting movie from favorites", e);
                    }
                });
    }
    private void addMovieToFavorites(String movieId) {
        // Create a new document in Firebase Firestore with the movie ID as the document ID
        Map<String, Object> movie = new HashMap<>();
        movie.put("favName", movieName.getText().toString());
        movie.put("favImage", movieImage);
        movie.put("favDescription", movieDes.getText().toString());
        movie.put("movieId", movieId);

        favoriteCollectionRef.document(movieId)
                .set(movie)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MovieDetailActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MovieDetailActivity.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void getDataMovieDetail(){
        DocumentReference documentReference = db.collection("movies").document(movieId);
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()){
                                String name = documentSnapshot.getString("movieName");
                                String description = documentSnapshot.getString("movieDescription");
                                String movieUrl = documentSnapshot.getString("movieWatchLink");

                                //set url for video
                                setUpVideoView(movieUrl);
                                movieName.setText(name);
                                movieDes.setText(description);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void setUpVideoView(String url){
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}