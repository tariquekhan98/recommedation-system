package com.indianexpress.application.shared.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends Exception{

    public AuthenticationFailedException(String message){
        super(message);
    }

    public HttpStatus getHttpStatus(){
        return HttpStatus.UNAUTHORIZED;
    }
}
