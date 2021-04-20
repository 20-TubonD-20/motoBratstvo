package com.example.motobratstvo;

import android.content.Intent;
import android.os.Bundle;
import com.example.motobratstvo.srcActivity.SrcActivity;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.search.SearchFactory;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("0f07d937-a358-4269-836d-33d9285feea5");
        MapKitFactory.initialize(this);
        SearchFactory.initialize(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, SrcActivity.class);
        startActivity(intent);

    }


}