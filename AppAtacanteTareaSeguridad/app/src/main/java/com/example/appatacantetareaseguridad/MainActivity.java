package com.example.appatacantetareaseguridad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.main_activity_button_vibrate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // cuando se oprime el boton se llama al action reciever com.excample.vibrate
                Intent intent = new Intent("com.example.vibrate");
                getApplicationContext().sendBroadcast(intent);
                Toast.makeText(getBaseContext(), "Haciendo vibración", Toast.LENGTH_LONG).show();
            }
        });
    }
}
