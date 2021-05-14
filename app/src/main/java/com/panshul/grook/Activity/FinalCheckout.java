package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.grook.Model.AllHistoryModel;
import com.panshul.grook.Model.BookingModel;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.Model.UserHistoryModel;
import com.panshul.grook.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinalCheckout extends AppCompatActivity {

    TextView name,address,sport,timing,charges,fees,total,mainTotal;
    ImageView image;
    Button cancel,pay;
    BookingModel booking;
    GroundModel ground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout);
        findViewBy();
        Intent i = getIntent();
        String json = i.getStringExtra("ground");
        String json1 = i.getStringExtra("booking");
        Gson gson = new Gson();
        Type type = new TypeToken<BookingModel>() {}.getType();
        Type type1 = new TypeToken<GroundModel>() {}.getType();
        ground = gson.fromJson(json,type1);
        booking = gson.fromJson(json1,type);
        initialise();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinalCheckout.this,MainActivity.class));
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Booking").child(ground.getGid()).child(booking.getDate()).child(booking.getSport()).child(booking.getSlot());
                DatabaseReference myref1 = FirebaseDatabase.getInstance().getReference("History");
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
//                            Log.i("ground",ground.getGid());
//                            Log.i("date",booking.getDate());
//                            Log.i("sport",booking.getSport());
//                            Log.i("slot",booking.getSlot());
                            String avail = snapshot.getValue().toString();
                            Log.i("avail",avail);
                            if (Integer.valueOf(avail)>0){
                                Toast.makeText(FinalCheckout.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                                AllHistoryModel model1 = new AllHistoryModel(getSharedPreferences("com.panshul.grook.userdata",MODE_PRIVATE).getString("name",""),booking.getSlot(),booking.getSport(),booking.getDate(),booking.getBid());
                                UserHistoryModel model = new UserHistoryModel(ground.getGname(),booking.getSport(),booking.getPrice(),booking.getDate(),ground.getGpic2(),booking.getSlot(),booking.getBid(),ground.getGid(),ground.getGaddress(),ground.getGpic(),ground.getGtiming(),ground.getGclosed(),ground.getGsport());
                                myref1.child("Ground").child(ground.getGid()).child(booking.getBid()).setValue(model1);
                                myref1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(booking.getBid()).setValue(model);
                                myref.setValue(String.valueOf(Integer.valueOf(avail)-1));
                                startActivity(new Intent(FinalCheckout.this,MainActivity.class));

                            }
                            else {
                                Toast.makeText(FinalCheckout.this, "Slot not Available", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            //Log.i("avail",e.getMessage());
                            Toast.makeText(FinalCheckout.this, "Slot not Available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FinalCheckout.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM").format(df);
        return vv;
    }
    public String unixconvertDay(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        int i=df.getDay();
        if (i==1){
            return "Monday";
        }
        else if (i==2){
            return "Tuesday";
        }
        else if (i==3){
            return "Wednesday";
        }
        else if (i==4){
            return "Thursday";
        }
        else if (i==5){
            return "Friday";
        }
        else if (i==6){
            return "Saturday";
        }
        else {
            return "Sunday";
        }
    }
    void initialise(){
        name.setText(ground.getGname());
        address.setText(ground.getGaddress());
        sport.setText(booking.getSport());
        timing.setText(unixconvertDay(booking.getDate())+", "+unixconvert(booking.getDate()));
        charges.setText("Rs. "+booking.getPrice());
        fees.setText("Rs. "+ground.getGbookingfees());
        int tota = Integer.valueOf(booking.getPrice())+Integer.valueOf(ground.getGbookingfees());
        total.setText("Rs. "+String.valueOf(tota));
        mainTotal.setText("Rs. "+String.valueOf(tota));
        booking.setPrice(String.valueOf(tota));
        Glide.with(this).load(ground.getGpic()).into(image);
        image.setClipToOutline(true);

    }
    void findViewBy(){
        name = findViewById(R.id.checkoutGroundName);
        address = findViewById(R.id.checkoutGroundAddress);
        sport = findViewById(R.id.checkoutGroundSport);
        timing = findViewById(R.id.checkoutGroundTiming);
        charges = findViewById(R.id.groundCharges);
        fees = findViewById(R.id.bookingFees);
        total = findViewById(R.id.totalPrice);
        image = findViewById(R.id.checkoutGroundImage);
        cancel = findViewById(R.id.checkoutCancel);
        mainTotal = findViewById(R.id.checkoutTotal);
        pay = findViewById(R.id.payOnSite);
    }
}