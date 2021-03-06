package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Category;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import dtos.PersonIniDto;
import dtos.PictureDto;
import dtos.SettingEntityDto;
import entities.enums.RoomType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class SettingServerController extends AbstractServerController {

    @PostMapping("api/setting/privacyupdate")
    public ResponseEntity<Void> privacyUpdate(@RequestBody SettingEntityDto settingEntityDto){
        System.out.println("!!! 1");
        Person person = personService.findById(settingEntityDto.getPersonId());
        if(settingEntityDto.isToDeactivate()){
            personService.changeActiveState(person, false);
            return(new ResponseEntity<Void>(HttpStatus.OK));
        }
        if(settingEntityDto.isToDelete()){
            System.out.println("!!! 2");
            personService.deleteAccount(person);
            return(new ResponseEntity<Void>(HttpStatus.OK));
        }
        person.setLastSeenType(settingEntityDto.getLastSeenType());
        person.setPrivate(settingEntityDto.isPrivate());
        if(settingEntityDto.getPassword()!=null && !settingEntityDto.getPassword().equals("")){
            person.setPassword(settingEntityDto.getPassword());
        }
        personService.save(person);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/categorycreatebuttonaction")
    public ResponseEntity<Void> categoryCreateButtonAction(@RequestParam String name, @RequestParam int currentPersonId, @RequestBody List<Integer> peopleToAdd){
        Person currentPerson = personService.findById(currentPersonId);
        Category category = categoryService.makeCategory(name, currentPerson);
        for(int personId:peopleToAdd){
            Person person = personService.findById(personId);
            category = categoryService.addPerson(person, category);
            if(!roomService.existsPv(currentPerson, person)){
                roomService.makePv(currentPerson, person);
            }
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/roomcreatebuttonaction")
    public ResponseEntity<Void> roomCreateButtonAction(@RequestParam String name, @RequestParam int currentPersonId, @RequestParam boolean isPv, @RequestBody List<Integer> peopleToAdd){
        Person currentPerson = personService.findById(currentPersonId);
        List<Person> selectedPeopleFromRoomCreating = new LinkedList<Person>();
        for(int personId:peopleToAdd) selectedPeopleFromRoomCreating.add(personService.findById(personId));
        if(isPv && peopleToAdd.size()!=1){
            return(new ResponseEntity<Void>(HttpStatus.OK));
        }
        if(isPv && roomService.existsPv(currentPerson, (new LinkedList<Person>(selectedPeopleFromRoomCreating)).get(0))){
            return(new ResponseEntity<Void>(HttpStatus.OK));
        }
        if(isPv){
            Room room = roomService.makePv(currentPerson, (new LinkedList<Person>(selectedPeopleFromRoomCreating).get(0)));
            return(new ResponseEntity<Void>(HttpStatus.OK));
        }
        Room room = roomService.makeRoom(name, isPv?RoomType.PRIVATE:RoomType.GROUP);
        for(Person person:selectedPeopleFromRoomCreating){
            room = roomService.addPerson(person, room);
        }
        room = roomService.addPerson(currentPerson, room);
        room = roomService.save(room);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/changepicture")
    public ResponseEntity<Void> changePicture(@RequestParam int currentPersonId, @RequestBody PictureDto picture){
        Person currentPerson = personService.findById(currentPersonId);
        Picture pic = pictureService.findById(picture.getId());
        currentPerson = personService.changePicture(currentPerson, pic);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/updateprofilebuttonaction")
    public ResponseEntity<Void> updateProfileButtonAction(@RequestParam int currentPersonId, @RequestBody PersonIniDto personIniDto){
        Person currentPerson = personService.findById(currentPersonId);
        currentPerson.setFirstname(personIniDto.getFirstname());
        currentPerson.setLastName(personIniDto.getLastName());
        if(currentPerson.getUserName().equals(personIniDto.getUserName()) || !personService.existsPersonByUserName(personIniDto.getUserName())){
            currentPerson.setUserName(personIniDto.getUserName());
        }
        currentPerson.setEmailAddress(personIniDto.getEmailAddress());
        currentPerson.setToShowEmail(personIniDto.isToShowEmail());
        currentPerson = personService.save(currentPerson);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/addpersontocategorybuttonaction")
    public ResponseEntity<Void> addPersonToCategoryButtonAction(@RequestParam int categoryId, @RequestBody List<Integer> peopleToAdd){
        List<Person> selectedPeopleFromCategoryManaging = new LinkedList<Person>();
        for(int personId:peopleToAdd) selectedPeopleFromCategoryManaging.add(personService.findById(personId));
        Category category = categoryService.findById(categoryId);
        if(category==null){
            return(new ResponseEntity<Void>(HttpStatus.OK));
        }
        for(Person person:selectedPeopleFromCategoryManaging){
            category = categoryService.addPerson(person, category);
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/deletecategorybuttonaction")
    public ResponseEntity<Void> deleteCategoryButtonAction(@RequestParam int currentPersonId, @RequestParam int categoryId){
        Person currentPerson = personService.findById(currentPersonId);
        Category category = categoryService.findById(categoryId);
        currentPerson.getOwnedCategories().remove(category);
        categoryService.delete(category);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/setting/removepersonfromcategorybuttonaction")
    public ResponseEntity<Void> removePersonFromCategoryButtonAction(@RequestParam int categoryId, @RequestBody List<Integer> peopleToRemove){
        Category category = categoryService.findById(categoryId);
        for(int personId:peopleToRemove){
            Person person = personService.findById(personId);
            if(category.getDestPeopleList().contains(person)){
                category = categoryService.delPerson(person, category);
            }
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}















