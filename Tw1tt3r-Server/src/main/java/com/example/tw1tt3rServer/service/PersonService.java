package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.repository.PersonRepository;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import entities.enums.LastSeenType;
import entities.enums.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    //------------------------------------------------------------------------------------------------------------

    @Autowired
    PersonRepository personRepository;
    @Autowired RoomService roomService;

    //------------------------------------------------------------------------------------------------------------

    public Person save(Person person) {
        return (personRepository.save(person));
    }

    public Person findById(int id){
        return(personRepository.findById(id).get());
    }

    //------------------------------------------------------------------------------------------------------------

    public Person findPersonByUserName(String userName) {
        return (personRepository.findPersonByUserNameAndActiveStateIsTrueAndDeletedIsFalse(userName));
    }

    public Person findPersonByUserNameNotCheckingActiveState(String userName) {
        return (personRepository.findPersonByUserNameAndDeletedIsFalse(userName));
    }

    public boolean existsPersonByUserName(String userName) {
        return (personRepository.existsPersonByUserNameAndDeletedIsFalse(userName));
    }

    public boolean existsPersonByEmailAddress(String emailAddress) {
        return (personRepository.existsPersonByEmailAddressAndDeletedIsFalse(emailAddress));
    }

    public Person updateLastSeen(Person person) {
        person.setLastSeen(new Timestamp(System.currentTimeMillis()));
        return(personRepository.save(person));
    }

    public Person makePerson(String firstName, String lastName, String userName, String password, String emailAddress,
                             boolean activeState, Timestamp lastSeen, boolean isPrivate, LastSeenType lastSeenType,
                             boolean toShowEmail) {
        Person person = new Person();
        person.setFirstname(firstName);
        person.setLastName(lastName);
        person.setUserName(userName);
        person.setPassword(password);
        person.setEmailAddress(emailAddress);
        person.setActiveState(true);
        person.setLastSeen(lastSeen);
        person.setPrivate(isPrivate);
        person.setLastSeenType(lastSeenType);
        person.setToShowEmail(toShowEmail);
        person = save(person);
        Room room = roomService.makeRoom("saved message", RoomType.SAVEDMESSAGE);
        room = roomService.addPerson(person, room);
        person.setSavedMessageRoom(room);
        return(save(person));
    }

    public void deleteAccount(Person person) {
        person.setDeleted(true);
        person.setActiveState(false);
        person.setUserName("Deleted account");
        person.setEmailAddress("Deleted account");
        save(person);
    }

    public void changePrivatePublicState(Person person, boolean isPrivate) {
        person.setPrivate(isPrivate);
        save(person);
    }

    public void changeLastSeenType(Person person, LastSeenType lastSeenType) {
        person.setLastSeenType(lastSeenType);
        save(person);
    }

    public void changeActiveState(Person person, boolean isActive) {
        person.setActiveState(isActive);
        save(person);
    }

    public void changePassword(Person person, String password) {
        person.setPassword(password);
        save(person);
    }

    public void changeFirstName(Person person, String firstName) {
        person.setFirstname(firstName);
        save(person);
    }

    public void changeLastName(Person person, String lastName) {
        person.setLastName(lastName);
        save(person);
    }

    public List<Person> findAllByActiveStateIsTrueAndDeletedIsFalse() {
        return (personRepository.findAllByActiveStateIsTrueAndDeletedIsFalse());
    }

    public Person changePicture(Person person, Picture picture){
        person.setPicture(picture);
        return(save(person));
    }

    public void login(String userName, String password) {
        Optional<Person> personOptional = personRepository.login(userName,password);
        if(personOptional.isPresent()){
            String token = UUID.randomUUID().toString();
            Person person = (Person) personOptional.get();
            person.setToken(token);
            personRepository.save(person);
        }

        return;
    }

    public Optional<User> findByToken(String token){
        Optional personOptional = personRepository.findByToken(token);
        if(personOptional.isPresent()){
            Person person = (Person) personOptional.get();
            User user = new User(person.getUserName(), person.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
            return(Optional.of(user));
        }
        return(Optional.empty());
    }

}






















