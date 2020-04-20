package com.icbc.itsp.generaltest.tools;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpUtil {
    private String url = "";
    public static HttpUtil getInstance(){
        return new HttpUtil();
    }

    public HttpUtil setUrl(String url){
        this.url = url;
        return this;
    }

    public JSONObject httpGet(Map<String, Object> param){
        HttpGet httpGet = new HttpGet(url);
        for(Map.Entry<String, Object> entry : param.entrySet()){
            httpGet.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return doHttp(httpGet);
    }

    public JSONObject httpPost(StringEntity entity){
        HttpPost httpPost = new HttpPost(url);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return doHttp(httpPost);
    }

    private JSONObject doHttp(HttpRequestBase httpRequestBase){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = StringUtils.EMPTY;
        try{
            RequestConfig config = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpRequestBase.setConfig(config);
            response = httpClient.execute(httpRequestBase);
            result = EntityUtils.toString(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(null != response){
                    response.close();
                }
                if(null != httpClient){
                    httpClient.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return JSONObject.parseObject(result);
    }


}
