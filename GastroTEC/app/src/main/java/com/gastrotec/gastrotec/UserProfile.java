package com.gastrotec.gastrotec;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    String userEmail;
    User currentUser;
    LoginDatabaseAdapter loginDataBaseAdapter;
    EditText nameEdit;
    EditText careerEdit;
    EditText passwordEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //Obtenemos el email de este otro lado


        loginDataBaseAdapter  = new LoginDatabaseAdapter(getApplicationContext());
        loginDataBaseAdapter.open();

        Bundle extras = getIntent().getExtras();
        userEmail= extras.getString("emailUser");
        getInformationUser();
        nameEdit = (EditText) findViewById(R.id.nombre_user_profile);
        careerEdit = (EditText) findViewById(R.id.carrera_user_profile);
        TextView carnetText = (TextView) findViewById(R.id.carnet_user_profile);
        TextView emailText = (TextView) findViewById(R.id.email_user_profile);
        passwordEdit = (EditText) findViewById(R.id.password_user_profile);
        nameEdit.setText(currentUser.getName());
        careerEdit.setText(currentUser.getCareer());
        carnetText.setText(currentUser.getCarnet());
        emailText.setText(currentUser.getEmail());
        passwordEdit.setText(currentUser.getPassword());
    }

    private void getInformationUser() {

        Cursor infoUser = loginDataBaseAdapter.getAllInfoUser(userEmail);
        this.currentUser =new User();
        currentUser.setName(infoUser.getString(1));
        currentUser.setCareer(infoUser.getString(2));
        currentUser.setCarnet(infoUser.getString(3));
        currentUser.setEmail(userEmail);
        currentUser.setPassword(infoUser.getString(5));

    }

    public void returnHome(View view) {
        finish();
    }

    public void ModifyData(View view) {
        loginDataBaseAdapter.updateEntry(userEmail,nameEdit.getText().toString()
                ,careerEdit.getText().toString(),passwordEdit.getText().toString());
        Toast.makeText(this, "Se han modificado tus datos", Toast.LENGTH_LONG).show();
        finish();


    }


}
