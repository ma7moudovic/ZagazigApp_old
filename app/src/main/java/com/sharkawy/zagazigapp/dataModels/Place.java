package com.sharkawy.zagazigapp.dataModels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by T on 4/3/2016.
 */
public class Place {
    /**
     * "Id": "25",
     "name": "cat 1,2 sub 1 tag 2 ,4",
     "description": "cat 1 sub 1 tag 2 ",
     "address": "cat 1 sub 1 tag 2 ??????? ???????",
     "areaCode": "1",
     "telephoneNum": "2323",
     "categoryId": "1",
     "subcategory": ["1", "3"],
     "ownerId": null,
     "serviceTags": ["1", "2", "3", "4"],
     "icoImage": "places_imgs\/icons\/0.jpg",
     "imagesPathes": [],
     "offers": []
     * **/
    String name ;
    String Desc;
    String address ;
    String telephone ;
    String tag ;
    String categoryID;
    String subCategory ;
    String imageURL ;
    JSONObject object ;

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
            this.subCategory=object.getString("");
            this.imageURL=object.getString("icoImage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String getImageURL() {
        return imageURL;
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
