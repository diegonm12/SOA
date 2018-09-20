package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.R;
import com.sportec.Service.ApiService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // obtengo todos los users para ver si alguno tiene la sesion inicializada
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                boolean flag = true;        // bandera para revisar la sesion (abierta o cerrada)
                int counter = 0;            //para llevar el usuario

                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getAsJsonObject().get("sessionInit").getAsString().matches("1")) {
                        counter = i;            //representa el numero de usuario que tiene la sesion abierta
                        flag = false;           //si esto es falso significa que hay una sesion abierta
                    }

                }

                // en este caso no hay ninguna sesion abierta entonces tengo que logear
                if (flag) {
                    Intent intent = new Intent(getApplicationContext(),
                            LogInActivity.class);
                    startActivity(intent);

                    // en este caso ya habria una sesion abierta, entonces me dirijo al main
                } else {
                    System.out.println("HOLA");
                    JsonElement favSport = result.get(counter).getAsJsonObject().get("favSport");
                    System.out.println(favSport.getAsJsonArray().size());

                    // este if chequea si tiene deportes favoritos o no, en caso de que no,
                    // lo manda a escoger a la pantalla respectiva
                    if (favSport.getAsJsonArray().size() == 0) {
                        Intent intentCheckSports = new Intent(SplashActivity.this, CheckSportsActivity.class);
                        String emailUser = result.get(counter).getAsJsonObject().get("email").getAsString();

                        intentCheckSports.putExtra("emailUser", emailUser);
                        startActivity(intentCheckSports);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    }

                    //para el caso de que ya tenga los deportes preferidos entonces va al main
                    else {
                        Intent intentMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        String emailUser = result.get(counter).getAsJsonObject().get("email").getAsString();

                        intentMainActivity.putExtra("emailUser", emailUser);
                        startActivity(intentMainActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }


            }
        };
        ApiService api = new ApiService();
        api.downloadUsers(this, arreglo);

    }
}
