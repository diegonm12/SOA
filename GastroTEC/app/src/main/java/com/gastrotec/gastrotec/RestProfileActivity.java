package com.gastrotec.gastrotec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.google.gson.Gson;


public class RestProfileActivity extends FragmentActivity {

    private FragmentTabHost tabHost;
    public Restaurant restaurantGsonRecieved;
    int cantidad_platillos; // o sea 3 por cada restaurante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_profile);




        //Recibiendo el gson
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        restaurantGsonRecieved = gson.fromJson(strObj, Restaurant.class);

        //Obtengo esos valores que adquieri mediante GSON para mandarlos a los tabs
        // los cuales van a imprimir la informacion, se manda con un bundle
        // ya que esa fue la unica forma que se investigo para mandar informacion a fragments
        Bundle bundle = new Bundle();
        String restName = restaurantGsonRecieved.getName();
        String restAddress = restaurantGsonRecieved.getAddress();
        String restTime = restaurantGsonRecieved.getTime();
        bundle.putCharSequence("name", restName);
        bundle.putCharSequence("address", restAddress);
        bundle.putCharSequence("time", restTime);

        Bundle bundlePosition = new Bundle();
        String position = restaurantGsonRecieved.getID();
        bundlePosition.putCharSequence("ID", position);


        tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,
                getSupportFragmentManager(),android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Información"),
                InformacionTab.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Horario de   comidas"),
                HorarioComidasTab.class, bundlePosition);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Votos"),
                VotoPlatilloTab.class, bundlePosition);




    }


}