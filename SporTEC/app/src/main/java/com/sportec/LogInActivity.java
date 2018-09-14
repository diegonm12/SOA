package com.sportec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Pattern;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class LogInActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment

        // Callback registration
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request= GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("response",response.toString());
                        getFacebookData(object);
                    }
                });
                Bundle parameters =new Bundle();
                parameters.putString("fields","email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void getFacebookData(JSONObject object) {
        try {
                System.out.println(object.getString("email"));
            }
            catch (JSONException e){
                e.printStackTrace();

            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Metodo que se encargaria del registro del nuevo cliente de la app
    public void signUp(View view) {
        Intent intentSignUp = new Intent(this, SignUpActivity.class);
        startActivity(intentSignUp);
        //linea que hace transicion
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    // metodo que se encarga de hacer el ingreso del usuario, pero aqui se hacen las
    // validaciones del correo y el password
    public void signIn(View view) {

        // estas cuatro lineas quitan el teclado de la pantalla.
        LinearLayout mainLayout;
        mainLayout = findViewById(R.id.activity_login_container);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        //variables que representan el email y la contraseña ingresada
        EditText emailEntry_mainActivity = findViewById(R.id.activity_login_email);
        EditText passwordEntry_mainActivity = findViewById(R.id.activity_login_password);

        // cambia lo que se ingreso a strings y valida que el correo sea correcto
        String emailUser = emailEntry_mainActivity.getText().toString();
        String passwordUser = passwordEntry_mainActivity.getText().toString();
        if (isValid(emailUser) && (!emailUser.equals("")) && (!passwordUser.equals(""))) {

            //String storedPassword = loginDataBaseAdapter.getSinlgeEntry(emailUser);
            String storedPassword = "m"; // deberia ir a la base a buscar el pw y user como arriba


            // revisa si ese usuario tiene el password que el usuario ingreso
            if (passwordUser.equals(storedPassword)) {

                //Mensaje de bienvenida a la aplicacion
                Toast.makeText(this, "Bienvenido a GastroTEC", Toast.LENGTH_LONG).show();

                // se inicia el activity del  menu principal
                Intent intentMainMenu = new Intent(this, MainActivity.class);
                intentMainMenu.putExtra("emailUser", emailUser);
                startActivity(intentMainMenu);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {

                Toast.makeText(this, "El usuario o password es incorrecto", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(this, "Tu correo no es correcto", Toast.LENGTH_LONG).show();
        }


    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }
}
