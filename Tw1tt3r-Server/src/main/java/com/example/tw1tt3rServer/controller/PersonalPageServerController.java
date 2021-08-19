package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
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
public class PersonalPageServerController extends AbstractServerController{
    @PostMapping("api/personalpage/blockbuttonaction")
    public ResponseEntity<Void> blockButtonAction(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        if(actionService.isBlocking(currentPerson, person)){
            actionService.deleteBlock(currentPerson, person);
        }
        else{
            actionService.makeBlock(currentPerson, person);
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @GetMapping("api/personalpage/getfollowstatus")
    public BaseResponse getFollowStatus(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        if(actionService.isFollowRequesting(currentPerson, person)){
            return(new BaseResponse(ResponseHeader.REQUESTED_STATUS, null));
        }
        if(actionService.isFollowing(currentPerson, person)){
            return(new BaseResponse(ResponseHeader.FOLLOWING_STATUS, null));
        }
        if(!actionService.isFollowing(currentPerson, person) && !actionService.isFollowRequesting(currentPerson, person)){
            return(new BaseResponse(ResponseHeader.FOLLOW_STATUS, null));
        }
        return(null);
    }

    @PostMapping("api/personalpage/followbuttonaction")
    public ResponseEntity<Void> followButtonAction(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        if(!actionService.isFollowing(currentPerson, person) && !actionService.isFollowRequesting(currentPerson, person)){
            actionService.makeFollowRequest(currentPerson, person);
        }
        else if(actionService.isFollowRequesting(currentPerson, person)){
            actionService.deleteFollowRequest(currentPerson, person);
        }
        else if(actionService.isFollowing(currentPerson, person)){
            actionService.makeUnfollow(currentPerson, person);
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/personalpage/mutebuttonaction")
    public ResponseEntity<Void> muteButtonAction(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        if(actionService.isSourceMuting(currentPerson, person)){
            actionService.deleteMute(currentPerson, person);
        }
        else{
            actionService.makeMute(currentPerson, person);
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @GetMapping("api/personalpage/messagebuttonaction")
    public BaseResponse messageButtonAction(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        Room room;
        if(roomService.existsPv(currentPerson, person)){
            room = roomService.findPv(currentPerson, person);
            RoomDto roomDto = ModelMapperInstance.getModelMapper().map(room, RoomDto.class);
            DtoUtility.makeRoomHealthy(roomDto);
            return(new BaseResponse(ResponseHeader.ROOM_EXISTS, roomDto));
        }
        else{
            room = roomService.makePv(currentPerson, person);
            RoomDto roomDto = ModelMapperInstance.getModelMapper().map(room, RoomDto.class);
            DtoUtility.makeRoomHealthy(roomDto);
            return(new BaseResponse(ResponseHeader.ROOM_NOT_EXISTS, roomDto));
        }
    }
}










