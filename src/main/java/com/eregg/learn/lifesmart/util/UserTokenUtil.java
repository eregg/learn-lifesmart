package com.eregg.learn.lifesmart.util;

import com.eregg.learn.lifesmart.bean.UserToken;

public class UserTokenUtil {

    private static UserToken userToken;

    public static void initUserToken(UserToken userToken){
        UserTokenUtil.userToken = userToken;
    }

    public static UserToken getUserToken(){
        return UserTokenUtil.userToken;
    }
}
