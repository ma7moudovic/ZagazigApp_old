package com.sharkawy.zagazigapp.dataModels;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by T on 5/17/2016.
 */
public class Image implements Serializable {
    private String small,large;
    private String DESC;
    String ID ;
    JSONObject jsonObject ;
    public Image() {
    }

    public Image(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try {
            this.small = jsonObject.getString("thumb");
            this.large = jsonObject.getString("path");
            this.ID = jsonObject.getString("ID");
            this.DESC = jsonObject.getString("desc");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public Image(String small, String large, String DESC) {
        this.small = small;
        this.large = large;
        this.DESC = DESC;
    }


    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getDESC() {
        return DESC;
    }

    public String getID() {
        return ID;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
