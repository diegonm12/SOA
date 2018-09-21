package com.sportec.Dependences;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sportec.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Sport> mSportsArray;

    public ImageAdapter(Context c, List<Sport> sportsArray) {
        this.mSportsArray = sportsArray;
        mContext = c;
    }

    public int getCount() {
        return mSportsArray.size();
    }

    @Override
    public Sport getItem(int position) {
        return mSportsArray.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(480, 480));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position).getImage();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(imageView);
        return imageView;
    }
}