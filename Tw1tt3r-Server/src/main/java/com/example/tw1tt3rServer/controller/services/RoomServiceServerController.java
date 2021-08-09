package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import dtos.RoomDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;

@RestController
public class RoomServiceServerController extends AbstractServerController {
    @GetMapping("api/roomservice/getroom")
    public RoomDto getRoom(@RequestParam int roomId){
        Room room = roomService.findById(roomId);
        return(ModelMapperInstance.getModelMapper().map(room, RoomDto.class));
    }

    @PostMapping("api/roomservice/sendmessage")
    public ResponseEntity<Void> sendMessage(@RequestParam int roomId, @RequestParam int currentPersonId, @RequestParam String text, @RequestParam int pictureId){
        Person currentPerson = personService.findById(currentPersonId);
        Room room = roomService.findById(roomId);
        Picture picture = null;
        if(pictureId>0){
            picture = pictureService.findById(pictureId);
        }
        roomService.sendMessage(text, currentPerson, room, picture);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

}
