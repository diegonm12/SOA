package com.sportec.Dependences;

import java.net.URL;

public class User {
    String name;
    String mEmail;
    String mPassword;
    URL mProfilePicture;

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

    public URL getmProfilePicture() {
        return mProfilePicture;
    }

    public void setmProfilePicture(URL mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

}
