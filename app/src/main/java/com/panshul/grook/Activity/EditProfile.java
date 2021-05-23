package com.panshul.grook.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.panshul.grook.Model.UserModel;
import com.panshul.grook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {

    Button edit;
    EditText name, phone, email;
    AutoCompleteTextView city;
    DatabaseReference myref;
    ImageView cancel;
    String[] cities = {"Delhi","Mumbai","Chennai"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name = findViewById(R.id.profileNameEditText);
        email = findViewById(R.id.profileEmailEditText);
        phone = findViewById(R.id.profilePhoneEditText);
        city = findViewById(R.id.profileCityEditText);
        edit = findViewById(R.id.buttonEditProfile);
        cancel = findViewById(R.id.editProfileCancel);
        email.setFocusable(false);
        SharedPreferences pref = getSharedPreferences("com.panshul.grook.userdata", MODE_PRIVATE);
        name.setText(pref.getString("name", ""));
        email.setText(pref.getString("email", ""));
        phone.setText(pref.getString("phone", ""));
        city.setText(pref.getString("city", ""));
        myref = FirebaseDatabase.getInstance().getReference("User");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,cities);
        city.setThreshold(1);
        city.setAdapter(adapter);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkempty()) {
                    if (checkCity()) {
                        if (checkemail()) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            UserModel model = new UserModel(uid, city.getText().toString(), phone.getText().toString(), name.getText().toString(), email.getText().toString());
                            try {
                                myref.child(uid).setValue(model);
                                Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(EditProfile.this, "Error occurred. Please try again!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
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

    public boolean checkCity(){

        if (city.getText().toString().equals("Delhi")){
            return true;
        }
        else if (city.getText().toString().equals("Mumbai")){
            return true;
        }
        else if (city.getText().toString().equals("Chennai")){
            return true;
        }
        Toast.makeText(this, "Please enter a correct city", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean checkemail() {
        String tempemail = email.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher = emailpattern.matcher(tempemail);
        if (emailMatcher.matches()) {
            return true;
        }
        Toast.makeText(this, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
        email.requestFocus();
        return false;
    }

    public Boolean checkempty() {
        if (name.getText().length() == 0) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        } else if (phone.getText().length() == 0) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return false;
        } else if (email.getText().length() == 0) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        } else if (city.getText().length() == 0) {
            Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
            city.requestFocus();
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
