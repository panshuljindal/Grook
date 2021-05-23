package com.panshul.grook.Fragments;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.grook.Adapter.HistoryAdapter;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.Model.UserHistoryModel;
import com.panshul.grook.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<UserHistoryModel> list1,searchList;
    DatabaseReference myref;
    ConstraintLayout searchCl,homeCl;
    LottieAnimationView animationView;
    boolean isEmpty;
    TextView noBookings;
    EditText search;
    ImageView searchImageView,refresh,cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.pastBookingsRecyclerView);
        searchCl = view.findViewById(R.id.historySearchCl);
        searchImageView = view.findViewById(R.id.historySearch);
        refresh = view.findViewById(R.id.historyRefresh);
        noBookings = view.findViewById(R.id.textView19);
        cancel = view.findViewById(R.id.historyCancel);
        search = view.findViewById(R.id.historySearchEditText);
        isEmpty = true;
        animationView = view.findViewById(R.id.historyAnimationView);
        homeCl = view.findViewById(R.id.historyCl);
        animationView.setVisibility(View.VISIBLE);
        homeCl.setVisibility(View.INVISIBLE);


        list1 = new ArrayList<>();
        myref = FirebaseDatabase.getInstance().getReference("History").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        addData();

        onclick();

        return view;
    }
    public void onclick(){
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.VISIBLE);
                searchImageView.setVisibility(View.INVISIBLE);
                searchCl.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                searchImageView.setVisibility(View.VISIBLE);
                searchCl.setVisibility(View.GONE);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();
                homeCl.setVisibility(View.INVISIBLE);
                addData();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchList=new ArrayList<>();
                for (UserHistoryModel model:list1){
                    if (model.getGname().toLowerCase().contains(s.toString().toLowerCase())){
                        searchList.add(model);
                    }
                }
                HistoryAdapter adapter = new HistoryAdapter(view.getContext(),searchList);
                LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
                manager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void addData(){
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noBookings.setVisibility(View.INVISIBLE);
                list1.clear();
                for (DataSnapshot ds :snapshot.getChildren()){
                   try {
                       UserHistoryModel model = ds.getValue(UserHistoryModel.class);
                       list1.add(model);

                   }catch (Exception e){

                   }
                }
                adapter();
                //Gson gson = new Gson();
                //Log.i("history",gson.toJson(list1));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter(){
        HistoryAdapter adapter = new HistoryAdapter(view.getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (list1.isEmpty()){
            isEmpty=true;
            homeCl.setVisibility(View.INVISIBLE);
            animationView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isEmpty==false){
                        noBookings.setVisibility(View.INVISIBLE);
                    }
                    else {
                        homeCl.setVisibility(View.VISIBLE);
                        animationView.setVisibility(View.INVISIBLE);
                        animationView.pauseAnimation();
                        noBookings.setVisibility(View.VISIBLE);
                    }
                }
            },5000);
        }
        else {
            isEmpty=false;
            homeCl.setVisibility(View.VISIBLE);
            animationView.setVisibility(View.INVISIBLE);
            animationView.pauseAnimation();
        }
    }
}