package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.R;
import com.sportec.Service.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;


public class LogInActivity extends AppCompatActivity {
    public static CallbackManager mCallbackManager;     // callback manager definido para el logeo con el facebook
    public static String mFirstName, mLastName, mEmail;     //se definen los atributos del user
    public static URL mProfilePicture;  // corresponde a la URL que posee la imagen de la foto del user
    public static String mUserId;   //el identificador de la base de datos del usuario
    public static String mTAG = "LoginActivity";    //tag que define la actividad actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //se hace el llamado del callback,  que se utilizar para el inicio de sesion desde
        // facebook
        mCallbackManager = CallbackManager.Factory.create();

        //corresponde al boton  de facebook para registrarse con el mismo
        final LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");

        //se hace el listener para el boton mencionado anterioremente para que tenga
        // funcion
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //se usa graphrequest para obtener todos los datos desde la cuenta de Facebook
                //posteriormente en el oncompleted, se asigna los valores que se obtienen de la
                //cuenta de facebook a los valores members de la clase LogInActivity
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.e(mTAG, object.toString());
                        Log.e(mTAG, response.toString());

                        try {

                            // en este try se obtiene la informacion desde facebook
                            // para este caso se obtienen los nombres, el correo y la foto de perfil
                            mUserId = object.getString("id");
                            mProfilePicture = new URL("https://graph.facebook.com/" + mUserId + "/picture?width=150&height=150");

                            if (object.has("first_name"))
                                mFirstName = object.getString("first_name");
                            if (object.has("last_name"))
                                mLastName = object.getString("last_name");
                            if (object.has("email"))
                                mEmail = object.getString("email");

                            // se definen todos los atributos de user
                            String name = mFirstName + " " + mLastName;
                            JsonArray favSports = new JsonArray();

                            // aqui hay que meter al usuario en la base de datos, despues de
                            // obtener todos losdatos que se necesitan
                            JsonObject json = new JsonObject();
                            json.addProperty("name", name);
                            json.addProperty("email", mEmail);
                            json.addProperty("password", "facebook");
                            json.addProperty("profilePicture", mProfilePicture.toString());
                            json.addProperty("sessionInit", "1");
                            json.add("favSport", favSports);
                            json.addProperty("type", "user");

                            //se hace uso de la api de user para poder agregar el user
                            // cuando ya se tiene el resultado del callback se va a la pantalla de elegir
                            // los  deportes favoritos
                            final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
                                @Override
                                public void onCompleted(Exception e, JsonArray result) {
                                    Intent intentSportsActivity = new Intent(LogInActivity.this, CheckSportsActivity.class);
                                    intentSportsActivity.putExtra("emailUser", mEmail);
                                    intentSportsActivity.putExtra("permission", "1");
                                    startActivity(intentSportsActivity);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            };
                            ApiService api = new ApiService();
                            api.addUser(LogInActivity.this, arreglo, json);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //Aqui se retornan los campos requeridos para que se haga el login con face
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Metodo que se encargaria del registro del nuevo cliente de la app
    public void signUp(View view) {

        //nuevo intent para ir a la parte del registro
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
        final String emailUser = emailEntry_mainActivity.getText().toString();
        final String passwordUser = passwordEntry_mainActivity.getText().toString();
        if (isValid(emailUser) && (!emailUser.equals("")) && (!passwordUser.equals(""))) {
            final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    if (result != null) {
                        if (passwordUser.matches(result.get("password").getAsString())) {
                            Toast.makeText(LogInActivity.this, "Bienvenido a SporTEC", Toast.LENGTH_LONG).show();

                            // se llama registrar sesion para llevar registro de quien es el user que tiene
                            // la sesion abierta
                            registrarSesion(result.get("name").getAsString(),
                                    emailUser, passwordUser,
                                    result.get("profilePicture").getAsString(), result.get("favSport"));

                            // aqui hace el inicio de sesion por que coinciden correo y contraseña y se envia el  correo del user actual
                            Intent intentMainMenu = new Intent(LogInActivity.this, MainActivity.class);
                            intentMainMenu.putExtra("emailUser", emailUser);
                            startActivity(intentMainMenu);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        } else {
                            Toast.makeText(LogInActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LogInActivity.this, "Correo incorrecto", Toast.LENGTH_LONG).show();
                    }
                }
            };
            ApiService api = new ApiService();
            api.downloadUser(this, emailUser, arreglo);

        } else {
            Toast.makeText(this, "Tu correo no es correcto", Toast.LENGTH_LONG).show();
        }


    }

    // este metodo  es utilizado para cambiar el campo donde se mantiene los registros de
    // sesion, para que no se cierre cuando el app deja de funcionar
    // le cambia al user el campode sessionInit
    private void registrarSesion(String name, String emailUser, String password, String profPic, JsonElement favSport) {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email", emailUser);
        json.addProperty("password", password);
        json.addProperty("profilePicture", profPic);
        json.addProperty("sessionInit", "1");
        json.add("favSport", favSport);
        json.addProperty("type", "user");
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {


            }
        };
        ApiService api = new ApiService();
        api.updateUser(LogInActivity.this, arreglo, json, emailUser);
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
