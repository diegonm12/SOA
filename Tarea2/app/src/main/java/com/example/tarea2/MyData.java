package com.example.tarea2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyData {

    private byte[] imageData = null;

    // Bitmap to byte[]
    public byte[] bitmapToByte(Bitmap bitmap) {
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
