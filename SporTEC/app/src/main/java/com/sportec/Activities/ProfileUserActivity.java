package com.sportec.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Dependences.User;
import com.sportec.R;
import com.sportec.Service.ApiService;
import com.squareup.picasso.Picasso;

public class ProfileUserActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static TextView mNameUserTextView;   //textview donde se observara el nombre del  user
    @SuppressLint("StaticFieldLeak")
    public static TextView mEmailUserTextView;  //textview donde se observara el email del  user
    @SuppressLint("StaticFieldLeak")
    public static TextView mPasswordUserTextView;   //textview donde se observara el password del  user
    public static User mCurrentUser;    //se define el user que se va a utilizar en la activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        //obtengo la informacion del usuario por medio de Gson
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("User");
        mCurrentUser = gson.fromJson(strObj, User.class);

        //Obtenemos quien esta haciendo la peticion para el perfil, para dar permisos de
        //cambio o no, o sea que solo pueda ver la informacion o que la  pueda editar
        String strCurrentEmail = getIntent().getStringExtra("currentUser");
        System.out.println(strCurrentEmail);


        //se define los elementos de la pantalla utilizar para mostrar informacion del user
        mNameUserTextView = findViewById(R.id.activity_profile_user_name);
        mEmailUserTextView = findViewById(R.id.activity_profile_user_email);
        mPasswordUserTextView = findViewById(R.id.activity_profile_user_password);
        ImageView imageNews = findViewById(R.id.activity_profile_user_photo);

        //se le asigna a cada elemento la informacion correspondiente
        mNameUserTextView.setText(mCurrentUser.getName());
        mEmailUserTextView.setText(mCurrentUser.getmEmail());
        mPasswordUserTextView.setText(mCurrentUser.getmPassword());

        //se emplea picasso para cargar las imagenes desde la web
        Picasso.get().load(mCurrentUser.getmProfilePicture()).fit().centerInside().into(imageNews);

        if (!(strCurrentEmail.matches(mCurrentUser.getmEmail()))) {
            noPermissions(); //no habria permiso de editar
        }
    }

    //Metodo que le quita los permisos a los usuarios en caso de que quieran ver alguno
    // solo se puede ver y no editar si el correo del usuario actual es diferente al
    // correo del usuario que se esta viendo
    private void noPermissions() {
        //se elimina el button de aceptar
        Button buttonAceptar = findViewById(R.id.activity_profile_user_acept);
        buttonAceptar.setVisibility(View.INVISIBLE);

        //se elimina el button de cambiar preferencias de deportes
        Button buttonPref = findViewById(R.id.activity_profile_pref_button);
        buttonPref.setVisibility(View.INVISIBLE);

        //por medio de los setKeyListener se inhabilita el cambio de los editText
        mEmailUserTextView.setKeyListener(null);
        mNameUserTextView.setKeyListener(null);
        mPasswordUserTextView.setKeyListener(null);
    }

    //al darle al boton aceptar se cambian los datos del user segun
    //sea lo que ingreso en los editText
    public void aceptar(View view) {
        //Le asigno una variable a los datos que se obtienen desde los editText
        String newName = mNameUserTextView.getText().toString();
        final String newEmail = mEmailUserTextView.getText().toString();
        String newPassword = mPasswordUserTextView.getText().toString();

        // aqui se definen los nuevo campos editados por el user
        JsonObject json = new JsonObject();
        json.addProperty("name", newName);
        json.addProperty("email", newEmail);
        json.addProperty("password", newPassword);
        json.addProperty("profilePicture", mCurrentUser.getmProfilePicture());
        json.addProperty("sessionInit", "1");
        json.add("favSport", mCurrentUser.getFavSports());
        json.addProperty("type", mCurrentUser.getmType());

        //se hace un callback con el api del user, para hacer el update
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                Intent intentMainMenu = new Intent(ProfileUserActivity.this, MainActivity.class);
                intentMainMenu.putExtra("emailUser", newEmail);
                startActivity(intentMainMenu);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        };
        ApiService api = new ApiService();
        api.updateUser(this, arreglo, json, mCurrentUser.getmEmail());


    }

    //metodo que envia al user atras
    public void goBack(View view) {
        finish();
    }

    //aqui se cambian los deportes favoritos del user
    public void editarSeleccionPref(View view) {
        Intent intentCheckSports = new Intent(ProfileUserActivity.this, CheckSportsActivity.class);
        intentCheckSports.putExtra("emailUser", mCurrentUser.getmEmail());
        intentCheckSports.putExtra("permission", "1");
        startActivity(intentCheckSports);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
