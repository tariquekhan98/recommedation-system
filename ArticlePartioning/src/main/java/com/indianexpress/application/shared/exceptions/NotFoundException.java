package com.indianexpress.application.shared.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Long postId) {
    }

    public HttpStatus getHttpStatus(){
        return HttpStatus.NOT_FOUND;
    }
}
