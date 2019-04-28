package com.eregg.learn.lifesmart;


import com.eregg.learn.lifesmart.config.AppConfig;
import com.eregg.learn.lifesmart.util.DateTimtUtils;
import com.eregg.learn.lifesmart.util.HttpUtils;
import com.eregg.learn.lifesmart.util.SignUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorizeTest {

    @Autowired
    AppConfig appConfig;


    @Test
    public void authorize(){
        String url = "https://api.ilifesmart.com/app/auth.authorize";//?id=***&appkey=***&time=***&auth_callback=***&did=***&sign=***&lang=zh
        TreeMap<String, String> parame = new TreeMap<>();
        parame.put("appkey", appConfig.getAppkey());
        parame.put("time", DateTimtUtils.getCurrentSecond().toString());
        parame.put("auth_callback", "http://localhost:8080/CallBack.ashx");
        parame.put("did", "1");
        String sign = SignUtil.sign(parame, appConfig.getApptoken());
        parame.put("id", "1");
        parame.put("lang", "zh");
        parame.put("sign", sign);

        String result = HttpUtils.doGet(url, parame);

        System.out.println(result);

    }
}
