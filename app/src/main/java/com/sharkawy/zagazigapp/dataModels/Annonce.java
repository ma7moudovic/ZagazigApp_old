package com.sharkawy.zagazigapp.dataModels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by T on 4/26/2016.
 */
public class Annonce {
    /*
    "contente": "course as announce ",
 		"announce_id": "13",
 		"expirationDate": "2016-09-02",
 		"place_id": "23",
 		"announcementType": "course",
 		"placeName": "\u0627\u0633\u0645 \u0627\u0644\u0645\u0643\u0627\u0646 \u0627\u0644\u062c\u062f\u064a\u062f ",
 		"icoimage": "places_imgs\/icons\/23.jpg"
    * */

    String content ;
    String placeID;
    String placeName ;
    String iconURL ;
    JSONObject object ;

    public Annonce() {
    }

    public Annonce(String content, String placeID, String placeName, String iconURL) {
        this.content = content;
        this.placeID = placeID;
        this.placeName = placeName;
        this.iconURL = iconURL;
    }

    public Annonce(JSONObject object) {
        this.object = object;
        try {
            this.content = object.getString("content");
            this.placeID= object.getString("place_id");
            this.placeName = object.getString("placeName");
            this.iconURL = object.getString("icoimage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Annonce(String content, String placeID, String placeName, String iconURL, JSONObject object) {
        this.content = content;
        this.placeID = placeID;
        this.placeName = placeName;
        this.iconURL = iconURL;
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public String getPlaceID() {
        return placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getIconURL() {
        return iconURL;
    }

    public JSONObject getObject() {
        return object;
    }
}
