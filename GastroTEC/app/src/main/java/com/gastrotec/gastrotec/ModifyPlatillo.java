package com.gastrotec.gastrotec;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class ModifyPlatillo extends AppCompatActivity {

    Restaurant restaurantGsonRecieved;
    PlatillosDatabaseAdapter adapterPlatillos;
    String restaurantID;
    String[] listaComida= new String[3];
    String[] listaHorario= new String[3];
    EditText platillo1;
    EditText platillo2;
    EditText platillo3;
    EditText horario1;
    EditText horario2;
    EditText horario3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_platillo);


        //Para poder manejar los datos de la base de datos de los platillos
        adapterPlatillos = new PlatillosDatabaseAdapter(getApplicationContext());
        adapterPlatillos.open();

        //Recibiendo el gson
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        restaurantGsonRecieved = gson.fromJson(strObj, Restaurant.class);

        //Obtengo esos valores que adquieri mediante GSON para mandarlos a los tabs
        // los cuales van a imprimir la informacion, se manda con un bundle
        // ya que esa fue la unica forma que se investigo para mandar informacion a fragments
        Bundle bundle = new Bundle();
        String restName = restaurantGsonRecieved.getName();
        String restAddress = restaurantGsonRecieved.getAddress();
        String restTime = restaurantGsonRecieved.getTime();
        String restID = restaurantGsonRecieved.getID();
        bundle.putCharSequence("name", restName);
        bundle.putCharSequence("address", restAddress);
        bundle.putCharSequence("time", restTime);
        bundle.putCharSequence("ID",restID);

        restaurantID = restaurantGsonRecieved.getID();


        platillo1 = (EditText) findViewById(R.id.platillo1_modify_platillo);
        platillo2 = (EditText) findViewById(R.id.platillo2_modify_platillo);
        platillo3 = (EditText) findViewById(R.id.platillo3_modify_platillo);

        horario1= (EditText) findViewById(R.id.horario1_modify_platillo);
        horario2= (EditText) findViewById(R.id.horario2_modify_platillo);
        horario3= (EditText) findViewById(R.id.horario3_modify_platillo);

        //Se obtienen los valores de la base de datos
        getDataPlatillo();

        platillo1.setText(listaComida[0]);
        platillo2.setText(listaComida[1]);
        platillo3.setText(listaComida[2]);

        horario1.setText(listaHorario[0]);
        horario2.setText(listaHorario[1]);
        horario3.setText(listaHorario[2]);




    }

    public void cancelar(View view) {
        finish();
    }

    private void getDataPlatillo() {
        Cursor item =  adapterPlatillos.getRestaurantsbyID(restaurantID.toString());
        int count =item.getCount();

        for(int i = 0; i < count; i++){
            listaComida[i] = item.getString(1);
            listaHorario[i] = item.getString(2);
            item.moveToNext();

        }

    }

    public void moficarPlato(View view) {
        int basePlatilloToInt = Integer.parseInt(restaurantID);
        basePlatilloToInt = ((basePlatilloToInt - 1)*3)+1;
        String basePlatillo = Integer.toString(basePlatilloToInt);

        adapterPlatillos.updatePlatilloHorario(basePlatillo,platillo1.getText().toString()
                                                ,horario1.getText().toString());

        int basePlatilloToInt1 = Integer.parseInt(basePlatillo);
        basePlatilloToInt1 = basePlatilloToInt1 + 1;
        String basePlatillo1 = Integer.toString(basePlatilloToInt1);
        adapterPlatillos.updatePlatilloHorario(basePlatillo1,platillo2.getText().toString()
                                                ,horario2.getText().toString());
        int basePlatilloToInt2 = Integer.parseInt(basePlatillo1);
        basePlatilloToInt2 = basePlatilloToInt2 + 1;
        String basePlatillo2 = Integer.toString(basePlatilloToInt2);
        adapterPlatillos.updatePlatilloHorario(basePlatillo2,platillo3.getText().toString()
                                                ,horario3.getText().toString());
        Toast.makeText(this, "Se han modificado tus platillos y horarios", Toast.LENGTH_LONG).show();

        finish();


    }
}
