package com.gastrotec.gastrotec;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;

public class MainMenu extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;
    RestaurantDatabaseAdapter restaurantDatabaseAdapter;
    private ViewPager viewpagerTop;

    public static final int ADAPTER_TYPE_TOP = 1;
    public static final int ADAPTER_TYPE_BOTTOM = 2;
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_TRANSITION_IMAGE = "image";

    public int restaurants;
    boolean primeraVez = true;
    public static String userEmail;
    LoginDatabaseAdapter loginDataBaseAdapter;

    boolean administrador = false;
    PlatillosDatabaseAdapter platilloDatabaseAdapter;

    int cantidad_platillos;
    boolean modifyRestaurant;
    boolean modifyPlatillo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //variable usada cuando se quiere modificar un restaurante
        modifyRestaurant = false;
        modifyPlatillo = false;



        //Obtenemos el email de este otro lado
        Bundle extras = getIntent().getExtras();
        userEmail= extras.getString("emailUser");
        System.out.println(userEmail);


        //En esta parte se definen los adaptadores de las bases de datos
        // en este caso la base restaurantes, login y platillos
        restaurantDatabaseAdapter = new RestaurantDatabaseAdapter(getApplicationContext());
        restaurantDatabaseAdapter.open();
        loginDataBaseAdapter  = new LoginDatabaseAdapter(getApplicationContext());
        loginDataBaseAdapter.open();
        platilloDatabaseAdapter = new PlatillosDatabaseAdapter(getApplicationContext());
        platilloDatabaseAdapter.open();

        //Se obtiene la cantidad de platillos que existen en la base platillos
        // ademas de la cantidad de restaurantes
        cantidad_platillos = (int) platilloDatabaseAdapter.getProfilesCount();
        restaurants = (int) restaurantDatabaseAdapter.getProfilesCount();

        //Se obtiene la informacion del usuario que esta en el moment en el home
        Cursor cursorCheck = loginDataBaseAdapter.getAllInfoUser(userEmail);
        String compare = cursorCheck.getString(0);

        // se obtiene la de la base restaurantes y platillos, los correspondientes
        setData();
        setDataPlatillos();

        // Aqui se hacen visibles los botones que va a utilizar el administrador
        // para manejar los restaurantes.
        if(compare.equals("1")){
            administrador = true;

            //En este caso estamos ante un administrador por que el id es uno
            //Entonces deben aparecer los botones que deben utilizar
            Button buttonAddHide = (Button) findViewById(R.id.button_main_menu_add_admi);
            Button buttonDeleteHide = (Button) findViewById(R.id.button_main_menu_modify_platillo_admi);
            Button buttonModifyHide = (Button) findViewById(R.id.button_main_menu_modify_admi);
            buttonAddHide.setVisibility(View.VISIBLE);
            buttonDeleteHide.setVisibility(View.VISIBLE);
            buttonModifyHide.setVisibility(View.VISIBLE);

        }


    }

    protected void onResume() {
        super.onResume();

        // ya en el resumen obtiene la cantidad de restaurantes
        restaurants = (int)restaurantDatabaseAdapter.getProfilesCount();

        //obtiene los items que actualmente estan en las bases
        Bitmap[] listItems = new Bitmap[restaurants];
        listItems = getListItems();

        // se inicia de nuevo el carrousel
        init();
        setupViewPager(listItems);


    }


    // en este metodo se obtienen los items que existen en la base de restaurantes
    public Bitmap[] getListItems() {
        Bitmap[] listReturn = new Bitmap[restaurants];
        Cursor item;
        byte[] imageByte;
        Bitmap bitmapElement;
        for(int i = 0; i < restaurants; i++){
            item = restaurantDatabaseAdapter.getBitmapFromDB(String.valueOf(i+1));
            imageByte = item.getBlob(item.getColumnIndex("IMAGE"));
            bitmapElement = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
            listReturn[i] = bitmapElement;
        }

        return  listReturn;


    }

    //metodo que mete la informacion de los restaurantes al inicio
    private void setData() {

        if(restaurants >= 4){

        }
        else {

            Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.mipmap.img1);
            Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.mipmap.img2);
            Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.mipmap.img3);
            Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.mipmap.img4);
            restaurantDatabaseAdapter.insertEntry("Casa Luna", getBitmapAsByteArray(bm1),
                    "Costado Oeste de la clinica", "L a V: 8:00 am a 5:00 pm");
            restaurantDatabaseAdapter.insertEntry("Soda Gym", getBitmapAsByteArray(bm2),
                    "Al costado derecho del gimnasio", "L a V: 8:00 am a 5:00 pm");
            restaurantDatabaseAdapter.insertEntry("Comedor", getBitmapAsByteArray(bm3),
                    "Al frente del pretil", "L a V: 7:30 am a 5:00 pm");
            restaurantDatabaseAdapter.insertEntry("Soda del Lago", getBitmapAsByteArray(bm4),
                    "En las cercanías del lago del TEC", "L a V: 8:00 am a 5:00 pm");
            primeraVez = false;
        }

    }

    //metodo que mete la informacion de los platillos en la base
    private void setDataPlatillos() {

            if(cantidad_platillos >= 12){

            }
            else {

                platilloDatabaseAdapter.insertEntry("Emparedados de atun", "8:00 am - 10:00am",
                        "1", "2", "3");
                platilloDatabaseAdapter.insertEntry("Arroz con pollo", "12:00 am - 1:00pm",
                        "1", "5", "2");
                platilloDatabaseAdapter.insertEntry("Empanada de pollo", "3:00 pm - 4:30pm",
                        "1", "5", "2");
                platilloDatabaseAdapter.insertEntry("Pinto con huevo", "8:00 am - 9:30am",
                        "2", "2", "0");
                platilloDatabaseAdapter.insertEntry("Arroz con camarones", "12:00 am - 2:00pm",
                        "2", "12", "2");
                platilloDatabaseAdapter.insertEntry("Empanada de carne", "3:00 pm - 4:30pm",
                        "2", "5", "9");
                platilloDatabaseAdapter.insertEntry("Emparedado de jalea", "7:30 am - 9:00am",
                        "3", "2", "0");
                platilloDatabaseAdapter.insertEntry("Hamburguesas con papas", "11:30 am - 1:00pm",
                        "3", "12", "2");
                platilloDatabaseAdapter.insertEntry("Arepas", "3:00 pm - 5:00pm",
                        "3", "0", "9");
                platilloDatabaseAdapter.insertEntry("Empanada de papa", "8:00 am - 9:00am",
                        "4", "2", "0");
                platilloDatabaseAdapter.insertEntry("Pollo frito", "11:30 am - 1:30pm",
                        "4", "12", "2");
                platilloDatabaseAdapter.insertEntry("Burritos de frijol", "3:00 pm - 5:00pm",
                        "4", "8", "5");
            }

    }

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

    private void init() {
        viewpagerTop = (ViewPager) findViewById(R.id.viewpagerTop_main_menu);

        viewpagerTop.setClipChildren(false);
        viewpagerTop.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.pager_margin));
        viewpagerTop.setOffscreenPageLimit(3);
        viewpagerTop.setPageTransformer(false, new CarouselEffectTransformer(this)); // Set transformer
    }

    /**
     * Setup viewpager and it's events
     */
    private void setupViewPager(Bitmap[] listItems) {
        // Set Top ViewPager Adapter
        MyPagerAdapter adapter = new MyPagerAdapter(this, listItems, ADAPTER_TYPE_TOP);
        adapter.getCount();
        viewpagerTop.setAdapter(adapter);




    }
    public void clickEvent(View view) {
        System.out.println("CLICKEASTE");
        switch (view.getId()) {
            case R.id.linMain:
                if (view.getTag() != null) {
                    if (modifyRestaurant) {
                        if(modifyPlatillo){

                            //en este caso se modificara los platillos y los horarios de
                            // algun restaurante
                            this.modifyPlatillo  = false;
                            this.modifyRestaurant = false;
                            int position = Integer.parseInt(view.getTag().toString());

                            //Se obtiene la informacion del restaurante
                            Restaurant restaurantToModifyPlatillo = new Restaurant();
                            Cursor item = restaurantDatabaseAdapter.getSinlgeEntry(String.valueOf(position + 1));

                            //  a la variable local restaurante se modifica con los datos que se obtuvieron
                            // en la parte de arriba
                            restaurantToModifyPlatillo.setName(item.getString(0));
                            restaurantToModifyPlatillo.setAddress(item.getString(1));
                            restaurantToModifyPlatillo.setTime(item.getString(2));
                            restaurantToModifyPlatillo.setID(String.valueOf(position + 1));

                            //Se usa GSON para mandar todos el objeto completo
                            Gson gsonRestaurant = new Gson();
                            Intent intentRestaurantModifyPlatillo = new Intent(this, ModifyPlatillo.class);
                            intentRestaurantModifyPlatillo.putExtra("obj", gsonRestaurant.toJson(restaurantToModifyPlatillo));
                            startActivity(intentRestaurantModifyPlatillo);
                            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                        }
                        else {
                            //aqui se modificaria los restaurantes
                            this.modifyRestaurant = false;
                            int position = Integer.parseInt(view.getTag().toString());

                            //obtengo elrestaurante de la base de datos
                            Restaurant restaurantToModify = new Restaurant();
                            Cursor item = restaurantDatabaseAdapter.getSinlgeEntry(String.valueOf(position + 1));

                            // modifico las caracteristicas del restaurante
                            restaurantToModify.setName(item.getString(0));
                            restaurantToModify.setAddress(item.getString(1));
                            restaurantToModify.setTime(item.getString(2));
                            restaurantToModify.setID(String.valueOf(position + 1));

                            //creo el gson
                            Gson gsonRestaurant = new Gson();
                            Intent intentRestaurantModify = new Intent(this, ModifyRestaurantActivity.class);
                            intentRestaurantModify.putExtra("obj", gsonRestaurant.toJson(restaurantToModify));
                            startActivity(intentRestaurantModify);
                            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }


                    } else {
                        int position = Integer.parseInt(view.getTag().toString());
                        Restaurant restaurantToShow = new Restaurant();
                        Cursor item = restaurantDatabaseAdapter.getSinlgeEntry(String.valueOf(position + 1));

                        //obtengo los valores que tienen los restaurantes
                        restaurantToShow.setName(item.getString(0));
                        restaurantToShow.setAddress(item.getString(1));
                        restaurantToShow.setTime(item.getString(2));
                        restaurantToShow.setID(String.valueOf(position + 1));

                        //creo el gson restaurante para mandarlo a la otra layout
                        Gson gsonRestaurant = new Gson();
                        Intent intentRestaurantInfo = new Intent(this, RestProfileActivity.class);
                        intentRestaurantInfo.putExtra("obj", gsonRestaurant.toJson(restaurantToShow));
                        startActivity(intentRestaurantInfo);
                        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menubutton, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.mi_perfil_MainMenu:
                Toast.makeText(this, "Mi perfil", Toast.LENGTH_LONG).show();
                Intent intentPerfilUser = new Intent(this, UserProfile.class);
                intentPerfilUser.putExtra("emailUser", userEmail);
                startActivity(intentPerfilUser);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.cerrar_sesion_MainMenu:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //funcion que realiza el boton add restaurante
    public void addRestaurant(View view) {
        Toast.makeText(this, "Se agregará restaurante", Toast.LENGTH_LONG).show();
        Intent intentAddRestaurant = new Intent(this, AddRestaurantActivity.class);
        startActivity(intentAddRestaurant);
        intentAddRestaurant.putExtra("emailUser", userEmail);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    //funcion que realiza el boton modificar restaurante
    public void modifyRestaurant(View view) {
        Toast.makeText(this, "Selecciona el restaurante a modificar", Toast.LENGTH_LONG).show();
        this.modifyRestaurant = true;

    }

    //funcion que realiza el boton modificar platillo
    public void modifyPlatillo(View view) {
        Toast.makeText(this, "Selecciona el restaurante con el platillo a modificar", Toast.LENGTH_LONG).show();
        this.modifyPlatillo = true;
        this.modifyRestaurant = true;
    }
}
