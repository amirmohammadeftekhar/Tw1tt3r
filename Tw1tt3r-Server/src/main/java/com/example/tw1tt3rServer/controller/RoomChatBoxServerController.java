package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import dtos.DtoUtility;
import dtos.RoomDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;
import web.BaseResponse;
import web.ResponseHeader;

@RestController
public class RoomChatBoxServerController extends AbstractServerController{
    @PostMapping("api/roomchatbox/sendbuttonaction")
    public ResponseEntity<Void> sendButtonAction(@RequestParam String toSend, @RequestParam int currentPersonId, @RequestParam int roomId, @RequestParam int pictureId){
        String[] split = toSend.split(" ");
        Picture picture = pictureService.findById(pictureId);
        int tillSend = 0;
        if(toSend.length()>4 && toSend.substring(0, 5).equals("/time") && split.length > 1){
            tillSend = Integer.parseInt(split[1]);
            System.out.println(tillSend);
        }
        roomService.sendMessage(toSend, personService.findById(currentPersonId), roomService.findById(roomId), picture, tillSend);
        int secondSpace = toSend.indexOf(" ", 5);
        if(toSend.length()>3 && toSend.substring(0, 4).equals("/bot") && split.length > 2){
            String botValue = split[1];
            if(botManager.getValueToClass().containsKey(botValue)){
                String response = botManager.getResponse(botValue, toSend.substring(secondSpace+1), roomId, currentPersonId);
                if(response!=null){
                    roomService.sendMessage(response, personService.findPersonByUserName(botValue), roomService.findById(roomId), null, 0);
                }
            }
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @GetMapping("api/roomchatbox/roompvaction")
    public BaseResponse roomPvAction(@RequestParam int currentPersonId, @RequestParam String personUserName){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findPersonByUserName(personUserName);
        if(person == null){
            return(new BaseResponse(ResponseHeader.NOT_ALLOWED, null));
        }
        Room room;
        if(roomService.existsPv(currentPerson, person)){
            room = roomService.findPv(currentPerson, person);
            RoomDto roomDto = ModelMapperInstance.getModelMapper().map(room, RoomDto.class);
            DtoUtility.makeRoomHealthy(roomDto);
            return(new BaseResponse(ResponseHeader.ROOM_EXISTS, roomDto));
        }
        else if(person.getBotValue()!=null) {
            room = roomService.makePv(currentPerson, person);
            RoomDto roomDto = ModelMapperInstance.getModelMapper().map(room, RoomDto.class);
            DtoUtility.makeRoomHealthy(roomDto);
            return(new BaseResponse(ResponseHeader.ROOM_NOT_EXISTS, roomDto));
        }
        else{
            return(new BaseResponse(ResponseHeader.NOT_ALLOWED, null));
        }
    }

    @GetMapping("api/roomchatbox/roomgroupaction")
    public BaseResponse roomGroupAction(@RequestParam int currentPersonId, @RequestParam String groupName){
        Person currentPerson = personService.findById(currentPersonId);
        Room room = roomService.findByName(groupName);
        if(room == null){
            return(new BaseResponse(ResponseHeader.NOT_ALLOWED, null));
        }
        if(!room.getMembers().contains(currentPerson)){
            roomService.addPerson(currentPerson, room);
        }
        RoomDto roomDto = ModelMapperInstance.getModelMapper().map(room, RoomDto.class);
        DtoUtility.makeRoomHealthy(roomDto);
        return(new BaseResponse(ResponseHeader.OK, roomDto));
    }
}
