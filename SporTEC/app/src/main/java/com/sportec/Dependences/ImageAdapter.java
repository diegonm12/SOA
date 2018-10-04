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
    private Context mContext; //corresponde al contexto de la actividad
    private List<Sport> mSportsArray;   // lista de los deportes

    //se define el constructor del adapter, que recibe el  contexto y lista de deportes
    public ImageAdapter(Context c, List<Sport> sportsArray) {
        this.mSportsArray = sportsArray;
        mContext = c;
    }

    // retorna la cantidad de deportes en la lista
    public int getCount() {
        return mSportsArray.size();
    }

    // retorna el  deporte que se elige segun sea la posicion
    @Override
    public Sport getItem(int position) {
        return mSportsArray.get(position);
    }

    //retorna el id?
    public long getItemId(int position) {
        return 0;
    }

    //obtiene el view para poder  enseñar la lista de los depotes y que asi el
    // user la pueda seleccionar
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