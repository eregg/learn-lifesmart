package com.eregg.learn.lifesmart.util;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {


    /**
     * sign:签名，⽣生成算法如下:
     * a) 签名原始串串:appkey=***&auth_callback=***&did=***&time=***&apptoken=***(签名原始字符串除 apptoken外其他字段按照字母,顺序排序生成，如果有did，则进行签名，否则不填入)
     * b) 将"签名原始串"进行MD5编码，并转化为32位的16进制⼩写字符串，作为签名值sign。
     * c) 注意:lang不放入签名原始串，即不不需要签名。
     * d) MD5算法必须正确，可用下面字符串进行对⽐验证:
     * 签名原始串:
     * appkey=1111111111&auth_callback=http://localhost:8080/CallBack.ashx&time=1445307713&apptoken=ABCDEFGHIKJLMJOBPOOFPDFDA
     * sign为 :
     * 0972888fac34d1d151e4433c9dc7a102
     * @return
     */
    public static String sign(TreeMap<String, String> treeMap, String apptoken){

        StringBuilder sb = new StringBuilder();

        for (String key : treeMap.keySet()) {
            sb.append(key).append('=').append(treeMap.get(key)).append('&');
        }

        sb.append("apptoken").append('=').append(apptoken);

        String originalSign = sb.toString();

        System.out.println(originalSign);

        return MD5Util.md5(originalSign);
    }

    /**
     将 params 部分的 param1，param2按升序排序，组成无空格字符串，
     并在字串前加 method，最后拼接 did、time、userid、usertoken、appkey、apptoken。
     最后组成的签名原始串如下： method:TestMethod,param1:12345,param2:abcde,
     did:DID_XXXXXXXX,time:1445307713,userid:1111111,usertoken:abcdefghijklmnopqrstuvwx,
     appkey:APPKEY_XXXXXXXX,apptoken:ABCDEFGHIKJLMJOBPOOFPDFDA
     签名值即对上述签名原始串计算 MD5 值，即 ：
     sign= MD5("method:TestMethod,param1:12345,param2:abcde,did:DID_XXXXXXXX,time:1445307713,
     userid:1111111,usertoken:abcdefghijklmnopqrstuvwx,
     appkey:APPKEY_XXXXXXXX,apptoken:ABCDEFGHIKJLMJOBPOOFPDFDA”)
     最终 sign 值为：2602efca4b1924fb1a7e62b78f2285b2
     */
    public static String sign(String method, TreeMap<String, String> treeMap, LinkedHashMap<String, String> linkedHashMap){

        StringBuilder sb = new StringBuilder();
        sb.append("method").append(':').append(method).append(',');

        if(treeMap != null){
            for (String key : treeMap.keySet()) {
                sb.append(key).append(':').append(treeMap.get(key)).append(',');
            }
        }

        for (String key : linkedHashMap.keySet()) {
            sb.append(key).append(':').append(linkedHashMap.get(key)).append(',');
        }

        sb = sb.deleteCharAt(sb.length() - 1);

        String originalSign = sb.toString();

        System.out.println(originalSign);

        return MD5Util.md5(originalSign);
    }

    public static String sign(String method, LinkedHashMap<String, String> linkedHashMap){

        return sign(method, null, linkedHashMap);
    }



    public static void main(String[] args){
//        String content = "appkey=1111111111&auth_callback=http://localhost:8080/CallBack.ashx&time=1445307713&apptoken=ABCDEFGHIKJLMJOBPOOFPDFDA";
//        String md5 = MD5Util.md5(content);
//        System.out.println(md5);
        TreeMap<String, String> parame = new TreeMap<>();
        parame.put("appkey", "");
        parame.put("auth_callback", "http://localhost:8080/CallBack.ashx");
        parame.put("time", "1445307713");

        String sign = sign(parame, "");

        System.out.println(sign);

    }
}
