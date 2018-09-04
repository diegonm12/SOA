package com.gastrotec.gastrotec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gastrotec.gastrotec.R;

public class InformacionTab extends Fragment {
    TextView nameRestauranteText;
    View myFragmentView;
    Restaurant restaurantInfo;

    public Restaurant getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(Restaurant restaurantInfo) {

        this.restaurantInfo = restaurantInfo;
    }



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        myFragmentView  = inflater.inflate(R.layout.tab1, container, false);


        return myFragmentView;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameRestauranteText = myFragmentView.findViewById(R.id.textView_informacion_tab_name);
        System.out.println(restaurantInfo);
        nameRestauranteText.setText("mae");
        nameRestauranteText = myFragmentView.findViewById(R.id.textView_informacion_tab_address);
        nameRestauranteText.setText("que");
        nameRestauranteText = myFragmentView.findViewById(R.id.textView_informacion_tab_time);
        nameRestauranteText.setText("hace");
    }



}