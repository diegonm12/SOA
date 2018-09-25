package com.sportec.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportec.R;

public class SportSelectedActivity extends AppCompatActivity {
    public static String mSportSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_selected);

        //recibo el nombre del deporte que se selecciono para poder saber de cual deporte se habla
        String sportSelected = getIntent().getStringExtra("sportSelected");
        mSportSelected = sportSelected;
    }

    public void goToChallenges(View view) {
        Intent intentChallenges = new Intent(SportSelectedActivity.this, ChallengesActivity.class);
        intentChallenges.putExtra("sportSelected", mSportSelected);
        startActivity(intentChallenges);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void goToTeam(View view) {
        Intent intentTeams = new Intent(SportSelectedActivity.this, TeamActivity.class);
        intentTeams.putExtra("sportSelected", mSportSelected);
        startActivity(intentTeams);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
