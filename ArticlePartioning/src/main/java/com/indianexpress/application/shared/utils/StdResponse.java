package com.indianexpress.application.shared.utils;

import java.util.HashMap;
import java.util.Map;

public class StdResponse {

    private String responseKey="detail";
    private String responseValue;

    public StdResponse(String responseKey,String responseValue){
        this.responseKey = responseKey;
        this.responseValue = responseValue;
    }

    public StdResponse(String responseValue){
        this.responseValue = responseValue;
    }

    public String getResponseKey() {
        return responseKey;
    }

    public void setResponseKey(String responseKey) {
        this.responseKey = responseKey;
    }

    public String getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(String responseValue) {
        this.responseValue = responseValue;
    }

    public Map<?,?> getMappedResponse(){
        HashMap<String,String> mappedResponse = new HashMap<String,String>();
        mappedResponse.put(responseKey,responseValue);
        return mappedResponse;
    }
}

