package com.example.movie_app.Fragment;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.movie_app.AccountActivity;
import com.example.movie_app.Database.DataLocalManager;
import com.example.movie_app.Model.HelperClass;
import com.example.movie_app.Model.Movie;
import com.example.movie_app.R;
import com.example.movie_app.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private TextView username, email;
    private RecyclerView collectionRecyclerView;
    private Button buttonMovieList, buttonWatched, buttonMyReview, buttonSetting, buttonLogout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public AccountFragment() {
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi(view);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent signInActivity = new Intent(getContext(), SignInActivity.class);
                signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInActivity);
            }
        });

        String currentUserId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (task.isSuccessful()){
                    String emailFetch = documentSnapshot.getString("email");
                    String usernameFetch = documentSnapshot.getString("username");
                    username.setText(usernameFetch);
                    email.setText(emailFetch);
                }
            }
        });
    }

    private void initUi(View view) {
        username = (TextView) view.findViewById(R.id.username_profile);
        email = (TextView) view.findViewById(R.id.email_profile);
        buttonMovieList = (Button) view.findViewById(R.id.profile_button_animelist);
        buttonWatched = (Button) view.findViewById(R.id.profile_button_watched);
        buttonMyReview = (Button) view.findViewById(R.id.profile_button_my_review);
        buttonSetting = (Button) view.findViewById(R.id.profile_button_setting);
        buttonLogout = (Button) view.findViewById(R.id.profile_button_logout);
    }

    private void onClickButtonSetting() {
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountActivity.class);
                startActivity(intent);
            }
        });
    }

}