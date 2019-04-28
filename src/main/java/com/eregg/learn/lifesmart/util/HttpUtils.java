package com.eregg.learn.lifesmart.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static String getUrl(String url, Map<String, String> parameter){

        try {
            URIBuilder builder = new URIBuilder(url);
            for (String key : parameter.keySet()) {
                builder.addParameter(key, parameter.get(key));
            }
            URI build = builder.build();

            return build.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String doGet(String url){
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, String> parameter){
        //1. 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
                String result = "";
        try {
            //2. 创建url对象
            URIBuilder builder = new URIBuilder(url);
            if (parameter != null) {
                for (String key : parameter.keySet()) {
                    builder.addParameter(key, parameter.get(key));
                }
            }
            URI uri = builder.build();
            System.out.println(String.format("url:[%s]", uri.toString()));
            //3. 创建Get对象
            HttpGet httpGet = new HttpGet(uri);
            //4. 执行请求
            httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //5. 返回响应数据
        return result;
    }

    public static String doPost(String url){
        return doPost(url, null);
    }

    public static String doPost(String url, Map<String, String> parameter){
        //1. 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            //2. 创建url对象
            URI uri = new URI(url);
            //3. 创建Post对象
            HttpPost httpPost = new HttpPost(uri);
            if(parameter != null){
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : parameter.keySet()) {
                    paramList.add(new BasicNameValuePair(key, parameter.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            //4. 执行请求
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //5. 返回响应数据
        return result;
    }

    public static String doPostJson(String url, String json){
        //1. 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            //2. 创建url对象
            URI uri = new URI(url);
            //3. 创建Post对象
            HttpPost httpPost = new HttpPost(uri);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            //4. 执行请求
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //5. 返回响应数据

        return result;
    }
}
