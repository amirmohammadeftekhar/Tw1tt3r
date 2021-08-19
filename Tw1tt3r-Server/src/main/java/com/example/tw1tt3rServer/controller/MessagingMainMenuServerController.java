package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Message;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.enums.MessageStatus;

@RestController
public class MessagingMainMenuServerController extends AbstractServerController {
    @PostMapping("api/messagingmainmenu/openchatbox")
    public ResponseEntity<Void> openChatBox(@RequestParam int currentPersonId, @RequestParam int roomId){
        Person currentPerson = personService.findById(currentPersonId);
        Room room = roomService.findById(roomId);
        for(Message message:room.getMessages()){
            if(message.getSourcePerson().getId()!=currentPersonId){
                messageService.changeStatus(message, MessageStatus.SEEN);
            }
            messageService.addWhoRead(currentPerson, message);
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/messagingmainmenu/deleteroom")
    public ResponseEntity<Void> deleteRoom(@RequestParam int currentPersonId, @RequestParam int roomId){
        Person currentPerson = personService.findById(currentPersonId);
        Room room = roomService.findById(roomId);
        roomService.deletePerson(currentPerson, room);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
