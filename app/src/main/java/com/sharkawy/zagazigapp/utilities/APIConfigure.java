package com.sharkawy.zagazigapp.utilities;

/**
 * Created by T on 5/8/2016.
 */
public class APIConfigure {
    public static final String API_SEARCH_PATH="action=search&";
    public static final String API_SECTOR_PATH="action=announce&type=";
    public static final String API_CONFIG="action=config";
    String PIC_URL_COMPLETE="";
    String PIC_URL_THUMB="";
    public static final String API_DOMAIN="http://176.32.230.22/mashaly.net/handler.php?";
    public String getAPI_SEARCH_PATH() {
        return API_SEARCH_PATH;
    }

    public String getAPI_SECTOR_PATH() {
        return API_SECTOR_PATH;
    }

    public String getPIC_URL_COMPLETE() {
        return PIC_URL_COMPLETE;
    }

    public String getPIC_URL_THUMB() {
        return PIC_URL_THUMB;
    }
}
