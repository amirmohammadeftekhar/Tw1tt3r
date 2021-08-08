package com.example.tw1tt3rServer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryMessagingServerController extends AbstractServerController{
    @PostMapping("api/categorymessaging/sendmessage")
    public ResponseEntity<Void> sendMessage(@RequestParam int categoryId, @RequestParam int currentPersonId, @RequestParam String toSend){
        categoryService.sendMessage(categoryService.findById(categoryId), personService.findById(currentPersonId), toSend);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
