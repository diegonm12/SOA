package com.example.tarea2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBaseManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dataBase.db";
    private static final String TABLE_IMAGES = "IMAGES";
    public static final class TableImages implements BaseColumns {//
        private TableImages() {}
        public static final String ID_IMAGE = "_id";
        public static final String DIR = "dir";
    }

    // Sentencias para la creación de tablas
    private static final String IMAGES_TABLE_CREATE = "create table " + TABLE_IMAGES
            + " (" + TableImages.ID_IMAGE + " integer primary key autoincrement, "
            + TableImages.DIR + " BLOB not null unique );";

    public DataBaseManager(Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IMAGES_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGES);
        onCreate(db);
    }
}
