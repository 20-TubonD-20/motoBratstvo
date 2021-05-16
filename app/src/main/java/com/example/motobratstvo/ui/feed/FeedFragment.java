package com.example.motobratstvo.ui.feed;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.motobratstvo.R;
import com.example.motobratstvo.srcActivity.SrcActivity;
import com.example.motobratstvo.ui.AddPostActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeedFragment extends Fragment {
    private  ArrayList<News> news = new ArrayList<>();
    public static InitData initData_ = new InitData();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button feedButton;
    private RecyclerView recView;
    private RecyclerViewAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNews();
        setInitialData();

        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        mSwipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        recView = root.findViewById(R.id.list);
        // начальная инициализация списка
        System.out.println(news.size());

        news.sort(Comparator.comparing(News::getDate));
        Collections.reverse(news);

        // создаем адаптер
        adapter = new RecyclerViewAdapter(getActivity(), news);
        // устанавливаем для списка адаптер
        recView.setAdapter(adapter);

        feedButton = root.findViewById(R.id.feedButton);

        return root;
    }

    public void setInitialData(){
        news = initData_.getNews();
    }
    public void addNews() {
        for (int i = initData_.lastId + 1; i < initData_.lastId + 16; i++) {
            initData_.initData(i);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override public void onStart() {
        super.onStart();
        SrcActivity scrActivity = (SrcActivity)getContext();
        SrcActivity scrActivityBuff = (SrcActivity) getContext();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            recView.setVisibility(ViewStub.INVISIBLE);

            addNews();
            setInitialData();

            news.sort(Comparator.comparing(News::getDate));
            Collections.reverse(news);

                recView.setVisibility(ViewStub.INVISIBLE);

                adapter = new RecyclerViewAdapter(getActivity(), news);
                recView.setAdapter(adapter);

                recView.setVisibility(ViewStub.VISIBLE);

            mSwipeRefreshLayout.setRefreshing(false);
        });

        if(! scrActivityBuff.role.equals("admin")) {
            feedButton.setVisibility(ViewStub.INVISIBLE);
        }
        assert scrActivity != null;
        feedButton.setOnClickListener(v -> {
            if(scrActivityBuff.role.equals("admin")) {
                Intent intent = new Intent(scrActivity, AddPostActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(), "access denied",
                        Toast.LENGTH_SHORT).show();
            }
        });

        recView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

}