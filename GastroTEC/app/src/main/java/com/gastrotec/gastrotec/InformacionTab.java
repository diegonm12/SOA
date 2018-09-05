package com.gastrotec.gastrotec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InformacionTab extends Fragment {
    TextView nameRestauranteText;
    View myFragmentView;
    CharSequence name;
    CharSequence address;
    CharSequence time;




    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        name = bundle.getCharSequence("name");
        address = bundle.getCharSequence("address");
        time = bundle.getCharSequence("time");


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
        nameRestauranteText.setText(name);
        nameRestauranteText = myFragmentView.findViewById(R.id.textView_informacion_tab_address);
        nameRestauranteText.setText(address);
        nameRestauranteText = myFragmentView.findViewById(R.id.textView_informacion_tab_time);
        nameRestauranteText.setText(time);
    }



}