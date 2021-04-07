package com.example.motobratstvo.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.example.motobratstvo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private TextView emailTextEnter, passwordTextEnter, passwordText, emailText;
    private Button regButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        emailTextEnter = findViewById(R.id.regTextEnterEmail);
        passwordTextEnter = findViewById(R.id.regEnterPassword);
        passwordText = findViewById(R.id.regTextPassword);
        emailText = findViewById(R.id.regTextEmail);
        regButton = findViewById(R.id.buttonReg);
        backButton = findViewById(R.id.buttonRegBack);


    }

    @Override
    public void onStart() {
        super.onStart();
        backButton.setOnClickListener(view -> {
            super.finish();
        });

    }

}
