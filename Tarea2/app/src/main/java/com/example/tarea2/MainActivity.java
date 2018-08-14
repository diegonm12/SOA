package com.example.tarea2;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri contentUri = Uri.parse("content://com.example.tarea2/mycontentprovider");
        ContentValues contentValues = new ContentValues();
        //clase usada convertir de bitmap a byte[] para guardar en la base del content provider
        MyData mDataSample = new MyData();
        // image corresponde a la imagen de res que se va a guardar en el content provider
        Bitmap bitmap = ((BitmapDrawable) getDrawable(R.drawable.image)).getBitmap();
        Cursor cursor = getContentResolver().query(contentUri , null, null, null, null);

        if (cursor.getCount() < 1){
            contentValues.put("dir", mDataSample.bitmapToByte( bitmap));
            getContentResolver().insert(contentUri, contentValues);
        }
        System.out.println(cursor.getCount());
        ImageView testImageView = (ImageView) findViewById(R.id.imageView);

        // Display sample bitmap in YOUR ImageView
        testImageView.setImageBitmap(bitmap);



    }
}
