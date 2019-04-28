package com.eregg.learn.lifesmart.enums;

public enum LifeSmartApiEnum {

    AUTHORIZE("authorize", "https://api.ilifesmart.com/app/auth.authorize"),
    DEVICE_LIST("EpGetAll", "https://api.ilifesmart.com/app/api.EpGetAll"),

    END("end", "");


    LifeSmartApiEnum(String apiName, String apiUrl) {
        this.apiName = apiName;
        this.apiUrl = apiUrl;
    }

    private String apiName;
    private String apiUrl;

    public String getApiName() {
        return apiName;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
