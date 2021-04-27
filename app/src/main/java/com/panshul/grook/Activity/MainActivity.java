package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.panshul.grook.Fragments.HistoryFragment;
import com.panshul.grook.Fragments.HomeFragment;
import com.panshul.grook.Fragments.ProfileFragment;
import com.panshul.grook.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        selectedfragment = new HomeFragment();
                        break;
                    case R.id.navigation_history:
                        selectedfragment = new HistoryFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedfragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedfragment).commit();
                return  true;
            }
        });
    }
}