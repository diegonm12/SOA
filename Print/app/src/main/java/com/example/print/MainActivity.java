package com.example.print;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public String mensaje;
    SharedPreferences pref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mensaje = getString(R.string.Saludo);
        //pref =getSharedPreferences("SOA4ID", Context.MODE_PRIVATE);
        //mensaje = pref.getString("variable","valor defecto");


    }
    @Override
    protected void onStart()
    {

        super.onStart();
        System.out.println("On start");
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        System.out.println("On resume");
        TextView textView = findViewById(R.id.textView);
        textView.setText(mensaje);


    }
    @Override
    protected void onPause()
    {

        super.onPause();
        System.out.println("on pause");;
    }

    @Override
    protected void onStop()
    {

        super.onStop();
        System.out.println("on stop");

    }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        System.out.println("on destroy");
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("variable","Hola soy diego");
        editor.commit();


    }



}
