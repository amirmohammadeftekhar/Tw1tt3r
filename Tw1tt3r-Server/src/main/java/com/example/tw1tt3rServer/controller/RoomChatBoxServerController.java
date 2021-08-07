package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Picture;
import dtos.PictureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;

@RestController
public class RoomChatBoxServerController extends AbstractServerController{
    @PostMapping("api/roomchatbox/sendbuttonaction")
    public ResponseEntity<Void> sendButtonAction(@RequestParam String toSend, @RequestParam int currentPersonId, @RequestParam int roomId, @RequestBody PictureDto pictureToSend){
        roomService.sendMessage(toSend, personService.findById(currentPersonId), roomService.findById(roomId),
                ModelMapperInstance.getModelMapper().map(pictureToSend, Picture.class));
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
