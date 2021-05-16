package com.example.motobratstvo.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motobratstvo.R;
import com.example.motobratstvo.srcActivity.SrcActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddPostActivity extends AppCompatActivity {
    private TextView editText;
    private TextView editTitle;
    private Button submitButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feed);

        editText = findViewById(R.id.editTextWriteText);
        editTitle = findViewById(R.id.editTextWriteTitle);
        submitButton = findViewById(R.id.buttonSubmit);
        backButton = findViewById(R.id.buttonBackEdit);

    }

    @Override
    public void onStart() {
        super.onStart();

        submitButton.setOnClickListener(view -> {
            String textBuff, titleBuff;
            titleBuff = editTitle.getText().toString();
            textBuff = editText.getText().toString();

            if(!titleBuff.equals("") && !textBuff.equals("")) {
                // Текущее время
                Date currentDate = new Date();
                // Форматирование времени как "день.месяц.год"
                DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss", Locale.getDefault());
                String dateText = dateFormat.format(currentDate);

                SrcActivity.mDatabase.child("news")
                        .child(Integer.toString(SrcActivity.initData.getLastId() + 1))
                        .child("title").setValue(titleBuff);
                SrcActivity.mDatabase.child("news")
                        .child(Integer.toString(SrcActivity.initData.getLastId() + 1))
                        .child("text").setValue(textBuff);
                SrcActivity.mDatabase.child("news")
                        .child(Integer.toString(SrcActivity.initData.getLastId() + 1))
                        .child("date").setValue(dateText);
                SrcActivity.mDatabase.child("news")
                        .child("lastId").setValue(Integer.toString(SrcActivity.initData.lastId + 1));

                SrcActivity.mDatabase.child("news") .child(Integer.toString(SrcActivity.initData.lastId + 1)).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error", task.getException());
                    } else {
                        if(! String.valueOf(Objects.requireNonNull(task.getResult()).getValue()).equals("null")) {
                            Toast.makeText(AddPostActivity.this, "Posted",
                                    Toast.LENGTH_SHORT).show();
                            SrcActivity.initData.lastId++;
                        }
                        else {
                            Toast.makeText(AddPostActivity.this, "Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(AddPostActivity.this, "Error: cant be empty",
                        Toast.LENGTH_SHORT).show();
            }
        });
       backButton.setOnClickListener(view -> super.finish());
    }
}
