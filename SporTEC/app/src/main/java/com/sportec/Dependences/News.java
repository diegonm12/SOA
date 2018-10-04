package com.sportec.Dependences;

public class News {
    private String mSportName; //deporte de la noticia
    private String mTitle;  //titulo de la noticia
    private String mContent;    //contenido de la noticia
    private String mImportant;  // si es del dia o no
    private String mImage;  //imagen de la noticia
    private String mType;   // me dice que es de tipo noticia

    public String getSportName() {
        return mSportName;
    }

    public void setSportName(String mSportName) {
        this.mSportName = mSportName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getImportant() {
        return mImportant;
    }

    public void setImportant(String mImportant) {
        this.mImportant = mImportant;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public void setType(String type) {
        this.mType = type;
    }
}
