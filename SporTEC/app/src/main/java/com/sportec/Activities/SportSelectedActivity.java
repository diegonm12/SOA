package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sportec.R;

public class SportSelectedActivity extends AppCompatActivity {
    public static String mSportSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_selected);

        //recibo el nombre del deporte que se selecciono para poder saber de cual deporte se habla
        mSportSelected = getIntent().getStringExtra("sportSelected");
    }

    // en este caso el  user eligio la opcion de un a los retos, entonces se hace un
    //intent a retos
    public void goToChallenges(View view) {
        Intent intentChallenges = new Intent(SportSelectedActivity.this, ChallengesActivity.class);
        intentChallenges.putExtra("sportSelected", mSportSelected);
        startActivity(intentChallenges);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //en este caso el user eligio ir a la pantalla de equipis
    //por lo que se hace un intent a equipos
    public void goToTeam(View view) {
        Intent intentTeams = new Intent(SportSelectedActivity.this, TeamActivity.class);
        intentTeams.putExtra("sportSelected", mSportSelected);
        startActivity(intentTeams);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void goBack(View view) {
        finish();
    }
}
