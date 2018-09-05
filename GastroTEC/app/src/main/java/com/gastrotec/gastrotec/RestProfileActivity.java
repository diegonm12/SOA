package com.gastrotec.gastrotec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.google.gson.Gson;


public class RestProfileActivity extends FragmentActivity {
    PlatillosDatabaseAdapter platilloDatabaseAdapter;
    private FragmentTabHost tabHost;
    public Restaurant restaurantGsonRecieved;
    int cantidad_platillos = 12; // o sea 3 por cada restaurante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_profile);
        platilloDatabaseAdapter = new PlatillosDatabaseAdapter(getApplicationContext());
        platilloDatabaseAdapter.open();

        setData();
        System.out.println(platilloDatabaseAdapter.getProfilesCount());

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

    private void setData() {

        if (platilloDatabaseAdapter.getProfilesCount() == cantidad_platillos)
        {
        }
        else {
            platilloDatabaseAdapter.insertEntry("Emparedados de atun", "8:00 am - 10:00am" ,
                    "1", "2","3");
            platilloDatabaseAdapter.insertEntry("Arroz con pollo", "12:00 am - 1:00pm" ,
                    "1", "5","2");
            platilloDatabaseAdapter.insertEntry("Empanada de pollo", "3:00 pm - 4:30pm" ,
                    "1", "5","2");
            platilloDatabaseAdapter.insertEntry("Pinto con huevo", "8:00 am - 9:30am" ,
                    "2", "2","0");
            platilloDatabaseAdapter.insertEntry("Arroz con camarones", "12:00 am - 2:00pm" ,
                    "2", "12","2");
            platilloDatabaseAdapter.insertEntry("Empanada de carne", "3:00 pm - 4:30pm" ,
                    "2", "5","9");
            platilloDatabaseAdapter.insertEntry("Emparedado de jalea", "7:30 am - 9:00am" ,
                    "3", "2","0");
            platilloDatabaseAdapter.insertEntry("Hamburguesas con papas", "11:30 am - 1:00pm" ,
                    "3", "12","2");
            platilloDatabaseAdapter.insertEntry("Arepas", "3:00 pm - 5:00pm" ,
                    "3", "0","9");
            platilloDatabaseAdapter.insertEntry("Empanada de papa", "8:00 am - 9:00am" ,
                    "4", "2","0");
            platilloDatabaseAdapter.insertEntry("Pollo frito", "11:30 am - 1:30pm" ,
                    "4", "12","2");
            platilloDatabaseAdapter.insertEntry("Burritos de frijol", "3:00 pm - 5:00pm" ,
                    "4", "8","5");
        }
    }
}