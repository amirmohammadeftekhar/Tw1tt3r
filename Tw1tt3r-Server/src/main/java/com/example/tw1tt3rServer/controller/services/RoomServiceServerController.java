package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Room;
import dtos.DtoUtility;
import dtos.RoomDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;

@RestController
public class RoomServiceServerController extends AbstractServerController {
    @GetMapping("api/roomservice/getroom")
    public RoomDto getRoom(@RequestParam int roomId){
        Room room = roomService.findById(roomId);
        RoomDto roomDto = ModelMapperInstance.getModelMapper().map(room, RoomDto.class);
        DtoUtility.makeRoomHealthy(roomDto);
        return(roomDto);
    }

}
