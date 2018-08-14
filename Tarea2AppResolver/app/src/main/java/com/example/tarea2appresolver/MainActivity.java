package com.example.tarea2appresolver;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Uri contentUri = Uri.parse("content://com.example.tarea2/mycontentprovider");
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(contentUri , null, null, null, null);
                System.out.println(cursor.getCount());
                cursor.moveToFirst();
                byte[] imageBytes = cursor.getBlob( cursor.getColumnIndex("dir")) ;
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                ImageView testImageView = (ImageView) findViewById(R.id.imageView);
                // Display sample bitmap in YOUR ImageView
                testImageView.setImageBitmap(bitmap2);
            }
        });

    }

}
