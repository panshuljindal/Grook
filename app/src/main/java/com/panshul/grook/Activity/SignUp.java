package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText entpass,entphone,entname,entemail;
    FirebaseAuth mauth;
    Spinner city;
    String[] cities = {"","Delhi","Mumbai","Chennai"};
    Button btnsignup;
    TextView enterCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        entpass=findViewById (R.id.upass) ;
        entphone =findViewById (R.id.uphone);
        entname=findViewById(R.id.uname);
        entemail=findViewById(R.id.umail);
        btnsignup=findViewById(R.id.btnsignup);
        city = findViewById(R.id.spinner);
        enterCity = findViewById(R.id.textView21);
        mauth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_dropdown_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(this);

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
                //Log.i("city",city.getSelectedItem().toString());
                if (checkempty()) {
                    if (checkemail()) {
                        if (checkCity()) {
                            if (checkPassword()){
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
                                                UserModel model = new UserModel(uid,city.getSelectedItem().toString(),entphone.getText().toString(),entname.getText().toString(),entemail.getText().toString(),false);
                                                myref.child(uid).setValue(model);
                                                Toast.makeText(SignUp.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this, Login.class));
                                            } catch (Exception e) {
                                                Toast.makeText(SignUp.this, "SignUp failed please try again!", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    public boolean checkCity(){
        if (city.getSelectedItem().toString().equals("Delhi")){
            //Log.i("city","Delhi");
            return true;
        }

        if (city.getSelectedItem().toString().equals("Mumbai")){
           // Log.i("city","Mumbai");
            return true;
        }

        if (city.getSelectedItem().toString().equals("Chennai")){
            //Log.i("city","Chennai");
            return true;
        }
        btnsignup.setEnabled(true);
        Toast.makeText(this, "Please enter a correct city", Toast.LENGTH_SHORT).show();
        return false;
    }
    public boolean checkemail(){
        String tempemail=entemail.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher= emailpattern.matcher(tempemail);
        if(emailMatcher.matches()){
            //Log.i("email","matched");
            return true;
        }
        Toast.makeText(this, "Please enter a valid Email ID", Toast.LENGTH_SHORT).show();
        entemail.requestFocus();
        btnsignup.setEnabled(true);
        return false;
    }
    public Boolean checkempty(){
        if(entname.getText().length()==0){
            Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
            entname.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }
        else if(entphone.getText().length()==0){
            Toast.makeText(this, "Please Enter your Phone Number", Toast.LENGTH_SHORT).show();
            entphone.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }
        else if(entemail.getText().length()==0){
            Toast.makeText(this, "Please enter your Email ID", Toast.LENGTH_SHORT).show();
            entemail.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }
        else if(city.getSelectedItem().toString().equals("")){
            Toast.makeText(this, "Please enter a City", Toast.LENGTH_SHORT).show();
            city.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }
        else if(entpass.getText().length()==0){
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            entpass.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }
        else if(entpass.getText().length()<=8){
            Toast.makeText(this, "Please enter minimum 8 characters", Toast.LENGTH_SHORT).show();
            entpass.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }
        else if (entphone.getText().length()>10 | entphone.getText().length()<10){
            Toast.makeText(this, "Please enter a valid Phone Number", Toast.LENGTH_SHORT).show();
            entphone.requestFocus();
            btnsignup.setEnabled(true);
            return false;
        }

        return true;
        }

        public boolean checkPassword(){
        String inputString = entpass.getText().toString();
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
            Pattern pattern1 = Pattern.compile("[0-9]");
            Matcher matcher = pattern.matcher(inputString);
            Matcher matcher1 = pattern1.matcher(inputString);
            boolean special= matcher.find();
            boolean number = matcher1.find();
            boolean hasUppercase = !inputString.equals(inputString.toLowerCase());
            if (!special){
                Toast.makeText(this, "Password should have at least one special character", Toast.LENGTH_SHORT).show();
                btnsignup.setEnabled(true);
                return false;
            }
            if (!number) {
                Toast.makeText(this, "Password should have at least one number", Toast.LENGTH_SHORT).show();
                btnsignup.setEnabled(true);
                return false;
            }
            if (!hasUppercase){
                Toast.makeText(this, "Password should have at least one uppercase", Toast.LENGTH_SHORT).show();
                btnsignup.setEnabled(true);
                return false;
            }
            return true;
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (cities[position].equals("")){
            enterCity.setVisibility(View.VISIBLE);
        }
        else {
            enterCity.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}