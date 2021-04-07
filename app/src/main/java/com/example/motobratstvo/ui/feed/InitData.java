package com.example.motobratstvo.ui.feed;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InitData {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/news");
    public ArrayList<News> news = new ArrayList<News>();

    public void initData(){

    }


}
