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
import com.panshul.grook.Adapter.GroundAdapter;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView groundrecyclerView;
    List<GroundModel> list1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);

        list1 = new ArrayList<>();
        groundrecyclerView=(RecyclerView)view.findViewById(R.id.slotrecyclerView);
        loadTopGrounds();

        return view;
    }
    private void loadTopGrounds() {
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.matchup.userdata",MODE_PRIVATE);
        String city = pref.getString("city","delhi").toLowerCase();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("Ground").child(city);
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    GroundModel model = ds.getValue(GroundModel.class);
                    list1.add(model);
                }
                adapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter(){
        GroundAdapter adapter = new GroundAdapter(view.getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        groundrecyclerView.setLayoutManager(manager);
        groundrecyclerView.setAdapter(adapter);
    }
}