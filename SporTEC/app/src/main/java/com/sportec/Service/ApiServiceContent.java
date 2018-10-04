package com.sportec.Service;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ApiServiceContent {
    private static final String HTTP_PROTOCOL = "http:"; //protocolo http
    private static final String HTTP_SERVER = "192.168.43.141:3000"; // este tiene que ir cambiando

    //construye el endpoint para hacer los  request al servidor
    private static String buildEndPoint(String path) {
        return HTTP_PROTOCOL + "//" + HTTP_SERVER + path;
    }

    // hace el request para obtener toda la informacion segun sea lo que esta buscando el user
    // en el buscador de contenidos, segun sea nombres de user, equpos, deportes etc
    public void downloadSearch(Context context, FutureCallback<JsonObject> arreglo, String wordkey) {
        Ion.with(context).
                load(buildEndPoint("/api/content?search=" + wordkey)).
                asJsonObject().
                setCallback(arreglo);

    }
}
