package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.repository.RoomRepository;
import com.example.tw1tt3rServer.repository.entity.Message;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Room;
import com.example.tw1tt3rServer.repository.entity.enums.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired MessageService messageService;
    @Autowired PersonService personService;

    public Room save(Room room){
        return(roomRepository.save(room));
    }

    public void delete(Room room){
        roomRepository.delete(room);
    }

    public Room addPerson(Person person, Room room) {
        person = personService.findById(person.getId());
        room.getMembers().add(person);
        person.getRooms().add(room);
        person = personService.save(person);
        return(save(room));
    }

    public Room delPerson(Person person, Room room) {
        if(!room.getMembers().contains(person)){
            return(room);
        }
        room.getMembers().remove(person);
        person.getRooms().remove(room);
        return(save(room));
    }

    public Room makeRoom(String name, RoomType roomType){
        Room room = new Room();
        room.setName(name);
        room.setMembers(new HashSet<Person>());
        room.setRoomType(roomType);
        return(save(room));
    }

    public Room findByName(String name){
        return(roomRepository.findByName(name));
    }

    public boolean existsByName(String name){
        return(roomRepository.existsByName(name));
    }

    public Room sendMessage(String toSend, Person sourcePerson, Room room, Picture picture){
        Message message = messageService.makeMessage(sourcePerson, room, new Timestamp(System.currentTimeMillis()), toSend, picture);
        room.getMessages().add(message);
        return(save(room));

    }

    public boolean existsPv(Person person1, Person person2){
        return(findPv(person1, person2) != null);
    }

    public Room makePv(Person person1, Person person2){
        Room room = makeRoom("pv:"+person1.getUserName()+" and "+person2.getUserName(), RoomType.PRIVATE);
        room = addPerson(person1, room);
        room = addPerson(person2, room);
        return(room);
    }

    public Room findPv(Person person1, Person person2){
        List<Room> rooms = roomRepository.findAllByRoomType(RoomType.PRIVATE);
        for(Room room:rooms){
            if(room.getMembers().contains(person1) && room.getMembers().contains(person2)){
                return(room);
            }
        }
        return(null);
    }

}
