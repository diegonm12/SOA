package com.gastrotec.gastrotec;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;


public class RestProfileActivity extends FragmentActivity {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_profile);
        tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,
                getSupportFragmentManager(),android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Información"),
                InformacionTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Horario de   comidas"),
                HorarioComidasTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Votos"),
                VotoPlatilloTab.class, null);


    }
}
