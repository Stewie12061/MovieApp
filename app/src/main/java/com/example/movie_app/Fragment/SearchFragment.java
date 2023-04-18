package com.example.movie_app.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.movie_app.Adapter.SearchMovieAdapter;
import com.example.movie_app.Model.Episode;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private SearchView searchView;
    private SearchMovieAdapter searchMovieAdapter;
    private RecyclerView searchRcv;
    private GridView gridView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        searchRcv = view.findViewById(R.id.rcv_search);
        searchView = (SearchView) view.findViewById(R.id.search_view);

        CollectionReference movieRef = db.collection("movie");
        movieRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Movie> list = new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Movie movie2 = documentSnapshot.toObject(Movie.class);
                        list.add(movie2);
                    }
                    setSearchRecyclerView(list);
                }
            }
        });
    }

    private void setSearchRecyclerView(List<Movie> list){
        searchMovieAdapter = new SearchMovieAdapter(getContext(), list);
        searchRcv.setHasFixedSize(true);
        searchRcv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        searchRcv.setAdapter(searchMovieAdapter);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovieAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovieAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private List<Episode> getEpisodes(){
        List<Episode> episodeList = new ArrayList<>();
        episodeList.add(new Episode(
                "Chainsaw man",
                "https://www.crunchyroll.com/imgsrv/display/thumbnail/1200x675/catalog/crunchyroll/91c8f9e4ddbcbcee7d8c12ace10e6dcf.jpe",
                "E1 - DOG & CHAINSAW",
                "1"));
        episodeList.add(new Episode(
                "Chainsaw man",
                "https://static.wikia.nocookie.net/chainsaw-man/images/0/07/Episode_2-1.png/revision/latest?cb=20221018090613",
                "E2 - ARRIVAL IN TOKYO",
                "1"));
        episodeList.add(new Episode(
                "Chainsaw man",
                "https://www.slashfilm.com/img/gallery/chainsaw-man-episode-3-is-an-action-packed-funny-and-gnarly/intro-1666735069.jpg",
                "E3 -  MEOWY'S WHEREABOUTS",
                "1"));
        return episodeList;
    }
}