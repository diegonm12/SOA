package com.sportec.Dependences;

import com.google.gson.JsonElement;

public class Team {

    private String mName;
    private String mImage;
    private String mType;
    private String mSport;
    private JsonElement member;
    private String id;

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
