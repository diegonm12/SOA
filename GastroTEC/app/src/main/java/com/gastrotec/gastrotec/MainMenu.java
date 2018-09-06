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
    public int restaurants = 4;
    String userEmail;
    LoginDatabaseAdapter loginDataBaseAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);




        //Obtenemos el email de este otro lado

        Bundle extras = getIntent().getExtras();
        userEmail= extras.getString("emailUser");


        restaurantDatabaseAdapter = new RestaurantDatabaseAdapter(getApplicationContext());
        restaurantDatabaseAdapter.open();
        loginDataBaseAdapter  = new LoginDatabaseAdapter(getApplicationContext());
        loginDataBaseAdapter.open();
        setData();
        Bitmap[] listItems = new Bitmap[restaurants];
        listItems = getListItems();

        init();
        setupViewPager(listItems);
    }
    // aqui tengo que acomodar la lista cada vez, dependiendo del mas votado
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

    private void setData() {

        if (restaurantDatabaseAdapter.getProfilesCount() == restaurants)
            {
            }
        else {
            Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.mipmap.icono);
            Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.mipmap.icono);
            Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.mipmap.icono);
            Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.mipmap.icono);
            restaurantDatabaseAdapter.insertEntry("Casa Luna", getBitmapAsByteArray(bm1),
                    "Costado Oeste de la clinica", "L a V: 8:00 am a 5:00 pm");
            restaurantDatabaseAdapter.insertEntry("Soda Gym", getBitmapAsByteArray(bm2),
                    "Al costado derecho del gimnasio", "L a V: 8:00 am a 5:00 pm");
            restaurantDatabaseAdapter.insertEntry("Comedor", getBitmapAsByteArray(bm3),
                    "Al frente del pretil", "L a V: 7:30 am a 5:00 pm");
            restaurantDatabaseAdapter.insertEntry("Soda del Lago", getBitmapAsByteArray(bm4),
                    "En las cercanías del lago del TEC", "L a V: 8:00 am a 5:00 pm");
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
                    int position = Integer.parseInt(view.getTag().toString());
                    Restaurant restaurantToShow = new Restaurant();
                    Cursor item = restaurantDatabaseAdapter.getSinlgeEntry(String.valueOf(position+1));
                    restaurantToShow.setName(item.getString(0));
                    restaurantToShow.setAddress(item.getString(1));
                    restaurantToShow.setTime(item.getString(2));
                    restaurantToShow.setID(String.valueOf(position+1));
                    Gson gsonRestaurant = new Gson();
                    Intent intentRestaurantInfo = new Intent(this, RestProfileActivity.class);
                    intentRestaurantInfo.putExtra("obj", gsonRestaurant.toJson(restaurantToShow));
                    startActivity(intentRestaurantInfo);
                    this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
        Cursor cursorCheck = loginDataBaseAdapter.getAllInfoUser(userEmail);
        String compare = cursorCheck.getString(0);

        if(compare.equals("1")){
            System.out.println("Soy ADmi");
        }

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


}
