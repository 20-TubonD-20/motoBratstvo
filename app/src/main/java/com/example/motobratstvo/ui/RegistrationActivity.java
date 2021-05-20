package com.example.motobratstvo.ui;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motobratstvo.R;
import com.example.motobratstvo.checker.StringChecker;
import com.example.motobratstvo.srcActivity.SrcActivity;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private TextView emailTextEnter;
    private TextView passwordTextEnter;
    private Button regButton, backButton;
    private static final String TAG = "firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        emailTextEnter = findViewById(R.id.editTextWriteTitle);
        passwordTextEnter = findViewById(R.id.editTextWriteText);
        findViewById(R.id.textWriteText);
        findViewById(R.id.textWriteTitle);
        regButton = findViewById(R.id.buttonSubmit);
        backButton = findViewById(R.id.buttonBackEdit);


    }

    @Override
    public void onStart() {
        super.onStart();

        regButton.setOnClickListener(view -> {
            StringChecker stringChecker = new StringChecker();
            String passwordBuff, emailBuff;

            emailBuff = emailTextEnter.getText().toString();
            passwordBuff = passwordTextEnter.getText().toString();

            if(stringChecker.checkEmail(emailBuff) == 1) {
                Toast.makeText(RegistrationActivity.this, "Error: email must contain @",
                        Toast.LENGTH_SHORT).show();
            }

            else if(stringChecker.checkPassword(passwordBuff) == 1) {
                    Toast.makeText(RegistrationActivity.this, "Error: password must contain more than 5 characters",
                            Toast.LENGTH_SHORT).show();
            }
            else {

                SrcActivity.mAuth.createUserWithEmailAndPassword(emailBuff, passwordBuff)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(RegistrationActivity.this, "User created",
                                        Toast.LENGTH_SHORT).show();
                                        finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

        backButton.setOnClickListener(view -> super.finish());

    }

}
