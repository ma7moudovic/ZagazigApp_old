package com.sharkawy.zagazigapp.dataModels;

import org.json.JSONObject;

/**
 * Created by T on 4/3/2016.
 */
public class Place {
    String name ;
    String Desc;
    String address ;
    String telephone ;
    String tag ;
    String categoryID;
    String subCategory ;



    JSONObject object ;
    public Place() {
    }

    public Place(JSONObject object) {
        this.object = object;
    }

    public Place(String name, String desc, String address, String telephone, String tag, String categoryID,String subCategory) {
        this.name = name;
        Desc = desc;
        this.address = address;
        this.telephone = telephone;
        this.tag = tag;
        this.categoryID=categoryID;
        this.subCategory=subCategory;
    }

    public Place(String name, String desc, String address, String telephone, String tag, String categoryID,String subCategory,JSONObject object) {
        this.name = name;
        Desc = desc;
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

    public String getDesc() {
        return Desc;
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

    public JSONObject getObject() {
        return object;
    }
    public String getCategoryID() {
        return categoryID;
    }

    public String getSubCategory() {
        return subCategory;
    }
}
