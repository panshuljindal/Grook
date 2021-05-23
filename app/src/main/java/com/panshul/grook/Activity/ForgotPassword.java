package com.panshul.grook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.panshul.grook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    EditText email;
    Button reset,checkEmail;
    ConstraintLayout ui1,ui2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.resetEmailId);
        reset = findViewById(R.id.resetPassword);
        ui1 = findViewById(R.id.resetUi1);
        ui2 = findViewById(R.id.resetUi2);
        checkEmail = findViewById(R.id.checkEmailReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.setEnabled(false);
                if (checkempty()){
                    if (checkemail()){
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    reset.setEnabled(true);
                                    ui1.setVisibility(View.GONE);
                                    ui2.setVisibility(View.VISIBLE);
                                    Toast.makeText(ForgotPassword.this, "Check email", Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(ForgotPassword.this, Login.class));
                                }
                                else{
                                    reset.setEnabled(true);
                                    ui2.setVisibility(View.GONE);
                                    ui1.setVisibility(View.VISIBLE);
                                    Toast.makeText(ForgotPassword.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    email.requestFocus();
                                }
                            }
                        });
                    }
                }
            }
        });
        checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private boolean checkempty(){
        if(email.getText().length()==0){
            Toast.makeText(ForgotPassword.this, "Please enter an email id", Toast.LENGTH_SHORT).show();
            reset.setEnabled(true);
            return false;
        }
        return true;
    }
    private boolean checkemail(){
        String tempemail=email.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher= emailpattern.matcher(tempemail);
        if(emailMatcher.matches()){
            return true;
        }
        Toast.makeText(ForgotPassword.this, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
        reset.setEnabled(true);
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}