package com.sharkawy.zagazigapp.dataModels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by T on 4/22/2016.
 */
public class Tag {
    String Tag ;
    String id ;
    JSONObject jsonObject ;

    public Tag() {
    }

    public Tag(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try {
            this.Tag=jsonObject.getString("description");
            this.id=jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Tag(String tag) {
        Tag = tag;
    }

    public Tag(String id, String tag) {
        this.id = id;
        Tag = tag;
    }

    public String getTag() {
        return Tag ;
    }

    public String getId() {
        return id;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
