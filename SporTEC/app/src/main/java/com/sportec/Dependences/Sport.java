package com.sportec.Dependences;

public class Sport {
    private String mName;   //nombre del deporte
    private String mImage;  //imagen que representa el deporte
    private String mType;   // me dice que es de tipo deporte

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public void setType(String type) {
        this.mType = type;
    }
}
