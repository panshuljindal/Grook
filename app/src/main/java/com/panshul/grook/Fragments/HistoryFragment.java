package com.panshul.grook.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.grook.Adapter.HistoryAdapter;
import com.panshul.grook.Model.HistoryGround;
import com.panshul.grook.Model.HistoryGroundModel;
import com.panshul.grook.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<HistoryGroundModel> list1;
    String city;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        list1 = new ArrayList<>();
        firebase();

        return view;
    }
    public  void firebase(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.matchup.userdata",MODE_PRIVATE);
        String uid = pref.getString("uid","");
        city = pref.getString("city","delhi").toLowerCase();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("History").child("Users").child(uid);
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    HistoryGround model = ds.getValue(HistoryGround.class);
                    DatabaseReference myref1 = db.getReference("Ground").child(city).child(model.getGid());
                    myref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String pic= snapshot.child("gpic").getValue().toString();
                            String name = snapshot.child("gname").getValue().toString();
                            String address = snapshot.child("gaddress").getValue().toString();
                            String rating = snapshot.child("grating").getValue().toString();
                            list1.add(new HistoryGroundModel(pic,name,address,model.getSname(),
                                    model.getDate() + "-" +model.getTime(),rating));
                            //Log.i("size",list1.toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                adapter();
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