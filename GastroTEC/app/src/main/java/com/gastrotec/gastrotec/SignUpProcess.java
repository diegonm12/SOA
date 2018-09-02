package com.gastrotec.gastrotec;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SignUpProcess extends AppCompatActivity {
    String receieveOk;
    LoginDatabaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_process);
        loginDataBaseAdapter=new LoginDatabaseAdapter(getApplicationContext());
        loginDataBaseAdapter=loginDataBaseAdapter.open();
    }


    public void Register(View view) {
        // estas cuatro lineas quitan el teclado de la pantalla.
        LinearLayout mainLayout;
        mainLayout = (LinearLayout)findViewById(R.id.register_form_sign_up_process_activity);
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);


        //variables que representan todos los datos del estuiante que se registra
        EditText emailEntry_Sign_Up = (EditText)findViewById(R.id.email_sign_up_process_activity);
        EditText passwordEntry_Sign_Up = (EditText)findViewById(R.id.password_sign_up_process_activity);
        EditText nameEntry_Sign_Up = (EditText)findViewById(R.id.nombre_sign_up_process_activity);
        EditText carnetEntry_Sign_Up = (EditText)findViewById(R.id.carnet_sign_up_process_activity);
        EditText careerEntry_Sign_Up = (EditText)findViewById(R.id.carrera_sign_up_process_activity);


        // cambia lo que se ingreso a strings y valida que el correo sea correcto
        String emailUser = emailEntry_Sign_Up.getText().toString();
        String passwordUser = passwordEntry_Sign_Up.getText().toString();
        String nameUser = nameEntry_Sign_Up.getText().toString();
        String carnetUser = carnetEntry_Sign_Up.getText().toString();
        String careerUser = careerEntry_Sign_Up.getText().toString();
        MainActivity instance = new MainActivity();



        if (instance.isValid(emailUser) && (!emailUser.equals("")) && (!passwordUser.equals(""))
                && (!nameUser.equals("")) && (!carnetUser.equals(""))
                && (!careerUser.equals("")))
        {
            // se hace elquery a la base de datos para ingresar al estudiante
            receieveOk=loginDataBaseAdapter.insertEntry(nameUser,careerUser,carnetUser,emailUser,passwordUser);
            // se hace un mensaje de que ya se registro a la app.
            Toast.makeText(this, "Registrado en GastroTEC", Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(this, "Ingresa correctamente toda la información", Toast.LENGTH_LONG).show();
        }
    }

    public void goHome(View view) {
        finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }
}
