package com.example.movie_app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.movie_app.Adapter.MovieAdapter;
import com.example.movie_app.AllMovieActivity;
import com.example.movie_app.Model.Movies;
import com.example.movie_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    FirebaseFirestore db;
    RecyclerView rvMovieAction,rvCartoonMovie, rvHorrorMovie, rvRomanceMovie;
    MovieAdapter movieActionAdapter, movieCartoonAdapter, movieHorrorAdapter, movieRomanceAdapter;

    Button btnShowAll;

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        imageSlider = view.findViewById(R.id.advertise);

        rvMovieAction = view.findViewById(R.id.rvActionMovie);
        rvMovieAction.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        rvCartoonMovie = view.findViewById(R.id.rvCartoonMovie);
        rvCartoonMovie.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        rvHorrorMovie = view.findViewById(R.id.rvHorrorMovie);
        rvHorrorMovie.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        rvRomanceMovie = view.findViewById(R.id.rvRomanceMovie);
        rvRomanceMovie.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        btnShowAll = view.findViewById(R.id.btnShowALl);
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AllMovieActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        getDetailImgSlider();
        getActionMovieData();
        getCartoonMovieData();
        getHorrorMovieData();
        getRomanceMovieData();
    }
    private void getActionMovieData(){
        List<Movies> moviesList = new ArrayList<>();
        CollectionReference moviesReference = db.collection("movies");
        moviesReference.whereEqualTo("movieCateId","1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Movies movies = documentSnapshot.toObject(Movies.class);
                                moviesList.add(movies);
                            }
                            movieActionAdapter = new MovieAdapter(moviesList,getContext());
                            rvMovieAction.setAdapter(movieActionAdapter);
                        }else {
                            Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getCartoonMovieData(){
        List<Movies> moviesList2 = new ArrayList<>();
        CollectionReference moviesReference = db.collection("movies");
        moviesReference.whereEqualTo("movieCateId","2").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Movies movies = documentSnapshot.toObject(Movies.class);
                                moviesList2.add(movies);
                            }
                            movieCartoonAdapter = new MovieAdapter(moviesList2,getContext());
                            rvCartoonMovie.setAdapter(movieCartoonAdapter);
                        }else {
                            Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getHorrorMovieData(){
        List<Movies> moviesList3 = new ArrayList<>();
        CollectionReference moviesReference = db.collection("movies");
        moviesReference.whereEqualTo("movieCateId","3").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Movies movies = documentSnapshot.toObject(Movies.class);
                                moviesList3.add(movies);
                            }
                            movieHorrorAdapter = new MovieAdapter(moviesList3,getContext());
                            rvHorrorMovie.setAdapter(movieHorrorAdapter);
                        }else {
                            Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getRomanceMovieData(){
        List<Movies> moviesList4 = new ArrayList<>();
        CollectionReference moviesReference = db.collection("movies");
        moviesReference.whereEqualTo("movieCateId","4").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Movies movies = documentSnapshot.toObject(Movies.class);
                                moviesList4.add(movies);
                            }
                            movieRomanceAdapter = new MovieAdapter(moviesList4,getContext());
                            rvRomanceMovie.setAdapter(movieRomanceAdapter);
                        }else {
                            Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getDetailImgSlider(){
        List<SlideModel> slideModelArrayList = new ArrayList<>();
        CollectionReference movieSliderReference = db.collection("movieSlider");
        movieSliderReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot  documentSnapshot : queryDocumentSnapshots){
                    slideModelArrayList.add(new SlideModel(documentSnapshot.getString("url"), ScaleTypes.FIT));
                    imageSlider.setImageList(slideModelArrayList, ScaleTypes.FIT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}