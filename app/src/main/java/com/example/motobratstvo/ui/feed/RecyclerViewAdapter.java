package com.example.motobratstvo.ui.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.motobratstvo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<News> news;

    RecyclerViewAdapter(Context context, List<News> news) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
    }
    @NotNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        @SuppressLint("ResourceType") View view = inflater.inflate(R.layout.recycler_view_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        News news_ = news.get(position);
        holder.textView.setText(news_.getText());
        holder.titleView.setText(news_.getTitle());
        holder.dateText.setText(news_.getDate());
        Log.wtf("CHTO ZA Fignya", news_.getDate());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView, textView, dateText;

        ViewHolder(View view) {
            super(view);
            dateText = view.findViewById(R.id.dateText);
            textView = view.findViewById(R.id.textNews);
            titleView = view.findViewById(R.id.titleNews);
        }
    }
}