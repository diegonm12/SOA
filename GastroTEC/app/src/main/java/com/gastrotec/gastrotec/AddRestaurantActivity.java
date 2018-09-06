package com.gastrotec.gastrotec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddRestaurantActivity extends AppCompatActivity {
    RestaurantDatabaseAdapter restaurantDatabaseAdapter; //Adaptador de la base restaurantes
    PlatillosDatabaseAdapter platillosAdapter;           //Adaptador de la base platillos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        //Se inicializa la base de datos que contiene los restaurantes
        restaurantDatabaseAdapter = new RestaurantDatabaseAdapter(getApplicationContext());
        restaurantDatabaseAdapter.open();

        //Se inicializa para base de datos que se utiliza para los platillos
        platillosAdapter = new PlatillosDatabaseAdapter(getApplicationContext());
        platillosAdapter.open();


    }

    public void cancelar(View view) {
        finish();
    }

    public void createRestaurant(View view) {

        // Todos los edit text que se van a utilizar para agregar la informacion
        // del restaurante
        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.mipmap.icono);
        EditText newNameRest = (EditText) findViewById(R.id.nombre_restaurante_add_restaurant_activity);
        EditText newAddressRest = (EditText) findViewById(R.id.direccion_restaurante_add_restaurant_activity);
        EditText newTimeRest = (EditText) findViewById(R.id.horario_restaurante_add_restaurant_activity);
        EditText newHorario1 = (EditText) findViewById(R.id.horario1_add_restaurant_activity);
        EditText newHorario2 = (EditText) findViewById(R.id.horario2_add_restaurant_activity);
        EditText newHorario3 = (EditText) findViewById(R.id.horario3_add_restaurant_activity);
        EditText newPlatillo1 = (EditText) findViewById(R.id.platillo1_add_restaurant_activity);
        EditText newPlatillo2 = (EditText) findViewById(R.id.platillo2_add_restaurant_activity);
        EditText newPlatillo3 = (EditText) findViewById(R.id.platillo3_add_restaurant_activity);

        //obtiene la cantidad de restaurantes que se tienen en la base de datos
        // y se le suma 1 para que sea el proximo ID
        int restCantidad = ((int) restaurantDatabaseAdapter.getProfilesCount())+1;
        String newIDrestaurant = Integer.toString(restCantidad);

        //despues de que el usuario ingresa la informacion, se obtiene desde los EditTExt
        // y luego se ingresan los datos en la base de platillos y restaurantes
        restaurantDatabaseAdapter.insertEntry(newNameRest.getText().toString(), getBitmapAsByteArray(defaultImage),
                newAddressRest.getText().toString(), newTimeRest.getText().toString());
        platillosAdapter.insertEntry(newPlatillo1.getText().toString(),newHorario1.getText().toString(),newIDrestaurant,"0","0");
        platillosAdapter.insertEntry(newPlatillo2.getText().toString(),newHorario2.getText().toString(),newIDrestaurant,"0","0");
        platillosAdapter.insertEntry(newPlatillo3.getText().toString(),newHorario3.getText().toString(),newIDrestaurant,"0","0");
        Toast.makeText(this, "Restaurante agregado", Toast.LENGTH_LONG).show();
        finish();

    }

    //metodo usado para cambiar de bitmaps a arrays
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap to byte[] stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] x = stream.toByteArray();
            //close stream to save memory
            stream.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
