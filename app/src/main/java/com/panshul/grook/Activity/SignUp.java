package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.panshul.grook.Model.UserModel;
import com.panshul.grook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText entpass,entphone,entname,entemail;
    AutoCompleteTextView cityEditText;
    FirebaseAuth mauth;
    String[] cities = {"Delhi","Mumbai","Kolkata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        entpass=findViewById (R.id.upass) ;
        entphone =findViewById (R.id.uphone);
        entname=findViewById(R.id.uname);
        entemail=findViewById(R.id.umail);
        Button btnsignup =findViewById(R.id.btnsignup);
        cityEditText = findViewById(R.id.cityEditText);

        mauth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,cities);
        cityEditText.setThreshold(1);
        cityEditText.setAdapter(adapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("User");

        TextView logintoSignup = findViewById(R.id.signUpToLogin);
        logintoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnsignup.setEnabled(false);
                if (checkempty()) {
                    if (checkemail()) {
                        if (checkCity()) {
                            String email = entemail.getText().toString();
                            String pass = entpass.getText().toString();
                            mauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        try {
                                            FirebaseUser user = mauth.getCurrentUser();
                                            String uid = user.getUid();
                                            btnsignup.setEnabled(true);
                                            //Log.i("uid", uid);
                                            UserModel model = new UserModel(uid,cityEditText.getText().toString(),entphone.getText().toString(),entname.getText().toString(),entemail.getText().toString());
                                            myref.child(uid).setValue(model);
                                            startActivity(new Intent(SignUp.this, Login.class));
                                        } catch (Exception e) {
                                            Toast.makeText(SignUp.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                            btnsignup.setEnabled(true);
                                        }
                                    } else {
                                        Toast.makeText(SignUp.this, "SignUp failed please try again!", Toast.LENGTH_SHORT).show();
                                        btnsignup.setEnabled(true);
                                    }

                                }
                            });
                        }
                    }
                }
            }
        });
    }
    public boolean checkCity(){

        if (cityEditText.getText().toString().equals("Delhi")){
            return true;
        }

        else if (cityEditText.getText().toString().equals("Mumbai")){
            return true;
        }

        else if (cityEditText.getText().toString().equals("Kolkata")){
            return true;
        }
        Toast.makeText(this, "Please enter correct city", Toast.LENGTH_SHORT).show();
        return false;
    }
    public boolean checkemail(){
        String tempemail=entemail.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher= emailpattern.matcher(tempemail);
        if(emailMatcher.matches()){
            return true;
        }
        Toast.makeText(this, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
        entemail.requestFocus();
        return false;
    }
    public Boolean checkempty(){
        if(entname.getText().length()==0){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            entname.requestFocus();
            return false;
        }
        else if(entphone.getText().length()==0){
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            entphone.requestFocus();
            return false;
        }
        else if(entemail.getText().length()==0){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            entemail.requestFocus();
            return false;
        }
        else if(cityEditText.getText().length()==0){
            Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
            cityEditText.requestFocus();
            return false;
        }
        else if(entpass.getText().length()==0){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            entpass.requestFocus();
            return false;
        }
        else if(entpass.getText().length()<=6){
            Toast.makeText(this, "Please enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            entpass.requestFocus();
            return false;
        }
        return true;
        }
    }