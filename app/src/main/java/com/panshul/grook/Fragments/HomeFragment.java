package com.panshul.grook.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.grook.Activity.MainActivity;
import com.panshul.grook.Adapter.GroundAdapter;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView homeRecyclerView;
    List<GroundModel> list1,searchList;
    ImageView searchImageView,refresh,cancel;
    ConstraintLayout searchCl,homeCl;
    LottieAnimationView animationView;
    EditText search;
    TextView noBookings,noGround;
    boolean isEmpty;
    int filterOption;
    Button option;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_home, container, false);
        list1 = new ArrayList<>();
        filterOption=0;
        homeRecyclerView= view.findViewById(R.id.homeRecyclerView);
        searchImageView  = view.findViewById(R.id.homeSearch);
        refresh = view.findViewById(R.id.homeRefresh);
        cancel = view.findViewById(R.id.homeCancel);
        searchCl = view.findViewById(R.id.searchCl);
        animationView = view.findViewById(R.id.homeAnimationView);
        homeCl = view.findViewById(R.id.homeCL);
        animationView.setVisibility(View.VISIBLE);
        homeCl.setVisibility(View.INVISIBLE);
        noBookings = view.findViewById(R.id.textView20);
        search = view.findViewById(R.id.searchEditText);
        option = view.findViewById(R.id.filterOption);
        noGround = view.findViewById(R.id.textView24);
        isEmpty = true;

        onClick();
        addData();
        loadData();
        adapter();
        return view;
    }
    void onClick(){
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), option);
                popupMenu.getMenuInflater().inflate(R.menu.option1,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.toString().equals("Ground Name")){
                            filterOption=0;
                            search.setHint("Search by Ground Name");
                        }
                        else if(item.toString().equals("Ground Locality")){
                            filterOption=1;
                            search.setHint("Search by Ground Locality");
                        }
                        else if (item.toString().equals("Ground Address")){
                            filterOption=2;
                            search.setHint("Search by Ground Address");
                        }
                        else {
                            filterOption=0;
                            search.setHint("Search by Ground Name");
                            noBookings.setText("No Grounds found");
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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
                animationView.playAnimation();
                animationView.setVisibility(View.VISIBLE);
                homeCl.setVisibility(View.INVISIBLE);
                addData();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                noBookings.setVisibility(View.VISIBLE);
                searchList = new ArrayList<>();
                if (filterOption==0){
                    for (GroundModel horsePool:list1){
                        if (horsePool.getGname().toLowerCase().contains(s.toString().toLowerCase())){
                            searchList.add(horsePool);
                        }
                    }
                }
                else if (filterOption==1){
                    for (GroundModel horsePool:list1){
                        if (horsePool.getGlocality().toLowerCase().contains(s.toString().toLowerCase())){
                            searchList.add(horsePool);
                        }
                    }
                    if (searchList.isEmpty()){
                        noBookings.setText("No Grounds found in the "+s.toString());
                    }
                }
                else if (filterOption==2){
                    for (GroundModel horsePool:list1){
                        if (horsePool.getGaddress().toLowerCase().contains(s.toString().toLowerCase())){
                            searchList.add(horsePool);
                        }
                    }
                    if (searchList.isEmpty()){
                        noBookings.setText("No Grounds found in the "+s.toString());
                    }
                }
                GroundAdapter adapter = new GroundAdapter(view.getContext(),searchList);
                LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
                manager.setOrientation(RecyclerView.VERTICAL);
                homeRecyclerView.setLayoutManager(manager);
                homeRecyclerView.setAdapter(adapter);
            }
        });
    }
    private void addData() {
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.grook.userdata", MODE_PRIVATE);
        String city=pref.getString("city", "");
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
            isEmpty=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isEmpty==false){
                        noBookings.setVisibility(View.INVISIBLE);
                    }
                    else {
                        noBookings.setText("No Grounds found");
                        homeCl.setVisibility(View.VISIBLE);
                        animationView.setVisibility(View.INVISIBLE);
                        animationView.pauseAnimation();
                        noBookings.setVisibility(View.VISIBLE);
                    }
                }
            },2000);
        }
        else {
            isEmpty=false;
            homeCl.setVisibility(View.VISIBLE);
            animationView.setVisibility(View.INVISIBLE);
            animationView.pauseAnimation();
        }
    }
}