package com.example.movie_app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movie_app.Fragment.AccountFragment;
import com.example.movie_app.Fragment.FavoriteFragment;
import com.example.movie_app.Fragment.HomeFragment;
import com.example.movie_app.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeFullActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefull);

        display(R.id.nav_home);
        navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                display(item.getItemId());
                return true;
            }
        });

    }
    void display(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_search:
                fragment = new SearchFragment();
                break;
            case R.id.nav_favourite:
                fragment = new FavoriteFragment();
                break;
            case R.id.nav_account:
                fragment = new AccountFragment();
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.contain, fragment);
        ft.commit();
    }

}
