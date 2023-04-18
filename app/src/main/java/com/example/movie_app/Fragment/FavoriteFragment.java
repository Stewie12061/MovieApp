package com.example.movie_app.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movie_app.Adapter.FavoriteAdapter;
import com.example.movie_app.Adapter.MovieAdapter;
import com.example.movie_app.Database.DataLocalManager;
import com.example.movie_app.Model.Favorite;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.Model.Movies;
import com.example.movie_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment{

    private FavoriteAdapter favoriteAdapter;
    private RecyclerView favoriteRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference favoriteCollectionRef;
    public FavoriteFragment() {
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        favoriteCollectionRef = db.collection("users").document(currentUserId).collection("favorite");
        favoriteRecyclerView = (RecyclerView) view.findViewById(R.id.rvFav);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        getFavoriteDate();

    }

    @Override
    public void onStart() {
        super.onStart();
        getFavoriteDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavoriteDate();
    }

    public void getFavoriteDate(){
        List<Favorite> favoritesList = new ArrayList<>();
        favoriteCollectionRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Favorite favorite = documentSnapshot.toObject(Favorite.class);
                                favoritesList.add(favorite);
                            }
                            favoriteAdapter = new FavoriteAdapter(favoritesList);
                            favoriteRecyclerView.setAdapter(favoriteAdapter);
                            favoriteAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}