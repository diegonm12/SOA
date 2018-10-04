package com.sportec.Dependences;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportec.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {


    private final Activity context; //corresponde al context que se maneja en laactividad
    private List<String> itemname;  // corresponde al nombre del  equipo
    private List<String> imgid;     //corresponde a la imagen del equipo

    // de hace el constructor del adaptador, recibiendo una lista de los nombres del equipo
    // con las imagenes que representan cada equipo
    public CustomListAdapter(Activity context, List<String> itemname, List<String> imgid) {
        super(context, R.layout.mylist, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    //obtiene el view para poder  enseñar la lista de los equipos y que asi el
    // user la pueda seleccionar
    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = rowView.findViewById(R.id.my_list_text_list);
        ImageView imageView = rowView.findViewById(R.id.my_list_image_list);
        txtTitle.setText(itemname.get(position));
        if(imgid.size()>0) {
            Picasso.get()
                    .load(imgid.get(position))
                    .placeholder(R.drawable.loader)
                    .fit()
                    .centerCrop().into(imageView);
        }
        return rowView;

    }
}