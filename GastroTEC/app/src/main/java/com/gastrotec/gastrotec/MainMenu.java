package com.gastrotec.gastrotec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;

public class MainMenu extends AppCompatActivity {
    RestaurantDatabaseAdapter restaurantDatabaseAdapter;

    private ViewPager viewpagerTop;
    public static final int ADAPTER_TYPE_TOP = 1;
    public static final int ADAPTER_TYPE_BOTTOM = 2;
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_TRANSITION_IMAGE = "image";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        restaurantDatabaseAdapter = new RestaurantDatabaseAdapter(getApplicationContext());
        setData();
        System.out.println(restaurantDatabaseAdapter.getProfilesCount());

        int[] listItems = {R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4,
                R.mipmap.img5, R.mipmap.img6, R.mipmap.img7, R.mipmap.img8, R.mipmap.img9, R.mipmap.img10};

        init();
        setupViewPager(listItems);
    }

    private void setData() {
        restaurantDatabaseAdapter.open();
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.mipmap.img1);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.mipmap.img2);
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.mipmap.img3);
        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.mipmap.img4);
        restaurantDatabaseAdapter.insertEntry("Casa Luna",getBitmapAsByteArray(bm1),
                "Costado Oeste de la clinica","L a V: 8:00 am a 5:00 pm");
        restaurantDatabaseAdapter.insertEntry("Soda Gym",getBitmapAsByteArray(bm2),
                "Al costado derecho del gimnasio","L a V: 8:00 am a 5:00 pm");
        restaurantDatabaseAdapter.insertEntry("Comedor",getBitmapAsByteArray(bm3),
                "Al frente del pretil","L a V: 7:30 am a 5:00 pm");
        restaurantDatabaseAdapter.insertEntry("Soda del Lago",getBitmapAsByteArray(bm4),
                "En las cercanías del lago del TEC","L a V: 8:00 am a 5:00 pm");

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
    private void setupViewPager(int[] listItems) {
        // Set Top ViewPager Adapter
        MyPagerAdapter adapter = new MyPagerAdapter(this, listItems, ADAPTER_TYPE_TOP);
        viewpagerTop.setAdapter(adapter);




    }
    public void clickEvent(View view) {
        System.out.println("CLICKEASTE");
        switch (view.getId()) {
            case R.id.linMain:
                if (view.getTag() != null) {
                    int position = Integer.parseInt(view.getTag().toString());
                    //Toast.makeText(getApplicationContext(), "Poistion: " + poisition, Toast.LENGTH_LONG).show();

                    System.out.println(position);
                }
                break;
        }
    }
}
