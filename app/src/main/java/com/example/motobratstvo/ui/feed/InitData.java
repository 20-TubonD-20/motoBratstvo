package com.example.motobratstvo.ui.feed;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class InitData {

    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/news");
    public ArrayList<News> news = new ArrayList<>();
    public String date = "", buffTitle = "", buffText = "", buffText2 = "";
    public int lastId = -1;

    public void initData(int c) {
        mDatabase.child(Integer.toString(c)).child("title").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    buffTitle = "null";
                } else {
                    buffTitle = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                }
        });
        mDatabase.child(Integer.toString(c)).child("text").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    buffText = "null";
                } else {
                    buffText = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                }
        });
        mDatabase.child(Integer.toString(c)).child("date").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                date = "null";
            } else {
                date = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                if(!buffTitle.equals("null")) {
                    news.add(new News(buffTitle, buffText, date, c));
                    lastId = c;
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


