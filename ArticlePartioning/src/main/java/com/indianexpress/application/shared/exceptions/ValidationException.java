package com.indianexpress.application.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception{

    public ValidationException(String message){
        super(message);
    }

    public HttpStatus getHttpStatus(){
        return HttpStatus.BAD_REQUEST;
    }
}
