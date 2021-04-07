package com.example.motobratstvo.ui;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.motobratstvo.R;
import com.example.motobratstvo.ScrActivity.ScrActivity;
import com.example.motobratstvo.checker.StringChecker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegistrationActivity extends AppCompatActivity {

    private TextView emailTextEnter, passwordTextEnter, passwordText, emailText;
    private Button regButton, backButton;
    private static String TAG = "firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        emailTextEnter = findViewById(R.id.editTextEmailAddress);
        passwordTextEnter = findViewById(R.id.editTextPassword);
        passwordText = findViewById(R.id.textPasswordNotAuth);
        emailText = findViewById(R.id.textEmailNotAuth);
        regButton = findViewById(R.id.buttonLogin);
        backButton = findViewById(R.id.buttonRegister);


    }

    @Override
    public void onStart() {
        super.onStart();

        regButton.setOnClickListener(view -> {
            StringChecker stringChecker = new StringChecker();
            String passwordBuff, emailBuff;

            emailBuff = emailTextEnter.getText().toString();
            passwordBuff = passwordTextEnter.getText().toString();

            if(stringChecker.checkPassword(passwordBuff) == 1) {
                Toast.makeText(RegistrationActivity.this, "Error: must be > 5 syms",
                        Toast.LENGTH_SHORT).show();
            }

            else if(stringChecker.checkEmail(emailBuff) == 1) {
                Toast.makeText(RegistrationActivity.this, "Error: must be one @",
                        Toast.LENGTH_SHORT).show();
            }
            else {

                ScrActivity.mAuth.createUserWithEmailAndPassword(emailBuff, passwordBuff)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(RegistrationActivity.this, "User created",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                finish();
            }
        });

        backButton.setOnClickListener(view -> {
            super.finish();
        });

    }

}
