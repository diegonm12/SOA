package com.sportec.Service;

import android.content.Context;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ApiServiceSports {
    private static final String HTTP_PROTOCOL = "http:";
    private static final String HTTP_SERVER = "172.18.29.207:3000"; // este tiene que ir cambiando

    private static String buildEndPoint(String path) {
        return HTTP_PROTOCOL + "//" + HTTP_SERVER + path;
    }

    public void downloadSports(Context context, FutureCallback<JsonArray> arreglo) {
        Ion.with(context).
                load(buildEndPoint("/api/sports")).
                asJsonArray().
                setCallback(arreglo);
    }
}
