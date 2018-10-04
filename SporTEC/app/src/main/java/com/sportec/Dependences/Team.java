package com.sportec.Dependences;

import com.google.gson.JsonElement;

public class Team {

    private String mName;   //nombre del equipo
    private String mImage;  //imagen del equipo
    private String mType;   // me dice que es de tipo equipo
    private String mSport;  // a que deporte pertenece
    private JsonElement member; //los miembros del equupo
    private String id;  // identificador de la base del equipo

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mNmae) {
        this.mName = mNmae;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmSport() {
        return mSport;
    }

    public void setmSport(String mSport) {
        this.mSport = mSport;
    }

    public JsonElement getMember() {
        return member;
    }

    public void setMemeber(JsonElement member) {
        this.member = member;
    }

}
