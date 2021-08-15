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
        int secondSpace = toSend.indexOf(" ", 5);
        if(toSend.length()>3 && toSend.substring(0, 4).equals("/bot")){
            String[] split = toSend.split(" ");
            if(split.length<3) return(new ResponseEntity<Void>(HttpStatus.OK));
            String botValue = split[1];
            if(botManager.getValueToClass().containsKey(botValue)){
                roomService.sendMessage(botManager.getResponse(botValue, toSend.substring(secondSpace+1), roomId, currentPersonId), personService.findPersonByUserName(botValue), roomService.findById(roomId), null);
            }
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
