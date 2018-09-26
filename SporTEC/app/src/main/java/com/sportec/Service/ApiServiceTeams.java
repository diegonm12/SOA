package com.sportec.Service;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ApiServiceTeams {
    private static final String HTTP_PROTOCOL = "http:";
    private static final String HTTP_SERVER = "192.168.1.146:3000"; // este tiene que ir cambiando

    private static String buildEndPoint(String path) {
        return HTTP_PROTOCOL + "//" + HTTP_SERVER + path;
    }

    //manda elrequest al api para obtener los equipos segun sea su deporte
    public void downloadTeamsBySport(Context context, FutureCallback<JsonArray> arreglo, String sport) {
        Ion.with(context).
                load(buildEndPoint("/api/teams/" + sport)).
                asJsonArray().
                setCallback(arreglo);
    }

    // actualiza un equipo a la base de datos por medio del servicio put
    public void updateTeam(Context context, FutureCallback<JsonArray> arreglo, JsonObject json, String id) {
        Ion.with(context).
                load("PUT", buildEndPoint("/api/teams/id/" + id)).
                setJsonObjectBody(json).
                asJsonArray().
                setCallback(arreglo);
    }
}
