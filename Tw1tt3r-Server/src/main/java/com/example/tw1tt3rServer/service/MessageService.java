package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.aspects.NoLogging;
import com.example.tw1tt3rServer.repository.MessageRepository;
import com.example.tw1tt3rServer.repository.entity.Message;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import entities.enums.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired PersonService personService;
    @Autowired ActionService actionService;

    public Message save(Message message) {
        return (messageRepository.save(message));
    }

    @NoLogging
    public Message findById(int id){
        return(messageRepository.findById(id).get());
    }

    private Person getPvRoomMember(Room room, Person ignoring){
        for(Person person:room.getMembers()) if(!person.equals(ignoring)) return(person);
        return(null);
    }

    public Message makeMessage(Person sourcePerson, Room destinationRoom, Timestamp timestamp, String text, Picture picture) {
        if(destinationRoom.getRoomType()== RoomType.PRIVATE && !actionService.isMessagingAllowed(sourcePerson, getPvRoomMember(destinationRoom, sourcePerson))){
            return(null);
        }
        Message message = new Message();
        message.setSourcePerson(sourcePerson);
        message.setDestinationRoom(destinationRoom);
        message.setTimestamp(timestamp);
        message.setText(text);
        message.setNotified(false);
        message.setPicture(picture);
        destinationRoom.getMessages().add(message);
        return(save(message));
    }

    @NoLogging
    public Message addWhoRead(Person person, Message message){
        person = personService.findById(person.getId());
        message.getWhoHasRead().add(person);
        person.getReadMessages().add(message);
        personService.save(person);
        return(save(message));
    }

    public void setNotified(Message message) {
        message.setNotified(true);
        save(message);
    }

}
