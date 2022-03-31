package com.indianexpress.application.shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpConnectionUtility {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionUtility.class);

    public HttpURLConnection connect(String urlString, String requestMethod, Map<String, String> headers,String params) throws IOException {
        /*
        Utility to fetch response from a URL.
        Generic architecture respects url,headers,data and request method.
        Currently works only for GET and POST.
         */

        if (requestMethod.equalsIgnoreCase("GET")){
            urlString = urlString.concat("?"+params);
        }
        URL url = new URL(urlString);

        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod(requestMethod);
        if (headers != null){
            headers.forEach((header,value) -> httpUrlConnection.setRequestProperty(header,value));
        }

        if (requestMethod.equalsIgnoreCase("POST")){
            byte[] postData = params.getBytes(StandardCharsets.UTF_8);
            logger.info("POST request,{},{},{}",urlString,params,postData.length);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.getOutputStream().write(postData,0,postData.length);
        }
        return httpUrlConnection;
    }
}
