package com.example.motobratstvo.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.motobratstvo.R;
import java.util.ArrayList;

public class FeedFragment extends Fragment {

    private void setInitialData(){

        news.add(new News ("Новость ", "тест", R.drawable.icon));
        news.add(new News ("Новость ", "тест", R.drawable.icon));
        news.add(new News ("Новость ", "тест", R.drawable.icon));
        news.add(new News ("Новость ", "тест", R.drawable.icon));
        news.add(new News ("Новость ", "тест", R.drawable.icon));
        news.add(new News ("Новость ", "тест", R.drawable.icon));
        news.add(new News ("Новость ", "тест", R.drawable.icon));
    }


    ArrayList<News> news = new ArrayList<News>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView recView = root.findViewById(R.id.list);
        // начальная инициализация списка
        setInitialData();
        // создаем адаптер
        RecyclerViewAdapter adapter = new RecyclerViewAdapter((Context) getActivity(), news);
        // устанавливаем для списка адаптер
        recView.setAdapter(adapter);

        return root;
    }

    public static int getSize(){
        return 1;
    }
}