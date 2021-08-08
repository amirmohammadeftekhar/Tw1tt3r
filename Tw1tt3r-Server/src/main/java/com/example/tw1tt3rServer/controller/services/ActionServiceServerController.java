package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Action;
import com.example.tw1tt3rServer.repository.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "api/actionservice/isblocking")
    public Boolean isBLocking(@RequestParam int currentPersonId, @RequestParam int personId){
        return(actionService.isBlocking(personService.findById(currentPersonId), personService.findById(personId)));
    }

    @GetMapping(value = "api/actionservice/isfollowing")
    public Boolean isFollowing(@RequestParam int currentPersonId, @RequestParam int personId){
        return(actionService.isFollowing(personService.findById(currentPersonId), personService.findById(personId)));
    }

    @GetMapping(value = "api/actionservice/issourcemuting")
    public Boolean isSourceMuting(@RequestParam int source, @RequestParam int destination){
        return(actionService.isSourceMuting(personService.findById(source), personService.findById(destination)));
    }

    @GetMapping(value = "api/actionservice/getfollowerspersonscount")
    public int getFollowersPersonsCount(@RequestParam int personId){
        return(actionService.getFollowersPersons(personService.findById(personId)).size());
    }

    @GetMapping(value = "api/actionservice/getfollowingspersonscount")
    public int getFollowingsPersonsCount(@RequestParam int personId){
        return(actionService.getFollowingsPersons(personService.findById(personId)).size());
    }

    @PostMapping("api/actionservice/makefollow")
    public ResponseEntity<Void> makeFollow(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        actionService.makeFollow(currentPerson, person);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/actionservice/makereject")
    public ResponseEntity<Void> makeReject(@RequestParam int currentPersonId, @RequestParam int personId){
        Person currentPerson = personService.findById(currentPersonId);
        Person person = personService.findById(personId);
        actionService.makeReject(currentPerson, person);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/actionservice/deleteaction")
    public ResponseEntity<Void> deleteAction(@RequestParam int actionId){
        Action action = actionService.findById(actionId);
        actionService.deleteAction(action);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}













