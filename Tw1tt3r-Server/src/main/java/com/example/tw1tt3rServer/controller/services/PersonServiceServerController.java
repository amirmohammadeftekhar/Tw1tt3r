package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Message;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Room;
import dtos.PersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;
import utility.enums.MessageStatus;

@RestController
public class PersonServiceServerController extends AbstractServerController {

    @PostMapping(value = "api/personservice/getperson")
    public PersonDto getPerson(@RequestParam int id){
        Person person = personService.findById(id);
        if(person!=null){
            for(Room room:person.getRooms()){
                for(Message message:room.getMessages()){
                    if(message.getSourcePerson().getId()!=person.getId() && message.getStatus()!=MessageStatus.SEEN){
                        messageService.changeStatus(message, MessageStatus.DELIVERED);
                    }
                }
            }
        }
        return(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
    }

    @PostMapping(value = "api/personservice/updatelastseen")
    public ResponseEntity<Void> updateLastSeen(@RequestParam int id){
        personService.updateLastSeen(id);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
