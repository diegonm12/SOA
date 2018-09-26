package com.sportec.Dependences;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportec.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {


    private final Activity context;
    private List<String> itemname = new ArrayList<>();
    private List<String> imgid = new ArrayList<>();

    public CustomListAdapter(Activity context, List<String> itemname, List<String> imgid) {
        super(context, R.layout.mylist, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

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

    };
}