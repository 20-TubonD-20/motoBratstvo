package com.example.motobratstvo.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.motobratstvo.R;

public class ViewPagerImagesAdapter extends PagerAdapter {

    /** IMAGES ARRAY **/
    private Context mContext;
    private int[] mImageIDs;

    public ViewPagerImagesAdapter(Context context, int[] resids) {
        this.mImageIDs = resids;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mImageIDs.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView avatarImageView;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager, container,
                false);


        avatarImageView = itemView.findViewById(R.id.viewPagerImageView);
        avatarImageView.setImageResource(mImageIDs[position]);

        container.addView(itemView);

        return itemView;
    }
}
