package com.sharkawy.zagazigapp.dataModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;

/**
 * Created by T on 4/3/2016.
 */
public class Place {

    private String name ;
    private String Desc;
    private String address ;
    private String telephone ;
    private String tag ;
    private String categoryID;
    private String subCategory ;
    private String imageURL ;
    private JSONObject object ;
    private JSONArray serviceTags ;

    public Place() {
    }

    public Place(JSONObject object) {
        this.object = object;
        try {
            this.name=object.getString("name");
            this.Desc=object.getString("description");
            this.address=object.getString("address");
            this.telephone=object.getString("telephoneNum");
//            this.tag=object.getString("");
            this.categoryID=object.getString("categoryId");
//            this.subCategory=object.getString("");
            this.imageURL=object.getString("icoImage");
            this.serviceTags=object.getJSONArray("serviceTags");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Place(String name, String desc, String address, String telephone, String tag, String categoryID,String subCategory) {
        this.name = name;
        this.Desc = desc;
        this.address = address;
        this.telephone = telephone;
        this.tag = tag;
        this.categoryID=categoryID;
        this.subCategory=subCategory;
    }

    public Place(String name, String desc, String address, String telephone, String tag, String categoryID,String subCategory,JSONObject object) {
        this.name = name;
        this.Desc = desc;
        this.address = address;
        this.telephone = telephone;
        this.tag = tag;
        this.categoryID=categoryID;
        this.subCategory=subCategory;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getTag() {
        return tag;
    }

    public String getImageURL() {
        return imageURL;
    }

    public JSONObject getObject() {
        return object;
    }

    public String getCategoryID() {
        return categoryID;

    }
    public JSONArray getServiceTags() {
        return serviceTags;
    }
    public String getSubCategory() {
        return subCategory;
    }

    public String getDesc() {
        return Desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public void setServiceTags(JSONArray serviceTags) {
        this.serviceTags = serviceTags;
    }
}
