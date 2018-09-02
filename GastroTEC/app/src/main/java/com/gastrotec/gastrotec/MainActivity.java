package com.gastrotec.gastrotec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void signIn(View view) {

        // estas cuatro lineas quitan el teclado de la pantalla.
        LinearLayout mainLayout;
        mainLayout = (LinearLayout)findViewById(R.id.login_form_container_main_activity);
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);


        //variables que representan el email y la contraseña ingresada
        EditText emailEntry_mainActivity = (EditText)findViewById(R.id.email_main_activity);
        EditText passwordEntry_mainActivity = (EditText)findViewById(R.id.password_main_activity);

        // cambia lo que se ingreso a strings y valida que el correo sea correcto
        String emailUser = emailEntry_mainActivity.getText().toString();
        String passwordUser = passwordEntry_mainActivity.getText().toString();
        if (isValid(emailUser)){
            Toast.makeText(this, "Bienvenido a GastroTEC", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Tu correo no es correcto", Toast.LENGTH_LONG).show();
        }



    }

    public void signUp(View view) {
        //
        Intent intentSignIn = new Intent(this, SignUpProcess.class);
        startActivity(intentSignIn);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


}
