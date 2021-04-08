package com.example.motobratstvo.ui.feed;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InitData {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/news");
    public ArrayList<News> news = new ArrayList<News>();
    String buffTitle, buffText, buffText2 = new String();
    public int lastId;
    int count = 1;


    public void initData() {
            mDatabase.child(Integer.toString(count)).child("title").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {


                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffTitle = "null";
                    } else {
                        buffTitle = String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffTitle);

                    }
                }
            });

            mDatabase.child(Integer.toString(count)).child("text").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                int count_ = count;
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffText = "null";
                    } else {
                        buffText = String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffText);
                        if(buffTitle != "null")
                            news.add(new News(buffTitle, buffText, count_));

                    }
                }
            });
        count+=1;
        if(count > 10000) count = 1;
    }

    public void initLastId() {
        mDatabase.child("lastId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    if(buffText2 != null && buffText2 != "null" ) {
                        buffText2 = String.valueOf(task.getResult().getValue());
                        lastId = Integer.parseInt(buffText2);
                    }

                    Log.d("firebaseBT2", buffText2);
                }
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
