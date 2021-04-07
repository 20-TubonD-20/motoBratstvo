package com.example.motobratstvo;

import android.content.Intent;
import android.os.Bundle;
import com.example.motobratstvo.ScrActivity.ScrActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, ScrActivity.class);
        startActivity(intent);

    }

}