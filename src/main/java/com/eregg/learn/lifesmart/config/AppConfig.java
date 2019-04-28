package com.eregg.learn.lifesmart.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "edsip.lifesmart.app")
@Component
public class AppConfig {

    private String appkey;
    private String apptoken;

}
