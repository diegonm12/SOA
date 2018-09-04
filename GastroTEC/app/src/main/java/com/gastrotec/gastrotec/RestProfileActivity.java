package com.gastrotec.gastrotec;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.google.gson.Gson;


public class RestProfileActivity extends FragmentActivity {

    private FragmentTabHost tabHost;
    public Restaurant restaurantGsonRecieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_profile);

        //Recibiendo el gson
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        restaurantGsonRecieved = gson.fromJson(strObj, Restaurant.class);
        InformacionTab classInfo = new InformacionTab();
        System.out.println(restaurantGsonRecieved.getName());
        classInfo.setRestaurantInfo(restaurantGsonRecieved);

        tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,
                getSupportFragmentManager(),android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Información"),
                classInfo.getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Horario de   comidas"),
                HorarioComidasTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Votos"),
                VotoPlatilloTab.class, null);




    }
}
