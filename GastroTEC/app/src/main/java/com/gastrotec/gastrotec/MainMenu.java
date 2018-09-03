package com.gastrotec.gastrotec;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    private ViewPager viewpagerTop;
    public static final int ADAPTER_TYPE_TOP = 1;
    public static final int ADAPTER_TYPE_BOTTOM = 2;
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_TRANSITION_IMAGE = "image";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        int[] listItems = {R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4,
                R.mipmap.img5, R.mipmap.img6, R.mipmap.img7, R.mipmap.img8, R.mipmap.img9, R.mipmap.img10};

        init();
        setupViewPager(listItems);
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
