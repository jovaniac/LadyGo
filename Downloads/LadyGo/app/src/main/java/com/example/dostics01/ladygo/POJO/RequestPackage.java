package com.example.dostics01.ladygo.POJO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dostics01 on 24/01/2017.
 */

public class RequestPackage {
    //Propiedades
    private String uri;
    private String method= "POST";
    private Map<String, String> params = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    //Trabajar con un numero N de parametros
    public void setParam( String key, String value){
        params.put(key, value);
    }

    public String getEncodeParams(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: params.keySet()){
            String value = null;
            try {
                value= URLEncoder.encode(params.get(key),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(stringBuilder.length() >0){
                stringBuilder.append("&");
            }
            stringBuilder.append(key+"="+value);
        }
        return stringBuilder.toString();
    }
}
