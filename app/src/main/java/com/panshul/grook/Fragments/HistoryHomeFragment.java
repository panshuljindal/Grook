package com.panshul.grook.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.grook.Activity.GroundActivity;
import com.panshul.grook.Adapter.PreviousAdapter;
import com.panshul.grook.Model.AllHistoryModel;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.Model.UserHistoryModel;
import com.panshul.grook.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryHomeFragment extends Fragment {

    View view;
    UserHistoryModel user;
    ImageView image;
    ArrayList<AllHistoryModel> list;
    TextView name,address,sport,timing,closed,bookedBy,previousName,previousDate;
    RecyclerView recyclerView;
    ConstraintLayout cl,toGround;
    LottieAnimationView animation;
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
        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.panshul.grook.userdata", Context.MODE_PRIVATE);
        String json = pref.getString("history","");
        list = new ArrayList<>();
        //Log.i("json",json);
        findViewByID();

        cl.setVisibility(View.INVISIBLE);
        animation.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        Type type = new TypeToken<UserHistoryModel>() {}.getType();
        user = gson.fromJson(json,type);
        Glide.with(view.getContext()).load(user.getGpic2()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        }).into(image);
        name.setText(user.getGname());
        address.setText(user.getAddress());
        sport.setText(user.getGsport());
        timing.setText(user.getGtiming());
        closed.setText(user.getGclosed());
        bookedBy.setText("Booked by 1 User");
        previousName.setText(pref1.getString("name",""));
        previousDate.setText(user.getSport() + "  •  "+unixconvert(user.getDate())+"  •  "+user.getSlot());

        addData();
        toGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGround.setEnabled(false);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ground").child(user.getGid());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        GroundModel model = snapshot.getValue(GroundModel.class);
                        String json = gson.toJson(model);
                        Intent i = new Intent(v.getContext(), GroundActivity.class);
                        i.putExtra("ground",json);
                        toGround.setEnabled(false);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        toGround.setEnabled(false);
                        Toast.makeText(view.getContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM yyyy").format(df);
        return vv;
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
        cl = view.findViewById(R.id.historyHomeCl);
        animation = view.findViewById(R.id.historyHomeAnimationView);
        toGround = view.findViewById(R.id.groundToActivity);
        previousName = view.findViewById(R.id.historyPreviousName);
        previousDate=view.findViewById(R.id.historyPreviousDate);
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
                            if (user.getSlot().equals(model.getSlot())){
                                if (user.getDate().equals(model.getDate())){
                                    if (user.getSport().equals(model.getSport())){
                                        list.add(model);
                                    }
                                }

                            }
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
        cl.setVisibility(View.VISIBLE);
        animation.setVisibility(View.INVISIBLE);
        animation.pauseAnimation();
    }
}