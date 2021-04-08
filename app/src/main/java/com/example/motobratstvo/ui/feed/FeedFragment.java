package com.example.motobratstvo.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.motobratstvo.R;
import com.example.motobratstvo.ScrActivity.ScrActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FeedFragment extends Fragment {
    private  ArrayList<News> news = new ArrayList<News>();
    public static InitData initData_ = new InitData();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNews();
        setInitialData();
        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView recView = root.findViewById(R.id.list);
        // начальная инициализация списка
        System.out.println(news.size());



        // создаем адаптер
        RecyclerViewAdapter adapter = new RecyclerViewAdapter((Context) getActivity(), news);
        // устанавливаем для списка адаптер
        recView.setAdapter(adapter);

        return root;
    }

    public void setInitialData(){
        news = initData_.getNews();
    }
    public void addNews() {
        for (int i = 0; i < 1000; i++) {
            initData_.initData();
        }
    }


}