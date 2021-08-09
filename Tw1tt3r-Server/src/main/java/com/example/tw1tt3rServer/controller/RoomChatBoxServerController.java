package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Picture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomChatBoxServerController extends AbstractServerController{
    @PostMapping("api/roomchatbox/sendbuttonaction")
    public ResponseEntity<Void> sendButtonAction(@RequestParam String toSend, @RequestParam int currentPersonId, @RequestParam int roomId, @RequestParam int pictureId){
        Picture picture = pictureService.findById(pictureId);
        roomService.sendMessage(toSend, personService.findById(currentPersonId), roomService.findById(roomId), picture);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
