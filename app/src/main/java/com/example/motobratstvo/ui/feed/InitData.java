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
    String buffTitle, buffText = new String();
    boolean flag = true;


/*    public void initData() {
        for (int i = 1; i < 100000; i++) {

            mDatabase.child(Integer.toString(i)).child("title").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffTitle = "null";
                        flag = false;
                    } else {
                        buffTitle = String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffTitle);

                    }
                }
            });

            mDatabase.child(Integer.toString(i)).child("text").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffText = "null";
                        flag = false;
                    } else {
                        buffText = String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffText);

                    }
                }
            });

            if(!flag) break;

            news.add(new News(buffTitle, buffText));
        }
    }
*/
    public void initDataOnce() {

            mDatabase.child(Integer.toString(1)).child("title").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

            mDatabase.child(Integer.toString(1)).child("text").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffText = "null";
                    } else {
                        buffText = String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffText);
                        news.add(new News(buffTitle, buffText));

                    }
                }
            });
        }

    public ArrayList<News> getNews(){
        return news;
    }
}
