package com.example.tarea2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    //NO SE DEBE OLVIDAR QUE EL URI DEBE SER ÚNICO
    public static final String Authority = "com.example.tarea2";

    public static final String uri = "content://"+Authority+"/"+"mycontentprovider";

    //Creamos el Uri a partir de la cadena con el idenficador único
    public static final Uri CONTENT_URI = Uri.parse(uri);

    //Ahora debemos indicar cómo interpretar las uris y diferenciar si nos
    //estamos refiriendo a tod el contenido de una tabla o bien sólo a un registro
    //Para eso utilizamos UriMatcher
    public static final int ALL_ELEMENTS = 1;
    public static final int SINGLE_ELEMENT = 2;
    private static UriMatcher URI_MATCHER = null;
    static{
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(Authority,"mycontentprovider",ALL_ELEMENTS);
        URI_MATCHER.addURI(Authority,"mycontentprovider/#",SINGLE_ELEMENT);
    }

    private SQLiteDatabase baseDatos;
    @Override
    //Se crea la base de datos. Se devuelve true si la base de datos está abierta
    public boolean onCreate() {
        DataBaseManager dbHelper;
        dbHelper = new DataBaseManager(this.getContext());
        baseDatos = dbHelper.getWritableDatabase();
        System.out.println("SE CREO LA BASEEEE!!!!!!!!!!!!!!!!!!");
        return baseDatos!=null && baseDatos.isOpen();
    }

    @Nullable
    @Override
    //Consulta a la base de datos con nuestra tabla. Devuelve el cursor con el contenido de la consulta
    public Cursor query(Uri u, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        String id;
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
        qBuilder.setTables("IMAGES");
        //Si es una consulta a un ID concreto construimos el WHERE con el id correspondiente
        String where = selection;
        if(URI_MATCHER.match(u) == SINGLE_ELEMENT){
            id = u.getPathSegments().get(1);
            qBuilder.appendWhere("_id = "+id);
        }

        return qBuilder.query(baseDatos, projection, where,selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    // Devuelve el tipo MIME a partir de un URI pasado como parámetro
    public String getType(Uri u) {
        switch(URI_MATCHER.match(u)){
            case ALL_ELEMENTS:
                //Se trata de un conjunto de recursos
                return "vnd.android.cursor.dir/vnd.com.example.tarea2";
            case SINGLE_ELEMENT:
                //Se trata de un recurso único
                return "vnd.android.cursor.item/vnd.com.example.tarea2";
            default:
                throw new IllegalArgumentException("URI no valida: "+ u);
        }
    }

    @Nullable
    public Uri insert(Uri u, ContentValues values) {
        long fila = baseDatos.insert("IMAGES", null, values);
        if (fila>0) {
            return ContentUris.withAppendedId(CONTENT_URI, fila);
        }else {
            throw new SQLException("Bad Insert: "+u);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
