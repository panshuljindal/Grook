package com.panshul.grook.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.grook.Adapter.GroundAdapter;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView homeRecyclerView;
    List<GroundModel> list1;
    ImageView searchImageView,refresh,cancel;
    ConstraintLayout searchCl,homeCl;
    LottieAnimationView animationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_home, container, false);
        list1 = new ArrayList<>();
        homeRecyclerView= view.findViewById(R.id.homeRecyclerView);
        searchImageView  = view.findViewById(R.id.homeSearch);
        refresh = view.findViewById(R.id.homeRefresh);
        cancel = view.findViewById(R.id.homeCancel);
        searchCl = view.findViewById(R.id.searchCl);
        animationView = view.findViewById(R.id.homeAnimationView);
        homeCl = view.findViewById(R.id.homeCL);
        animationView.setVisibility(View.VISIBLE);
        homeCl.setVisibility(View.INVISIBLE);
        onClick();
        addData();
        loadData();
        adapter();
        return view;
    }
    void onClick(){
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImageView.setVisibility(View.GONE);
                refresh.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                searchCl.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImageView.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                searchCl.setVisibility(View.GONE);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }
    private void addData() {
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.matchup.userdata",MODE_PRIVATE);
        String city = pref.getString("city","delhi").toLowerCase();
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Ground");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    try {
                        GroundModel model = ds.getValue(GroundModel.class);
                        if (model.getGcity().toLowerCase().equals(city.toLowerCase())){
                            list1.add(model);
                        }
                    }
                    catch (Exception e){

                    }
                }
                saveData();
                adapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void saveData(){

    }
    public void loadData(){

    }
    public void adapter(){
        GroundAdapter adapter = new GroundAdapter(view.getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        homeRecyclerView.setLayoutManager(manager);
        homeRecyclerView.setAdapter(adapter);
        if (list1.isEmpty()){
            homeCl.setVisibility(View.INVISIBLE);
            animationView.setVisibility(View.VISIBLE);

        }
        else {
            homeCl.setVisibility(View.VISIBLE);
            animationView.pauseAnimation();
            animationView.setVisibility(View.INVISIBLE);
        }
    }
}