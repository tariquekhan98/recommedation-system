package com.indianexpress.application.controllers;

import com.indianexpress.application.services.ArticleService;
import com.indianexpress.application.shared.exceptions.NotFoundException;
import com.indianexpress.application.shared.exceptions.ValidationException;
import com.indianexpress.application.shared.utils.StdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    ArticleService articleService;

    @PostMapping("/article-to-word/")
    public ResponseEntity response(@RequestBody HashMap<?,?> requestBody){
        try{
            return new ResponseEntity(articleService.save(requestBody,null), HttpStatus.CREATED);
        }
        catch(ValidationException | IOException e){
            return new ResponseEntity(new StdResponse(e.getMessage()).getMappedResponse(), HttpStatus.BAD_REQUEST);
        }
        catch(NotFoundException e){
            return new ResponseEntity(new StdResponse(e.getMessage()).getMappedResponse(), HttpStatus.NOT_FOUND);
        }
    }
}
