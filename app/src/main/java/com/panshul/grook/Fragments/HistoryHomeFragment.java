package com.panshul.grook.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.grook.Adapter.PreviousAdapter;
import com.panshul.grook.Model.AllHistoryModel;
import com.panshul.grook.Model.UserHistoryModel;
import com.panshul.grook.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoryHomeFragment extends Fragment {

    View view;
    UserHistoryModel user;
    ImageView image;
    ArrayList<AllHistoryModel> list;
    TextView name,address,sport,timing,closed,bookedBy;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_history_home, container, false);
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.grook.history", Context.MODE_PRIVATE);
        String json = pref.getString("history","");
        list = new ArrayList<>();
        //Log.i("json",json);
        findViewByID();
        Gson gson = new Gson();
        Type type = new TypeToken<UserHistoryModel>() {}.getType();
        user = gson.fromJson(json,type);
        Glide.with(view.getContext()).load(user.getGpic2()).into(image);
        name.setText(user.getGname());
        address.setText(user.getAddress());
        sport.setText(user.getGsport());
        timing.setText(user.getGtiming());
        closed.setText(user.getGclosed());
        bookedBy.setText("Booked by 1 User");
        addData();
        return view;
    }
    public void findViewByID(){
        image = view.findViewById(R.id.hsImage);
        name = view.findViewById(R.id.hsName);
        address = view.findViewById(R.id.hsAddress);
        sport = view.findViewById(R.id.hsSport);
        timing = view.findViewById(R.id.hsTiming);
        closed = view.findViewById(R.id.hsclosed);
        bookedBy = view.findViewById(R.id.hsBookedBy);
        recyclerView = view.findViewById(R.id.historyHomeRecyclerView);
    }
    public void addData(){
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("History").child("Ground").child(user.getGid());
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                try {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        AllHistoryModel model = ds.getValue(AllHistoryModel.class);
                        if (user.getBid().equals(model.getBid())){

                        }
                        else {
                            list.add(model);
                        }
                    }
                }
                catch (Exception e){

                }
                bookedBy.setText("Booked by "+String.valueOf(list.size()+1)+" Users");
                adapter();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    public void adapter(){
        PreviousAdapter adapter = new PreviousAdapter(list,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}