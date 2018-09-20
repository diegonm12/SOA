package com.sportec.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Dependences.ImageAdapter;
import com.sportec.Dependences.Sport;
import com.sportec.R;
import com.sportec.Service.ApiService;
import com.sportec.Service.ApiServiceSports;

import java.util.ArrayList;
import java.util.List;

public class CheckSportsActivity extends AppCompatActivity {
    List<Sport> mSportsArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sports);
        getSportsFromDB();
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                System.out.println("ID");
                System.out.println(position);
            }
        });
    }

    private void getSportsFromDB() {
        // obtengo todos los deportes para imprimir la info en el grid
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                for (int i = 0; i < result.size(); i++) {
                    Sport newSport = new Sport();
                    newSport.setName(result.get(i).getAsJsonObject().get("name").getAsString());
                    newSport.setImage(result.get(i).getAsJsonObject().get("image").getAsString());
                    mSportsArray.add(newSport);
                }



            }
        };
        ApiServiceSports api = new ApiServiceSports();
        api.downloadSports(this, arreglo);

    }
}
