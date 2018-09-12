package com.sportec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void signUp(View view) {
        //
        Intent intentSignUp = new Intent(this, SignUpActivity.class);
        startActivity(intentSignUp);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
