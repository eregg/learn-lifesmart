package com.eregg.learn.lifesmart.controller;

import com.alibaba.fastjson.JSONObject;
import com.eregg.learn.lifesmart.bean.UserToken;
import com.eregg.learn.lifesmart.config.AppConfig;
import com.eregg.learn.lifesmart.enums.LifeSmartApiEnum;
import com.eregg.learn.lifesmart.proto.request.RequestContent;
import com.eregg.learn.lifesmart.util.DateTimtUtils;
import com.eregg.learn.lifesmart.util.HttpUtils;
import com.eregg.learn.lifesmart.util.SignUtil;
import com.eregg.learn.lifesmart.util.UserTokenUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.TreeMap;

@Controller
@Log4j2
public class LifeSmartController extends BaseController {

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/auth")
    public String auth(){

        TreeMap<String, String> parame = new TreeMap<>();
        parame.put("appkey", appConfig.getAppkey());
        parame.put("time", DateTimtUtils.getCurrentSecond().toString());
        parame.put("auth_callback", "http://localhost:8080/call_back");
        parame.put("did", "1");
        String sign = SignUtil.sign(parame, appConfig.getApptoken());
        parame.put("id", "1");
        parame.put("lang", "zh");
        parame.put("sign", sign);

        String url = HttpUtils.getUrl(LifeSmartApiEnum.AUTHORIZE.getApiUrl(), parame);

        return String.format("redirect:%s", url);
    }

    @GetMapping("/call_back")
    public String callBack(Model model){

        String userid = request.getParameter("userid");
        String usertoken = request.getParameter("usertoken");
        String expiredtime = request.getParameter("expiredtime");
        String svrurl = request.getParameter("svrurl");

        UserToken userToken = new UserToken();
        userToken.setUserId(userid);
        userToken.setUserToken(usertoken);
        userToken.setSvrurl(svrurl);
        userToken.setExpiredtime(Long.parseLong(expiredtime));
        UserTokenUtil.initUserToken(userToken);


        model.addAttribute("userToken", userToken);

        return "/index";
    }

    @GetMapping("/device/list")
    @ResponseBody
    public String deviceList(){


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

        RequestContent requestContent = new RequestContent();
        requestContent.setId(144);
        requestContent.setMethod("EpGetAll");
        requestContent.getSystem().setAppkey(appConfig.getAppkey());
        requestContent.getSystem().setUserid(UserTokenUtil.getUserToken().getUserId());
        requestContent.getSystem().setTime(DateTimtUtils.getCurrentSecond().toString());



        //method:EpGetAll,
        // time:1447395539,
        // userid:1111111,
        // usertoken:USERTOKEN_XXXXXXXX,
        // appkey:APPKEY_XXXXXXXX,
        // apptoken:APPTOKEN_XXXXXXXX

        //appkey=ja9ekycTGDt5GiyozHc68g&
        // method=EpGetAll&
        // time=1555395562&
        // userid=7700934&
        // usertoken=4tpvxoXPoWBkiJsitG20ZA&
        // apptoken=GHWobCwPhBqmdMa6eN9hnA

//        将 params 部分的 param1，param2按升序排序，组成无空格字符串，
//        并在字串前加 method，最后拼接 did、time、userid、usertoken、appkey、apptoken。
//        最后组成的签名原始串如下： method:TestMethod,param1:12345,param2:abcde,
//        did:DID_XXXXXXXX,time:1445307713,userid:1111111,usertoken:abcdefghijklmnopqrstuvwx,
//        appkey:APPKEY_XXXXXXXX,apptoken:ABCDEFGHIKJLMJOBPOOFPDFDA
//
//        签名值即对上述签名原始串计算 MD5 值，即 ：
//        sign= MD5("method:TestMethod,param1:12345,param2:abcde,did:DID_XXXXXXXX,time:1445307713,
//        userid:1111111,usertoken:abcdefghijklmnopqrstuvwx,
//        appkey:APPKEY_XXXXXXXX,apptoken:ABCDEFGHIKJLMJOBPOOFPDFDA”)
//
//        最终 sign 值为：2602efca4b1924fb1a7e62b78f2285b2

        LinkedHashMap<String, String> parame = new LinkedHashMap<>();
        parame.put("time", requestContent.getSystem().getTime());
        parame.put("userid", requestContent.getSystem().getUserid());
        parame.put("usertoken", UserTokenUtil.getUserToken().getUserToken());
        parame.put("appkey", appConfig.getAppkey());
        parame.put("apptoken", appConfig.getApptoken());

        String sign = SignUtil.sign(requestContent.getMethod(), parame);
        requestContent.getSystem().setSign(sign);

        String requestContentJson = JSONObject.toJSONString(requestContent);

        log.info(String.format("requestContentJson:%s", requestContentJson));

        String postJsonResult = HttpUtils.doPostJson(LifeSmartApiEnum.DEVICE_LIST.getApiUrl(), requestContentJson);

        log.info(String.format("postJsonResult:%s", postJsonResult));

        return postJsonResult;
    }

}
