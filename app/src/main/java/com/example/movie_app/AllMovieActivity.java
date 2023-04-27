package com.example.movie_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.movie_app.Adapter.AllMovieAdapter;
import com.example.movie_app.Adapter.MovieAdapter;
import com.example.movie_app.Model.Favorite;
import com.example.movie_app.Model.Movies;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.List;

public class AllMovieActivity extends AppCompatActivity {

    RecyclerView rvMovieAll;
    Button btnAZ, btnZA, btnBack;

    FirebaseFirestore db;
    private CollectionReference favoriteCollectionRef;


    AllMovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movie);
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String currentUserId = sharedPreferences.getString("userId", "");
        favoriteCollectionRef = db.collection("users").document(currentUserId).collection("favorite");

        rvMovieAll = findViewById(R.id.rvMovieAll);
        btnAZ = findViewById(R.id.btnSortAZ);
        btnZA = findViewById(R.id.btnSortZA);
        rvMovieAll.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        btnAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllMovieAZ();
            }
        });
        btnZA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllMovieZA();
            }
        });
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllMovieAZ();
    }

    private void getAllMovieZA(){
        List<Movies> moviesList = new ArrayList<>();
        List<String> favoriteIds = new ArrayList<>();
        Query moviesReference = db.collection("movies").orderBy("movieName", Query.Direction.DESCENDING);
        moviesReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Movies movies = documentSnapshot.toObject(Movies.class);
                                moviesList.add(movies);
                            }
                            favoriteCollectionRef
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                String id = document.getId();
                                                favoriteIds.add(id);
                                            }
                                            movieAdapter = new AllMovieAdapter(getApplicationContext(),moviesList,favoriteIds);
                                            rvMovieAll.setAdapter(movieAdapter);
                                        }
                                    });
                        }else {
                            Toast.makeText(getApplicationContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getAllMovieAZ(){
        List<Movies> moviesList = new ArrayList<>();
        List<String> favoriteIds = new ArrayList<>();
        Query moviesReference = db.collection("movies").orderBy("movieName", Query.Direction.ASCENDING);
        moviesReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Movies movies = documentSnapshot.toObject(Movies.class);
                                moviesList.add(movies);
                            }
                            favoriteCollectionRef
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                String id = document.getId();
                                                favoriteIds.add(id);
                                            }
                                            movieAdapter = new AllMovieAdapter(getApplicationContext(),moviesList,favoriteIds);
                                            rvMovieAll.setAdapter(movieAdapter);
                                        }
                                    });


                        }else {
                            Toast.makeText(getApplicationContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}