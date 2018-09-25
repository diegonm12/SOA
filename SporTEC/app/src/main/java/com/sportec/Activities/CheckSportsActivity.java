package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    List<Integer> mSportPositionArray = new ArrayList<>();
    public static String mUserEmail;
    public static String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sports);
        Intent intent = getIntent();
        mUserEmail = intent.getStringExtra("emailUser");
        permission = intent.getStringExtra("permission");

        disableButtonAdd();
        getSportsFromDB();

    }

    //Con este metodo quitamos o dejamos el boton para agregar deportes,
    //con el fin de reciclar la vista.
    private void disableButtonAdd() {
        if(permission.matches("0")){
            FloatingActionButton buttonToAddSports =findViewById(R.id.activity_check_sports);
            buttonToAddSports.setVisibility(View.INVISIBLE);
        }
        else{
            FloatingActionButton buttonToAddSports =findViewById(R.id.activity_check_sports);
            buttonToAddSports.setVisibility(View.VISIBLE);
        }
    }

    private void getSportsFromDB() {
        // obtengo todos los deportes para imprimir la info en el grid
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {

                //obtengo todos los deportes y los meto a la lista de deportes
                for (int i = 0; i < result.size(); i++) {
                    Sport newSport = new Sport();
                    newSport.setName(result.get(i).getAsJsonObject().get("name").getAsString());
                    newSport.setImage(result.get(i).getAsJsonObject().get("image").getAsString());
                    newSport.setType(result.get(i).getAsJsonObject().get("type").getAsString());
                    mSportsArray.add(newSport);
                }

                //se le informa al usuario que es momento de elegir los deportes favoritos
                Toast.makeText(CheckSportsActivity.this, "Elige tus deportes favoritos", Toast.LENGTH_LONG).show();

                //se hace una lista de deportes seleccionados, se permiten repetidos
                final List<Integer> sportSelected = new ArrayList<>();
                final GridView gridview = findViewById(R.id.activity_check_sport_gridview);

                //este grid corresponde a las images que se deplegan para elegir deporte preferido
                gridview.setAdapter(new ImageAdapter(getApplicationContext(), mSportsArray));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        if (isListed(sportSelected, position)) {

                            //este es el caso donde el deporte ya esta agregado a la lista
                            Toast.makeText(CheckSportsActivity.this, "Ese deporte ya fue elegido", Toast.LENGTH_LONG).show();

                        } else {

                            //este caso el deporte no esta en la lista
                            Toast.makeText(CheckSportsActivity.this, "Agregado a la lista de deportes", Toast.LENGTH_LONG).show();
                            sportSelected.add(position);
                            mSportPositionArray = sportSelected;
                        }
                    }
                });
            }
        };
        ApiServiceSports api = new ApiServiceSports();
        api.downloadSports(this, arreglo);

    }

    //metodo utilizado para verificar si el deporte seleccionado ya esta en la lista o no
    private boolean isListed(List<Integer> sportSelected, int position) {
        if (sportSelected.size() == 0) {
            return false;
        }
        for (int i = 0; i < sportSelected.size(); i++) {
            if (position == sportSelected.get(i)) {
                return true; // en este caso ya estaria el numero
            }
        }

        return false;
    }

    // metodo usado para agregar deportes favoritos de los usuarios
    public void addSports(View view) {
        if (mSportPositionArray.size() == 0) {
            Toast.makeText(CheckSportsActivity.this, "Selecciona algun deporte", Toast.LENGTH_LONG).show();
        } else {

            // teniendo las listas de las posiciones elegidas por el ususario, se cambian esos numeros a los deportes
            // correspondientes
            List<String> listSport = new ArrayList<>();
            for (int i = 0; i < mSportPositionArray.size(); i++) {
                listSport.add(mSportsArray.get(mSportPositionArray.get(i)).getName());
            }

            //se cambia esa lista de String a  un Json element para meter la informacion a la base
            final JsonElement SportsToAdd = new GsonBuilder().create().toJsonTree(listSport);

            //obtenemos los datos del usuario para hacer la actualizacion
            final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    updateSports(result.get("name").getAsString(),
                            mUserEmail, result.get("password").getAsString(),
                            result.get("profilePicture").getAsString(), result.get("sessionInit").getAsString(), SportsToAdd, result.get("type").getAsString());

                }
            };
            ApiService api = new ApiService();
            api.downloadUser(this, mUserEmail, arreglo);

        }

    }


    //se actualiza el usuario para poder agregar los deportes favoritos
    private void updateSports(String name, final String mUserEmail, String password, String profilePicture, String sesionInit, JsonElement favSport, String type) {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email", mUserEmail);
        json.addProperty("password", password);
        json.addProperty("profilePicture", profilePicture);
        json.addProperty("sessionInit", sesionInit);
        json.add("favSport", favSport);
        json.addProperty("type", type);
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {

                // luego de cambiar los  datos me dirigo al main
                Intent intentMainMenu = new Intent(CheckSportsActivity.this, MainActivity.class);
                intentMainMenu.putExtra("emailUser", mUserEmail);
                startActivity(intentMainMenu);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
        ApiService api = new ApiService();
        api.updateUser(CheckSportsActivity.this, arreglo, json, mUserEmail);
    }


}
