package com.panshul.grook.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.grook.Adapter.HistoryAdapter;
import com.panshul.grook.Model.UserHistoryModel;
import com.panshul.grook.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<UserHistoryModel> list1;
    DatabaseReference myref,myref1;

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
        list1 = new ArrayList<>();
        myref = FirebaseDatabase.getInstance().getReference("History").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        addData();


        return view;
    }
    public void addData(){
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot ds :snapshot.getChildren()){
                    UserHistoryModel model = ds.getValue(UserHistoryModel.class);
                    list1.add(model);
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
    }
}