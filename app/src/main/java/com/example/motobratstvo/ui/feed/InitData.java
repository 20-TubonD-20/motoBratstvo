package com.example.motobratstvo.ui.feed;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class InitData {

    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/news");
    public ArrayList<News> news = new ArrayList<>();
    String date, buffTitle, buffText, buffText2 = "";
    public int lastId = -1;
    int count = 1;


    public void initData(int c) {
            mDatabase.child(Integer.toString(c)).child("title").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    buffTitle = "null";
                } else {
                    buffTitle = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                    Log.d("firebase", buffTitle);

                }
            });

            mDatabase.child(Integer.toString(c)).child("text").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    buffText = "null";
                } else {
                    buffText = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                    Log.d("firebase", buffText);
                }
            });

        mDatabase.child(Integer.toString(c)).child("date").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            final int count_ = count;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    date = "null";
                } else {
                    date = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                    Log.d("firebaseInit ", date);
                    Log.d("firebaseInit ", Integer.toString(c));
                    if(!buffTitle.equals("null")) {
                        news.add(new News(buffTitle, buffText, date, count_));
                        lastId = c;
                    }

                }
            }
        });
    }

    public void initLastId() {
        mDatabase.child("lastId").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                if(buffText2 != null && !buffText2.equals("null")) {
                    buffText2 = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                    lastId = Integer.parseInt(buffText2);
                }

                Log.d("firebaseBT2", buffText2);
            }
        });
    }

    public int getLastId(){
        return lastId;
    }


    public ArrayList<News> getNews(){
        return news;
    }

}
