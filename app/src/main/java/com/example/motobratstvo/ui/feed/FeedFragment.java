package com.example.motobratstvo.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.motobratstvo.R;
import com.example.motobratstvo.checker.StringChecker;
import com.example.motobratstvo.srcActivity.SrcActivity;
import com.example.motobratstvo.ui.AddPostActivity;
import com.example.motobratstvo.ui.RegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeedFragment extends Fragment {
    private  ArrayList<News> news = new ArrayList<News>();
    public static InitData initData_ = new InitData();
    private Button feedButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNews();
        setInitialData();
        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView recView = root.findViewById(R.id.list);
        // начальная инициализация списка
        System.out.println(news.size());

        news.sort(Comparator.comparing(News::getDate));
        Collections.reverse(news);

        // создаем адаптер
        RecyclerViewAdapter adapter = new RecyclerViewAdapter((Context) getActivity(), news);
        // устанавливаем для списка адаптер
        recView.setAdapter(adapter);

        feedButton = root.findViewById(R.id.feedButton);

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

    @Override public void onStart() {
        super.onStart();
        SrcActivity scrActivity = (SrcActivity)getContext();
        assert scrActivity != null;
        feedButton.setOnClickListener(v -> {
            SrcActivity scrActivityBuff = (SrcActivity) getContext();
            if(scrActivityBuff.role.equals("admin")) {
                Intent intent = new Intent(scrActivity, AddPostActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText((SrcActivity) getActivity(), "access denied",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}