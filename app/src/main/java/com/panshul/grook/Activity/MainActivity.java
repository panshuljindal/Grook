package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.grook.Fragments.HistoryFragment;
import com.panshul.grook.Fragments.HistoryHomeFragment;
import com.panshul.grook.Fragments.HomeFragment;
import com.panshul.grook.Fragments.ProfileFragment;
import com.panshul.grook.R;

import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private SmoothBottomBar smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //datasave();
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

    Boolean doubleback=false;
    @Override
    public void onBackPressed() {

        tellFragments();
    }
    private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof HistoryHomeFragment)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HistoryFragment()).commit();
            else if (f!=null && f instanceof ProfileFragment){
                HomeFragment home = new HomeFragment();
                FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                smoothBottomBar.setItemActiveIndex(0);
            }
            else if (f!=null && f instanceof HistoryFragment){
                HomeFragment home = new HomeFragment();
                FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                smoothBottomBar.setItemActiveIndex(0);
            }
            else if (f!=null && f instanceof HomeFragment){
                if (doubleback) {
                    //super.onBackPressed();
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    //Log.i("doubleback", doubleback.toString());
                } else {
                    doubleback = true;

                    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleback = false;
                            //Log.i("doubleback", doubleback.toString());
                        }
                    }, 2000);
                    //Log.i("doubleback", doubleback.toString());
                }
            }
        }
    }
}