package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Action;
import com.example.tw1tt3rServer.repository.entity.Person;
import dtos.ActionDto;
import dtos.PersonDto;
import dtos.ProfileReloadDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;
import web.BaseResponse;
import web.ResponseHeader;

import java.util.LinkedList;
import java.util.List;

@RestController
public class ProfileServerController extends AbstractServerController{
    @GetMapping("api/profile/getblockingpersons")
    public List<PersonDto> getBlockingPersons(@RequestParam int currentPersonId){
        List<Person> people = actionService.getBlockingsPersons(personService.findById(currentPersonId));
        List<PersonDto> newList = new LinkedList<PersonDto>();
        for(Person person:people){
            newList.add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        return(newList);
    }

    @GetMapping("api/profile/getfollowerspersons")
    public List<PersonDto> getFollowersPersons(@RequestParam int currentPersonId){
        List<Person> people = actionService.getFollowersPersons(personService.findById(currentPersonId));
        List<PersonDto> newList = new LinkedList<PersonDto>();
        for(Person person:people){
            newList.add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        return(newList);
    }

    @GetMapping("api/profile/getfollowingspersons")
    public List<PersonDto> getFollowingsPersons(@RequestParam int currentPersonId){
        List<Person> people = actionService.getFollowingsPersons(personService.findById(currentPersonId));
        List<PersonDto> newList = new LinkedList<PersonDto>();
        for(Person person:people){
            newList.add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        return(newList);
    }

    @GetMapping("api/profile/reload")
    public BaseResponse reload(@RequestParam int currentPersonId){
        ProfileReloadDto dto = new ProfileReloadDto();
        Person currentPerson = personService.findById(currentPersonId);
        dto.setBlockingsCount(actionService.getBlockingsPersons(currentPerson).size());
        dto.setFollowersCount(actionService.getFollowersPersons(currentPerson).size());
        dto.setFollowingsCount(actionService.getFollowingsPersons(currentPerson).size());
        dto.setTweetsCount(tweetService.findByPersonWhoMadeThis(currentPerson).size());
        for(Action action:actionService.getFollowers(currentPerson)){
            if(action.isNotified()){
                continue;
            }
            actionService.changeNotified(action, true);
            Person person = action.getSourcePerson();
            dto.getNewFollowers().add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        int t_unfollowers = 0;
        for(Action action:actionService.getUnfollowers(currentPerson)){
            if(action.isNotified()){
                continue;
            }
            actionService.changeNotified(action, true);
            Person person = action.getSourcePerson();
            dto.getNewUnfollowers().add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        int t_rejectors = 0;
        for(Action action:actionService.getRejecters(currentPerson)){
            if(action.isNotified()){
                continue;
            }
            actionService.changeNotified(action, true);
            Person person = action.getSourcePerson();
            dto.getNewRejectors().add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        int t_your_requests = 0;
        for(Action action:actionService.getFollowRequestings(currentPerson)){
            if(action.isNotified()){
                continue;
            }
            actionService.changeNotified(action, true);
            Person person = action.getSourcePerson();
            dto.getYourRequests().add(ModelMapperInstance.getModelMapper().map(person, PersonDto.class));
        }
        int t_others_requests = 0;
        for(Action action:actionService.getFollowRequesters(currentPerson)){
            if(action.isNotified()){
                continue;
            }
            dto.getOtherRequests().add(ModelMapperInstance.getModelMapper().map(action, ActionDto.class));
        }
        return(new BaseResponse(ResponseHeader.OK, dto));
    }

}












