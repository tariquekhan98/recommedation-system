package com.indianexpress.application.shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Base64Utility {

    private static final Logger logger = LoggerFactory.getLogger(Base64Utility.class);

    public static String encode(String s){
        byte[] bytes;

        try {
            bytes = s.getBytes("UTF-8");
        }
        catch (Exception ex){
            logger.error("Unable to extract bytes from string,{}",ex);
            return "";
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decode(String s){
        return new String(Base64.getDecoder().decode(s));
    }
}
