package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
import dtos.PersonDto;
import dtos.PersonIniDto;
import entities.enums.LastSeenType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;
import web.BaseResponse;
import web.ResponseHeader;

@RestController
public class EntryServerController extends AbstractServerController {

    @PostMapping(value = "signin")
    public BaseResponse signin(@RequestParam String userName, @RequestParam String password){
        if (!personService.existsPersonByUserName(userName)) {
            return(new BaseResponse(ResponseHeader.USERNAME_NOT_EXISTS, null));
        }
        Person person = personService.findPersonByUserNameNotCheckingActiveState(userName);
        if (!person.getPassword().equals(password)) {
            return(new BaseResponse(ResponseHeader.WRONG_PASSWORD, null));
        }
        person.setActiveState(true);
        person = personService.save(person);
        personService.login(person.getUserName(), person.getPassword());
        PersonDto personDto = ModelMapperInstance.getModelMapper().map(person, PersonDto.class);
        return(new BaseResponse(ResponseHeader.OK, personDto));
    }

    @PostMapping(value = "signup")
    public BaseResponse signup(@RequestBody PersonIniDto personIniDto){
        if (personService.existsPersonByUserName(personIniDto.getUserName())) {
            return(new BaseResponse(ResponseHeader.USERNAME_NOT_EXISTS, null));
        }

        if (personService.existsPersonByEmailAddress(personIniDto.getEmailAddress())) {
            return(new BaseResponse(ResponseHeader.EMAIL_NOT_EXISTS, null));
        }
        Person person = personService.makePerson(personIniDto.getFirstname(), personIniDto.getLastName(),
                personIniDto.getUserName(), personIniDto.getPassword(), personIniDto.getEmailAddress(), true,
                getTimeStamp(), personIniDto.isPrivate(), LastSeenType.NOBODY, personIniDto.isToShowEmail(), null);
        personService.login(person.getUserName(), person.getPassword());
        PersonDto personDto = ModelMapperInstance.getModelMapper().map(person, PersonDto.class);
        return(new BaseResponse(ResponseHeader.OK, personDto));
    }


}












