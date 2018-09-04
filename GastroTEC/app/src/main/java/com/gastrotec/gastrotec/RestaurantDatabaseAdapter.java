package com.gastrotec.gastrotec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class RestaurantDatabaseAdapter {
    static final String DATABASE_NAME = "database.db";
    String ok="OK";
    static final int DATABASE_VERSION = 1;
    public  static byte[] getBitmap;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table RESTAURANT( ID integer primary key autoincrement,NAME  text unique,IMAGE  BLOB,ADDRESS text,TIME text); ";
    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DataBaseHelper dbHelper;
    public  RestaurantDatabaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Method to openthe Database
    public  RestaurantDatabaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    // Method to close the Database
    public void close()
    {
        db.close();
    }
    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
    public String insertEntry(String name, byte[] image, String address, String time)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("NAME", name);
            newValues.put("IMAGE", image);
            newValues.put("ADDRESS", address);
            newValues.put("TIME", time);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("RESTAURANT", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "User Info Saved", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }
    // method to delete a Record of UserName
    public int deleteEntry(String Name)
    {
        String where="NAME=?";
        int numberOFEntriesDeleted= db.delete("RESTAURANT", where, new String[]{Name}) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    // method to get the password  of userName
    public Cursor getSinlgeEntry(String ID)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("RESTAURANT", new String[]{"NAME","ADDRESS","TIME"}, "ID=?", new String[]{ID}, null, null, null);
        if(cursor.getCount()<1) { // UserName Not Exist
            System.out.println("no encontró nada");
            return null;
        }
        cursor.moveToFirst();

        return cursor;
    }

    public Cursor getBitmapFromDB(String ID)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("RESTAURANT", new String[] {"IMAGE"}, "ID=?", new String[]{ID}, null, null, null);
        if(cursor.getCount()<1) { // UserName Not Exist
            System.out.println("no encontró nada");
            return null;
        }
        cursor.moveToFirst();

        return cursor;
    }
    // Method to Update an Existing
    // DE ESTO HAY QUE PREOCUPARSECON EL ADMI
    public void  updateEntry(String name,String password)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("USERNAME", name);
        updatedValues.put("PASSWORD", password);
        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{name});
    }

    public long getProfilesCount() {
        db = dbHelper.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "RESTAURANT");
        db.close();
        return count;
    }
}
