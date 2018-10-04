package com.sportec.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sportec.R;

public class ChallengesActivity extends AppCompatActivity {

    //on create utilizado para la vista de los retos
    //falta la implementacion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
    }

    //funcion que te envia hacia atras la menu principal
    public void goBack(View view) {
        finish();
    }
}
