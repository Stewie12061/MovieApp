package com.example.movie_app.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.movie_app.Adapter.SearchMovieAdapter;
import com.example.movie_app.Model.Episode;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.Model.Movies;
import com.example.movie_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private SearchView searchView;
    private SearchMovieAdapter searchMovieAdapter;
    private RecyclerView searchRcv;
    private GridView gridView;
    private FirebaseFirestore db;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        searchRcv = view.findViewById(R.id.rcv_search);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText);
                return true;
            }
        });
    }
    private void searchMovies(String query) {
        Query movieQuery = db.collection("movies")
                .whereGreaterThanOrEqualTo("movieName", query)
                .whereLessThan("movieName", query + "\uf8ff")
                .orderBy("movieName");

        movieQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Movies> moviesList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Movies movie = document.toObject(Movies.class);
                        moviesList.add(movie);
                    }
                    searchMovieAdapter = new SearchMovieAdapter(moviesList);
                    searchRcv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    searchRcv.setAdapter(searchMovieAdapter);
                    searchMovieAdapter.notifyDataSetChanged();
                } else {
                    Log.e("SearchFragment", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}