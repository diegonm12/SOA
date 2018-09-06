package com.gastrotec.gastrotec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class ModifyRestaurantActivity extends AppCompatActivity {
    Restaurant restaurantGsonRecieved;
    RestaurantDatabaseAdapter restaurantDatabaseAdapter;
    EditText nameRestaurant;
    EditText addressRestaurant;
    EditText timeRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_restaurant);

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

        // Se usa para enseñar al admin cuales son los valores que tienen los restaurantes
        nameRestaurant = (EditText) findViewById(R.id.nombre_restaurante_modify_restaurant_activity);
        addressRestaurant = (EditText) findViewById(R.id.direccion_modify_restaurant_activity);
        timeRestaurant = (EditText) findViewById(R.id.horario_modify_restaurant_activity);

        //Se cambian los valores para que la informacion se muestre
        nameRestaurant.setText(restaurantGsonRecieved.getName());
        addressRestaurant.setText(restaurantGsonRecieved.getAddress());
        timeRestaurant.setText(restaurantGsonRecieved.getTime());




        // llamo a la base de datos para poderla utilizar
        restaurantDatabaseAdapter = new RestaurantDatabaseAdapter(getApplicationContext());
        restaurantDatabaseAdapter.open();
    }

    public void cancelModification(View view) {
        finish();
    }

    public void modifyRestaurant(View view) {
        String newNameRest = nameRestaurant.getText().toString();
        String newAddressRest = addressRestaurant.getText().toString();
        String newTimeRest = timeRestaurant.getText().toString();
        System.out.println(newNameRest);
        System.out.println(newAddressRest);
        System.out.println(newTimeRest);
        restaurantDatabaseAdapter.updateEntry(restaurantGsonRecieved.getID(),newNameRest,
                newAddressRest,newTimeRest);
        Toast.makeText(this, "Restaurante Actualizado", Toast.LENGTH_LONG).show();
        finish();
    }
}
