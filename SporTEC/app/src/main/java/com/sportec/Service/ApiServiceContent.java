package com.sportec.Service;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sportec.Activities.MainActivity;

public class ApiServiceContent {
    private static final String HTTP_PROTOCOL = "http:";
    private static final String HTTP_SERVER = "192.168.1.128:3000"; // este tiene que ir cambiando

    private static String buildEndPoint(String path) {
        return HTTP_PROTOCOL + "//" + HTTP_SERVER + path;
    }

    public void downloadNewsSearch(Context context, FutureCallback<JsonArray> arreglo,String wordkey) {
        Ion.with(context).
                load(buildEndPoint("/api/news?search=" + wordkey)).
                asJsonArray().
                setCallback(arreglo);
        Ion.with(context).
                load(buildEndPoint("/api/users?search=" + wordkey)).
                asJsonArray().
                setCallback(arreglo);

    }
}
