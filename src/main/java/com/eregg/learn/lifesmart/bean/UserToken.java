package com.eregg.learn.lifesmart.bean;

import lombok.Data;

@Data
public class UserToken {

    private String userId;
    private String userToken;
    private Long expiredtime;
    private String svrurl;

}
