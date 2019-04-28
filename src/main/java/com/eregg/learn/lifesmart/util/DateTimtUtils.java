package com.eregg.learn.lifesmart.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateTimtUtils {

    public static Long getCurrentSecond(){
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Long getCurrentMilliSecond(){
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


}
