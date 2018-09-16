package com.sportec.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Activities.LogInActivity;
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
                boolean flag = true;
                int counter = 0;
                for (int i = 0; i < result.size(); i++){
                    System.out.println(result.get(i).getAsJsonObject().get("sessionInit"));
                    if(result.get(i).getAsJsonObject().get("sessionInit").getAsString().matches("1")){
                        System.out.println("flag cambia a false");
                        counter = i;
                        flag = false;
                    }

                }
                if(flag) {
                    Intent intent = new Intent(getApplicationContext(),
                            LogInActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intentSplashActivity = new Intent(SplashActivity.this, MainActivity.class);
                    String emailUser = result.get(counter).getAsJsonObject().get("email").getAsString();
                    intentSplashActivity.putExtra("emailUser", emailUser);
                    startActivity(intentSplashActivity);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }



            }
        };
        ApiService api = new ApiService();
        api.downloadUsers(this, arreglo);

    }
}
