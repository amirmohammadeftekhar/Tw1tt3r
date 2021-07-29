package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.repository.CategoryRepository;
import com.example.tw1tt3rServer.repository.entity.Category;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MessageService messageService;
    @Autowired RoomService roomService;
    @Autowired PersonService personService;

    private Category save(Category category) {
        return (categoryRepository.save(category));
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public Category findByNameAndSourcePerson(String name, Person sourcePerson) {
        return (categoryRepository.findByNameAndSourcePerson(name, sourcePerson));
    }

    public Category addPerson(Person person, Category category) {
        category.getDestPeopleList().add(person);
        person.getIncludedCategories().add(category);
        category = save(category);
        personService.save(person);
        return(category);
    }

    public Category delPerson(Person person, Category category) {
        if (!category.getDestPeopleList().contains(person)) {
            return(category);
        }
        category.getDestPeopleList().remove(person);
        person.getIncludedCategories().remove(category);
        category = save(category);
        personService.save(person);
        return(category);
    }

    public boolean existsByName(String name){
        return(categoryRepository.existsByName(name));
    }

    public Category makeCategory(String name, Person sourcePerson) {
        if(existsByName(name)){
            return(null);
        }
        Category category = new Category();
        category.setName(name);
        category.setSourcePerson(sourcePerson);
        category.setDestPeopleList(new HashSet<Person>());
        sourcePerson.getOwnedCategories().add(category);
        return (save(category));
    }

    public void sendMessage(Category category, Person sourcePerson, String toSend){
        for(Person person:category.getDestPeopleList()){
            Room room;
            if(roomService.existsPv(sourcePerson, person)){
                room = roomService.findPv(sourcePerson, person);
            }
            else{
                room = roomService.makePv(sourcePerson, person);
            }
            roomService.sendMessage(toSend, sourcePerson, room, null);
        }
    }

}
