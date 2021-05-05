package com.panshul.grook.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.R;

import java.lang.reflect.Type;

public class GroundFragment extends Fragment {

    View view;
    TextView gname,gloc,gaddress,gphone,grating;
    ImageView gpic;
    String bookid = "";
    FirebaseDatabase grounddet;
    DatabaseReference ground;
    GroundModel model;
    Button fball,bball,cricket,tennis,golf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_ground, container, false);

        grounddet = FirebaseDatabase.getInstance();
        ground = grounddet.getReference("Ground");

//        fball = (Button) view.findViewById(R.id.btnfootball);
//        bball = (Button) view.findViewById(R.id.btnbasketball);
//        cricket = (Button) view.findViewById(R.id.btncricket);
//        tennis = (Button) view.findViewById(R.id.btntennis);
//        golf = (Button) view.findViewById(R.id.btngolf);
//        gaddress = (TextView) view.findViewById(R.id.gaddress2);
//        gname = (TextView) view.findViewById(R.id.gname2);
//        gloc = (TextView) view.findViewById(R.id.groundlocdes);
//        gphone = (TextView) view.findViewById(R.id.gphone2);
//        grating = (TextView) view.findViewById(R.id.bookdesrating);
//        gpic = (ImageView) view.findViewById(R.id.bookdesimg);


        getGroundDetails();
        return view;
    }

    private void getGroundDetails (){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.matchup.ground", Context.MODE_PRIVATE);
        String json = pref.getString("groundInfo","");
        Gson gson = new Gson();
        Type type = new TypeToken<GroundModel>() {}.getType();
        model = gson.fromJson(json,type);
        gaddress.setText(model.getGaddress());
        gname.setText(model.getGname());
        Glide.with(view.getContext()).load(model.getGpic()).into(gpic);

    }
}