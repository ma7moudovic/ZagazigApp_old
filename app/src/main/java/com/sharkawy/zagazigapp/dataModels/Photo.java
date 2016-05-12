package com.sharkawy.zagazigapp.dataModels;

/**
 * Created by T on 4/26/2016.
 */
public class Photo {
    String photoURL ;
    String photoThumb ;

    public String getPhotoThumb() {
        return photoThumb;
    }

    public Photo(String photoURL , String photoThumb) {
        this.photoURL = photoURL;
        this.photoThumb = photoThumb ;

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
