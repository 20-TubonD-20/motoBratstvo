package com.example.motobratstvo.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.motobratstvo.R;
import com.example.motobratstvo.srcActivity.SrcActivity;
import com.example.motobratstvo.checker.StringChecker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class AddPostActivity extends AppCompatActivity {

    private TextView editText, editTitle, textText, textTitle;
    private Button submitButton, backButton;
    private static String TAG = "firebase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feed);

        editText = findViewById(R.id.editTextWriteText);
        editTitle = findViewById(R.id.editTextWriteTitle);
        textText = findViewById(R.id.textWriteText);
        textTitle = findViewById(R.id.textWriteTitle);
        submitButton = findViewById(R.id.buttonSubmit);
        backButton = findViewById(R.id.buttonBackEdit);

    }

    @Override
    public void onStart() {
        super.onStart();

        submitButton.setOnClickListener(view -> {
            StringChecker stringChecker = new StringChecker();
            String textBuff, titleBuff;

            titleBuff = editTitle.getText().toString();
            textBuff = editText.getText().toString();

            if(titleBuff != "" && textBuff!= "") {
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

                SrcActivity.mDatabase.child("news") .child(Integer.toString(SrcActivity.initData.lastId + 1)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error", task.getException());
                        } else {
                            if(! String.valueOf(task.getResult().getValue()).equals("null")) {
                                Toast.makeText(AddPostActivity.this, "Posted",
                                        Toast.LENGTH_SHORT).show();
                                SrcActivity.initData.lastId++;
                            }
                            else {
                                Toast.makeText(AddPostActivity.this, "Error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });



            }
            else {
                Toast.makeText(AddPostActivity.this, "Error: cant be empty",
                        Toast.LENGTH_SHORT).show();
            }
        });
       backButton.setOnClickListener(view -> {
           super.finish();
       });

    }

}
