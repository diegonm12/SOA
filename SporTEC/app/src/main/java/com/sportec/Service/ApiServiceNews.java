package com.sportec.Service;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ApiServiceNews {

    private static final String HTTP_PROTOCOL = "http:";  //protocolo http
    private static final String HTTP_SERVER = "192.168.43.141:3000"; // este tiene que ir cambiando

    //construye el endpoint para hacer los  request al servidor
    private static String buildEndPoint(String path) {
        return HTTP_PROTOCOL + "//" + HTTP_SERVER + path;
    }

    // hace el request para descargar todas las noticias
    public void downloadNews(Context context, FutureCallback<JsonArray> arreglo) {
        Ion.with(context).
                load(buildEndPoint("/api/news")).
                asJsonArray().
                setCallback(arreglo);
    }

    // hace el request para descargar todas las noticias segun sea el identificador de la  base
    public void downloadNewById(Context context, FutureCallback<JsonObject> arreglo, String id) {
        Ion.with(context).
                load(buildEndPoint("/api/news/id/" + id)).
                asJsonObject().
                setCallback(arreglo);
    }
}
