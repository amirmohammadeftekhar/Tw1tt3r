package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import dtos.PictureDto;
import dtos.RoomDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utility.ModelMapperInstance;

@RestController
public class RoomServiceServerController extends AbstractServerController {
    @GetMapping("api/roomservice/getroom")
    public RoomDto getRoom(@RequestParam int roomId){
        Room room = roomService.findById(roomId);
        return(ModelMapperInstance.getModelMapper().map(room, RoomDto.class));
    }

    @PostMapping("api/roomservice/sendmessage")
    public ResponseEntity<Void> sendMessage(@RequestParam int roomId, @RequestParam int currentPersonId, @RequestParam String text, @RequestBody PictureDto pictureDto){
        Person currentPerson = personService.findById(currentPersonId);
        Room room = roomService.findById(roomId);
        Picture picture = pictureService.makePicture(pictureDto);
        roomService.sendMessage(text, currentPerson, room, picture);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

}
