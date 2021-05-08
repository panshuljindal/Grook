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

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private SmoothBottomBar smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smoothBottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);
        smoothBottomBar.bringToFront();
        smoothBottomBar.setOnItemSelectedListener(i -> {
            Fragment selectedFragment = null;
            switch (i){
                case 0:
                    selectedFragment = new HomeFragment();
                    setTitle("Home");
                    break;
                case 1:
                    selectedFragment = new HistoryFragment();
                    setTitle("History");
                    break;
                case 2:
                    selectedFragment = new ProfileFragment();
                    setTitle("Profile");
                    break;
            }
            //Frag Transaction
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        });
        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }
}