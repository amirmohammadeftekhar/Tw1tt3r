package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionServiceServerController extends AbstractServerController {
    @PostMapping(value = "api/actionservice/block")
    public ResponseEntity<Void> block(@RequestParam int currentPersonId, @RequestParam int personId){
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
}
