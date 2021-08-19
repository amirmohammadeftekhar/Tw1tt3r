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
        String[] split = toSend.split(" ");
        Picture picture = pictureService.findById(pictureId);
        int tillSend = 0;
        if(toSend.length()>4 && toSend.substring(0, 5).equals("/time") && split.length > 1){
            System.out.println("!!!");
            tillSend = Integer.parseInt(split[1]);
            System.out.println(tillSend);
        }
        roomService.sendMessage(toSend, personService.findById(currentPersonId), roomService.findById(roomId), picture, tillSend);
        int secondSpace = toSend.indexOf(" ", 5);
        if(toSend.length()>3 && toSend.substring(0, 4).equals("/bot") && split.length > 2){
            String botValue = split[1];
            if(botManager.getValueToClass().containsKey(botValue)){
                roomService.sendMessage(botManager.getResponse(botValue, toSend.substring(secondSpace+1), roomId, currentPersonId), personService.findPersonByUserName(botValue), roomService.findById(roomId), null, 0);
            }
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
