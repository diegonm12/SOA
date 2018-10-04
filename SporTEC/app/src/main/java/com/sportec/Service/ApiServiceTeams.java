package com.sportec.Service;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ApiServiceTeams {
    private static final String HTTP_PROTOCOL = "http:";    //protocolo http
    private static final String HTTP_SERVER = "192.168.43.141:3000"; // este tiene que ir cambiando

    //construye el endpoint para hacer los  request al servidor
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

    // descarga el equipo segun sea el identificador de la base de datos
    public void downloadTeamById(Context context, FutureCallback<JsonObject> arreglo, String id) {
        Ion.with(context).
                load(buildEndPoint("/api/teams/id/" + id)).
                asJsonObject().
                setCallback(arreglo);
    }
}
