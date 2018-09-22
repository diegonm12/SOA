package com.sportec.Service;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ApiService {
    private static final String HTTP_PROTOCOL = "http:";
    private static final String HTTP_SERVER = "192.168.1.128:3000"; // este tiene que ir cambiando

    private static String buildEndPoint(String path) {
        return HTTP_PROTOCOL + "//" + HTTP_SERVER + path;
    }

    public void downloadUsers(Context context, FutureCallback<JsonArray> arreglo) {
        Ion.with(context).
                load(buildEndPoint("/api/users")).
                asJsonArray().
                setCallback(arreglo);
    }

    public void downloadUser(Context context, String email, FutureCallback<JsonObject> arreglo) {

        //traigo el email del user para buscarlo y asi obtener sus datos

        Ion.with(context).
                load(buildEndPoint("/api/users/" + email)).
                asJsonObject().
                setCallback(arreglo);

    }

    // agrega un usuario a la base de datos por medio del servicio post
    public void addUser(Context context, FutureCallback<JsonArray> arreglo, JsonObject json) {
        Ion.with(context).
                load(buildEndPoint("/api/users")).
                setJsonObjectBody(json).
                asJsonArray().
                setCallback(arreglo);
    }

    // actualiza un usuario a la base de datos por medio del servicio put
    public void updateUser(Context context, FutureCallback<JsonArray> arreglo, JsonObject json, String email) {
        Ion.with(context).
                load("PUT", buildEndPoint("/api/users/" + email)).
                setJsonObjectBody(json).
                asJsonArray().
                setCallback(arreglo);
    }
}
