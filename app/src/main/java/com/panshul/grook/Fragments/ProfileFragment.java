package com.panshul.grook.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.grook.Activity.CheckEmail;
import com.panshul.grook.Activity.EditProfile;
import com.panshul.grook.Activity.Login;
import com.panshul.grook.Model.UserModel;
import com.panshul.grook.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    View view;
    Button logout,editProfile,resetPassword;
    TextView name,email,phone,city;
    LottieAnimationView lottie;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.logout);
        editProfile = view.findViewById(R.id.editProfile);
        resetPassword = view.findViewById(R.id.changePassword);
        name  = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        phone = view.findViewById(R.id.profilePhone);
        city = view.findViewById(R.id.profileLocation);
        lottie = view.findViewById(R.id.profileAnimationView);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("User").child(uid);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                try {
                    UserModel model = snapshot.getValue(UserModel.class);
                    name.setText(model.getName());
                    email.setText(model.getEmail());
                    phone.setText("+91 "+model.getPhone());
                    city.setText(model.getCity()+", India");
                    SharedPreferences pref1 = view.getContext().getSharedPreferences("com.panshul.grook.userdata",MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = pref1.edit();
                    editor1.putString("name",model.getName());
                    editor1.putBoolean("isPremium",model.isPremium()).commit();
                    editor1.putString("email",model.getEmail());
                    editor1.putString("phone",model.getPhone());
                    editor1.putString("city",model.getCity());
                    editor1.apply();

                    lottie.setVisibility(View.GONE);
                    lottie.pauseAnimation();
                }
                catch (Exception e){
                    name.setText("");
                    email.setText("");
                    phone.setText("");
                    city.setText("");

                    lottie.setVisibility(View.GONE);
                    lottie.pauseAnimation();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                name.setText("");
                email.setText("");
                phone.setText("");
                city.setText("");

                lottie.setVisibility(View.GONE);
                lottie.pauseAnimation();
            }
        });
        onClick();
        return view;
    }
    public void onClick(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                clearData();
                Intent i = new Intent(v.getContext(), Login.class);
                Toast.makeText(v.getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), EditProfile.class));
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            startActivity(new Intent(v.getContext(), CheckEmail.class));
                        }
                        else {
                            Toast.makeText(v.getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void clearData(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.grook.userdata",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}