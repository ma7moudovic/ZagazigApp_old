package com.sharkawy.zagazigapp.dataModels;

/**
 * Created by T on 4/26/2016.
 */
public class Photo {
    String photoURL ;

    public Photo(String photoURL) {
        this.photoURL = photoURL;
    }

    public Photo() {
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
