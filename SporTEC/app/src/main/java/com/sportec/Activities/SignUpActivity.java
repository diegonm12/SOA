package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.R;
import com.sportec.Service.ApiService;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    // metodo encarga de hacer el registro del user
    // mediante el llamado de la api del user para poder agregar el mismo a la base
    public void register(View view) {
        // estas cuatro lineas quitan el teclado de la pantalla.
        LinearLayout mainLayout;
        mainLayout = findViewById(R.id.activity_signup_container);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);


        //variables que representan todos los datos del cliente que se registra
        EditText emailEntry_Sign_Up = findViewById(R.id.activity_signup_email);
        EditText passwordEntry_Sign_Up = findViewById(R.id.activity_signup_password);
        EditText nameEntry_Sign_Up = findViewById(R.id.activity_signup_name);

        // cambia lo que se ingreso a strings para su manejo y valida que el correo sea correcto
        final String emailUser = emailEntry_Sign_Up.getText().toString();
        String passwordUser = passwordEntry_Sign_Up.getText().toString();
        String nameUser = nameEntry_Sign_Up.getText().toString();


        if (isValid(emailUser) && (!emailUser.equals("")) && (!passwordUser.equals(""))
                && (!nameUser.equals(""))) {

            // se debe hacer el query para el ingreso de los clientes en la base
            // se hace un mensaje de que ya se registro a la app.
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
            JsonArray favSports = new JsonArray();

            //aqui hay que meter al usuario en la base de datos, se agarra una imagen de un
            //link por defecto pero el user luego la puede cambiar
            JsonObject json = new JsonObject();
            json.addProperty("name", nameUser);
            json.addProperty("email", emailUser);
            json.addProperty("password", passwordUser);
            json.addProperty("profilePicture", "http://www.iconninja.com/files/699/12/848/avatar-male-account-user-profile-icon.png");
            json.addProperty("sessionInit", "1");
            json.add("favSport", favSports);
            json.addProperty("type", "user");
            final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
                @Override
                public void onCompleted(Exception e, JsonArray result) {

                    //pasa a la pantalla de la escogencia de deporte favoritos
                    Intent intentCheckSports = new Intent(SignUpActivity.this, CheckSportsActivity.class);
                    intentCheckSports.putExtra("emailUser", emailUser);
                    intentCheckSports.putExtra("permission", "1");
                    startActivity(intentCheckSports);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            };
            ApiService api = new ApiService();
            api.addUser(SignUpActivity.this, arreglo, json);


        } else {
            Toast.makeText(this, "Ingresa correctamente toda la información", Toast.LENGTH_LONG).show();
        }
    }

    //metodo que retorna a la pantalla anterior para ingresar
    public void goBack(View view) {
        finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // esta funcion es utilizada para detectar si un email es valido o no
    // por medio de expresiones regulares (regex)
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }
}
