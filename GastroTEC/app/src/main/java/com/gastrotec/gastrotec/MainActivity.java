package com.gastrotec.gastrotec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void signUp(View view) {
        EditText emailEntry = (EditText)findViewById(R.id.email_main_activity);
        EditText passwordEntry = (EditText)findViewById(R.id.password_main_activity);

        String emailUser = emailEntry.getText().toString();
        String passwordUser = passwordEntry.getText().toString();
        if (isValid(emailUser)){
            Toast.makeText(this, "Welcome to GastroTEC", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Your email isn't correct", Toast.LENGTH_LONG).show();
        }



    }

    public void signIn(View view) {
        Intent intentSignIn = new Intent(this, SignInProcess.class);
        startActivity(intentSignIn);
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
