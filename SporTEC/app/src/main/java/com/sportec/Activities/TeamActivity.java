package com.sportec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sportec.R;

public class TeamActivity extends AppCompatActivity {
    public static String mSportSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        String sportSelected = getIntent().getStringExtra("sportSelected");
        mSportSelected = sportSelected;
        System.out.println(mSportSelected);
    }
}
