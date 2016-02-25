package com.brotherhood.gramatyka.server.enums;


public enum ServiceType {
    GET_DATABASE,
    CHECK_VERSION;

    public static final String SERVER_PATH = "http://www.datastore.waw.pl/grammar2016/";

    public static String getURL(ServiceType serviceType){
        switch(serviceType){

            case GET_DATABASE:
                return SERVER_PATH + "getDatabase.php";
            case CHECK_VERSION:
                return SERVER_PATH + "checkVersion.php";
        }
        return "Service path is invalid";
    }
}
