package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.grook.Adapter.DateAdapter;
import com.panshul.grook.Adapter.SlotAdapter;
import com.panshul.grook.Adapter.SportAdapter;
import com.panshul.grook.Model.BookingModel;
import com.panshul.grook.Model.DateModel;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.Model.SlotModel;
import com.panshul.grook.Model.SportModel;
import com.panshul.grook.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class GroundActivity extends AppCompatActivity {

    Button cancel,next;
    public static TextView rs;
    TextView name,address,sport,timing;
    RecyclerView dateR,sportR,slotR;
    ImageView image;
    DatabaseReference myref;
    GroundModel ground;
    public static BookingModel booking;
    ArrayList<DateModel> dateList;
    ArrayList<SlotModel> slotList;
    ArrayList<SportModel> sportList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground);
        findViewId();
        dateList = new ArrayList<>();
        slotList = new ArrayList<>();
        sportList = new ArrayList<>();
        Intent i = getIntent();
        String json =i.getStringExtra("ground");
        Gson gson =new Gson();
        Type type = new TypeToken<GroundModel>() {}.getType();
        ground = gson.fromJson(json,type);
        setOptions();
        addDate();
    }
    void setOptions(){
        name.setText(ground.getGname());
        address.setText(ground.getGaddress());
        sport.setText(ground.getGsport());
        timing.setText(ground.getGtiming());
        rs.setText("-");
        booking  = new BookingModel(UUID.randomUUID().toString(),ground.getGid(),"null","null","null","null");
        Glide.with(this).load(ground.getGpic()).into(image);
        image.setClipToOutline(true);
        //saveData();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(booking);
                if (booking.getDate().equals("null")){
                    Toast.makeText(GroundActivity.this, "Please select a Date.", Toast.LENGTH_SHORT).show();
                }
                else if (booking.getSport().equals("null")){
                    Toast.makeText(GroundActivity.this, "Please select a Sport.", Toast.LENGTH_SHORT).show();
                }
                else if (booking.getSlot().equals("null")){
                    Toast.makeText(GroundActivity.this, "Please select a Slot.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(GroundActivity.this,FinalCheckout.class);
                    Gson gson1 = new Gson();
                    String json1 = gson1.toJson(ground);
                    String json2 = gson1.toJson(booking);
                    i.putExtra("ground",json1);
                    i.putExtra("booking",json2);
                    startActivity(i);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void findViewId(){
        cancel = findViewById(R.id.groundCancel);
        next = findViewById(R.id.groundNext);
        name = findViewById(R.id.mainGroundName);
        address = findViewById(R.id.mainGroundAddress);
        sport = findViewById(R.id.mainGroundSport);
        timing = findViewById(R.id.mainGroundTiming);
        rs = findViewById(R.id.groundRs);
        dateR = findViewById(R.id.dateRecyclerView);
        sportR = findViewById(R.id.sportRecyclerView);
        slotR = findViewById(R.id.slotRecyclerView);
        image = findViewById(R.id.mainGroundImage);
    }
    public void saveData(){
        Gson gson = new Gson();
        String json = gson.toJson(booking);
        SharedPreferences pref = getSharedPreferences("com.panshul.grook.booking",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("booking",json);
        editor.apply();
    }
    public void loadData(){
        Gson gson = new Gson();
        SharedPreferences pref1 = getSharedPreferences("com.panshul.grook.booking",MODE_PRIVATE);
        String json1 = pref1.getString("booking","");
        Type type1 = new TypeToken<BookingModel>() {}.getType();
        booking = gson.fromJson(json1,type1);
        if (booking.getPrice().equals("null")){
            rs.setText("-");
        }
        else {
            rs.setText(booking.getPrice());
        }
    }
    void addDate(){
        myref = FirebaseDatabase.getInstance().getReference("Ground").child(ground.getGid());
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dateList.clear();
                for (DataSnapshot ds: snapshot.child("gdate").getChildren()){
                    DateModel model = ds.getValue(DateModel.class);
                    dateList.add(model);
                }
                dateAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addSport();
    }
    void addSport(){
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.child("gsportList").getChildren()){
                    SportModel model = ds.getValue(SportModel.class);
                    sportList.add(model);
                }
                sportAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addSlot();
    }
    void addSlot(){
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slotList.clear();
                for (DataSnapshot ds:snapshot.child("gslot").getChildren()){
                    SlotModel model = ds.getValue(SlotModel.class);
                    slotList.add(model);
                }
                slotAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void dateAdapter(){
        DateAdapter dateAdapter = new DateAdapter(dateList,GroundActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(GroundActivity.this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        dateR.setAdapter(dateAdapter);
        dateR.setLayoutManager(manager);

    }
    void sportAdapter(){
        SportAdapter sportAdapter = new SportAdapter(sportList,GroundActivity.this);
        GridLayoutManager manager = new GridLayoutManager(GroundActivity.this,2);
        sportR.setAdapter(sportAdapter);
        sportR.setLayoutManager(manager);
    }
    void slotAdapter(){
        SlotAdapter slotAdapter = new SlotAdapter(slotList,GroundActivity.this);
        GridLayoutManager manager = new GridLayoutManager(GroundActivity.this,2);
        slotR.setAdapter(slotAdapter);
        slotR.setLayoutManager(manager);
    }
}





















