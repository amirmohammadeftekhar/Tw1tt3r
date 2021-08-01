package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.service.PersonService;
import dtos.PersonDto;
import dtos.PersonIniDto;
import dtos.StringDto;
import entities.enums.LastSeenType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.BaseResponse;
import web.ResponseHeader;

@RestController

public class EntryServerController extends AbstractServerController {
    @Autowired
    PersonService personService;

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
        ModelMapper modelMapper = new ModelMapper();
//        PersonDto personDto = ModelMapperInstance.getInstance().getModelMapper().map(person, PersonDto.class);
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
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
                getTimeStamp(), personIniDto.isPrivate(), LastSeenType.NOBODY, personIniDto.isToShowEmail());
        return(new BaseResponse(ResponseHeader.OK, new StringDto(personService.login(person.getUserName(), person.getPassword()))));
    }


}












