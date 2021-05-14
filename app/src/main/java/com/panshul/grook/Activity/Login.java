package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.grook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private EditText entpass;
    private EditText entemail;
    FirebaseAuth mauth;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entpass=findViewById (R.id.upass2) ;
        entemail =findViewById (R.id.uEmail);
        Button btnlogin =findViewById(R.id.btnlogin);

        mauth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance() ;
        myref = database.getReference ("User");
        if(mauth.getCurrentUser()!=null){
            datasave();
            startActivity(new Intent(Login.this,MainActivity.class));
        }
        TextView logintoSignup = findViewById(R.id.loginToSignup);
        logintoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkempty()){
                    if (checkemail()){
                        mauth.signInWithEmailAndPassword(entemail.getText().toString(),entpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    datasave();
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                }
                                else{
                                    Toast.makeText(Login.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }
    public void datasave(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser user = mauth.getCurrentUser();
                String uid = user.getUid();
                SharedPreferences pref = getSharedPreferences("com.panshul.grook.userdata",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("uid",uid);
                String name = snapshot.child(uid).child("name").getValue().toString();
                editor.putString("name",name);
                String email = snapshot.child(uid).child("email").getValue().toString();
                editor.putString("email",email);
                String phone = snapshot.child(uid).child("phone").getValue().toString();
                editor.putString("phone",phone);
                String city = snapshot.child(uid).child("city").getValue().toString();
                editor.putString("city",city);
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Error! Please try again", Toast.LENGTH_SHORT).show();
            }
        });
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
        if(entemail.getText().length()==0){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            entemail.requestFocus();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}