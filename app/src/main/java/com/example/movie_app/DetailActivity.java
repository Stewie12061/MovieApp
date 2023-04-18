package com.example.movie_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_app.Adapter.EpisodeAdapter;
import com.example.movie_app.Adapter.TrailerDetailAdapter;
import com.example.movie_app.Database.DataLocalManager;
import com.example.movie_app.Model.CastCrew;
import com.example.movie_app.Adapter.CastCrewAdapter;
import com.example.movie_app.Model.Episode;
import com.example.movie_app.Model.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    // using set value to continue video
    public VideoView videoView;
    public String cUri, cEpisodeTitle = "";

    private MediaController mediaController;
    private ImageView mTrailerMovie;
    private TextView mMovieName, mIn4, mCategory, mEpisode, mSynopsis;
    private ImageButton backButton, favoriteButton;
    private RecyclerView rcvCast, rcvEpisode, rcvTrailer;
    private CastCrewAdapter castCrewAdapter;
    private EpisodeAdapter episodeAdapter;
    private TrailerDetailAdapter trailerDetailAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference realtimeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://movie-app-eb471-default-rtdb.firebaseio.com/users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initUi();
        // main uid movie
        String FuidMovie = getIntent().getStringExtra("Fuid");
        onClickButtonBack();

        setUpDetail(FuidMovie);
        setUpEpisode(FuidMovie);
        setUpCast(FuidMovie);

        onClickFavoriteButton(FuidMovie);

        Map<String, Object> continuteWatching = new HashMap<>();
        continuteWatching.put("Cuid", FuidMovie);
        continuteWatching.put("Cname", mMovieName.getText());
        continuteWatching.put("Cfavorite", getIntent().getStringExtra("Ffavorite"));
        continuteWatching.put("Ctrailer", getIntent().getStringExtra("Ftrailer"));
        continuteWatching.put("CcurentTime", videoView.getCurrentPosition());
        continuteWatching.put("Cduration ", videoView.getDuration());
        realtimeRef.child(DataLocalManager.getUserUid()).child("continue").child(FuidMovie).setValue(continuteWatching);
    }

    private void onClickFavoriteButton(String FuidMovie){
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> movie = new HashMap<>();
                movie.put("Fuid", FuidMovie);
                movie.put("Fname", getIntent().getStringExtra("Fname"));
                movie.put("Ffavorite", getIntent().getStringExtra("Ffavorite"));
                movie.put("Ftrailer", getIntent().getStringExtra("Ftrailer"));
                realtimeRef.child(DataLocalManager.getUserUid()).child("favorite").child(FuidMovie).setValue(movie);
            }
        });
    }

    private void setUpCast(String FuidMovie){
        DocumentReference castRef = db.collection("cast").document(FuidMovie);
        castRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        List<CastCrew> castCrewList = new ArrayList<>();
                        ArrayList<Map<String, Object>> data = (ArrayList<Map<String, Object>>) document.get(FuidMovie);
                        for(Map map : data){
                            CastCrew castCrew = new CastCrew(map.get("imageUrl").toString(),
                                    map.get("nameCast").toString());
                            castCrewList.add(castCrew);
                        }
                        setUpCastRecyclerView(castCrewList);
                    }
                }
            }
        });
    }

    private void setUpEpisode(String FuidMovie){
        DocumentReference episodeRef = db.collection("episode").document(FuidMovie);
        episodeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        List<Episode> episodeList = new ArrayList<>();
                        ArrayList<Map<String, Object>> data = (ArrayList<Map<String, Object>>) document.get(FuidMovie);
                        for(Map map : data){
                            Episode dataEpisode = new Episode(map.get("nameMovie").toString(),
                                    map.get("imageEpisode").toString(),
                                    map.get("episodeTitle").toString(),
                                    map.get("playMovie").toString());
                            episodeList.add(dataEpisode);
                        }
                        setUpEpisodeRecyclerView(episodeList);
                    }
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

    private void setUpDetail(String FuidMovie){
        DocumentReference movieRef = db.collection("movie").document(FuidMovie);
        movieRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Movie moviePage = documentSnapshot.toObject(Movie.class);
                mMovieName.setText(moviePage.getFname());
                mSynopsis.setText(moviePage.getFsynopsis());
                mCategory.setText(moviePage.toStringCategory());
                mIn4.setText(moviePage.toStringIn4());
                mEpisode.setText(moviePage.toStringSeasonEpisode());
                setUpVideoView(moviePage.getFvideoTrailer());
            }
        });
    }

    private void onClickButtonBack(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeFullActivity();
            }
        });
    }

    private void initUi(){
        videoView = (VideoView) findViewById(R.id.video_view);
        mTrailerMovie = (ImageView) findViewById(R.id.detail_trailer_image);
        mMovieName = (TextView) findViewById(R.id.detail_movie_name);
        mIn4 = (TextView) findViewById(R.id.detail_genre);
        mSynopsis = (TextView) findViewById(R.id.detail_synopsis_about);
        rcvCast = (RecyclerView) findViewById(R.id.detail_cast_rcv);
        backButton = (ImageButton) findViewById(R.id.button_back_detail);
        favoriteButton = (ImageButton) findViewById(R.id.favorite_button);
        mCategory = (TextView) findViewById(R.id.detail_category);
        rcvEpisode = (RecyclerView) findViewById(R.id.detail_episode_rcv);
        rcvTrailer = (RecyclerView) findViewById(R.id.rcv_trailer);
        mEpisode = (TextView) findViewById(R.id.title_episode);
    }

    void setUpTrailerRecyclerView(List<Movie> movie2List){
        trailerDetailAdapter = new TrailerDetailAdapter(this, movie2List);
        rcvTrailer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvTrailer.setHasFixedSize(true);
        rcvTrailer.setAdapter(trailerDetailAdapter);
    }

    void setUpEpisodeRecyclerView(List<Episode> episodeList){
        episodeAdapter = new EpisodeAdapter(this, episodeList);
        rcvEpisode.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvEpisode.setHasFixedSize(true);
        rcvEpisode.setAdapter(episodeAdapter);
    }

    void setUpCastRecyclerView(List<CastCrew> list){
                castCrewAdapter = new CastCrewAdapter(this, list);
        rcvCast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvCast.setHasFixedSize(true);
        rcvCast.setAdapter(castCrewAdapter);
    }

    public void onHomeFullActivity(){
        Intent intent = new Intent(this, HomeFullActivity.class);
        startActivity(intent);
    }
}
