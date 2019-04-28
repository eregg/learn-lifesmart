package com.eregg.learn.lifesmart.proto.request;

import lombok.Data;

import java.util.Map;

@Data
public class RequestContent {

    @Data
    public class System{
        private String ver = "1.0";//Y	1.0
        private String lang = "en";//Y	en
        private String sign;//Y	签名值
        private String userid;//	Y	User ID
        private String appkey;//	Y	appkey
        //private String did;//	O	(可选)终端唯一 id。如果在授权时填入了，此处必须填入相同 id
        private String time;//	Y	UTC 时间戳，自1970年1月1日起计算的时间，单位为秒
    }

    //        var request = {
//                    "id":144,
//                    "method": "EpGetAll",
//                    "system": {
//                        "ver": "1.0",
//                        "lang": "en",
//                        "userid": "1111111",
//                        "appkey": "APPKEY_XXXXXXXX",
//                        "time": 1447396020,
//                        "sign":"SIGN_XXXXXXXX",
//            }
//         }

    private Integer id;
    private String method;
    private Map<String, String> params;
    private System system = new System();


}
