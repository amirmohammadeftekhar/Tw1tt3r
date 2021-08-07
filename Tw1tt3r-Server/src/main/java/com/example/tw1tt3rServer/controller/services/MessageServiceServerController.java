package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Message;
import dtos.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;

@RestController
public class MessageServiceServerController extends AbstractServerController {
    @PostMapping("api/messageservice/savemessage")
    public ResponseEntity<Void> saveMessage(@RequestBody MessageDto message){
        messageService.save(ModelMapperInstance.getModelMapper().map(message, Message.class));
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
