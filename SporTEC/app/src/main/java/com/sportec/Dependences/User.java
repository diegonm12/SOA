package com.sportec.Dependences;

import com.google.gson.JsonElement;

public class User {
    private String name; //nombre del usuario
    private String mEmail; // email del usuario
    private String mPassword; // password del usuario
    private String mProfilePicture; // foto de perfil del usuario
    private JsonElement mFavSports; //deportes favs del user

    public JsonElement getFavSports() {
        return mFavSports;
    }

    public void setFavSports(JsonElement favSports) {
        this.mFavSports = favSports;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    private String mType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmProfilePicture() {
        return mProfilePicture;
    }

    public void setmProfilePicture(String mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

}
