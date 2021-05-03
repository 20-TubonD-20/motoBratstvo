package com.example.motobratstvo.srcActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.motobratstvo.R;
import com.example.motobratstvo.ui.feed.InitData;
import com.example.motobratstvo.ui.feed.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class SrcActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    public static FirebaseAuth mAuth;
    public static DatabaseReference mDatabase;
    public String email = "null", password = "null", role = "null";
    public boolean isAuth = false;

    public NavController navController;

    public static String APP_PREFERENCES = "usersettings";
    public static String APP_PREFERENCES_EMAIL = "email";
    public static String APP_PREFERENCES_PASSWORD = "password";
    public static String APP_PREFERENCES_ROLE = "role";
    SharedPreferences mSettings;


    public static InitData initData = new InitData();
    public ArrayList<News> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initData.initLastId();

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        if(mSettings.contains(APP_PREFERENCES_EMAIL)) {
            email = mSettings.getString(APP_PREFERENCES_EMAIL, "null");
        }
        if(mSettings.contains(APP_PREFERENCES_PASSWORD)) {
            password = mSettings.getString(APP_PREFERENCES_PASSWORD, "null");
        }
        if(mSettings.contains(APP_PREFERENCES_ROLE)) {
            role = mSettings.getString(APP_PREFERENCES_ROLE, "null");
        }


        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
        if(!(email.equals("null") && password.equals("null"))) signIn(email, password);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_feed, R.id.navigation_map, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    public void onStart() {
        super.onStart();

    }


    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        isAuth = true;
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        String uid = user.getUid();
                        mDatabase.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    String buff = String.valueOf(task.getResult().getValue());
                                    Log.d("firebase_users", buff);
                                    if(buff == "null") {
                                        mDatabase.child("users")
                                                .child(uid).setValue("default");
                                    }
                                    else {
                                        role = buff;
                                        saveConf();
                                    }

                                }
                            }
                        });



                    } else {
                        isAuth = false;
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(SrcActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signInSecond(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        isAuth = true;
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();
                        mDatabase.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                //} else {
                                    /*String buff = String.valueOf(task.getResult().getValue());
                                    Log.d("firebase_users", buff);
                                    if(buff == "null") {
                                        mDatabase.child("users")
                                                .child(uid).setValue("default");
                                    }
                                    else {
                                        role = buff;
                                        saveConf();
                                    }*/

                                }
                            }
                        });

                        updateUI(user);
                    } else {
                        isAuth = false;
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(SrcActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void restart(){
        finish();
    }

    public void reload() { }

    public void updateUI(FirebaseUser user) {
        super.finish();
    }

    public void saveConf() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_EMAIL, email);
        editor.putString(APP_PREFERENCES_PASSWORD, password);
        editor.putString(APP_PREFERENCES_ROLE, role);
        editor.apply();
    }


    public void refreshData(){
        initData.refreshCount();
        for (int i = 0; i < 1000; i++) {
            initData.initData();
        }
        data = initData.getNews();
    }

}