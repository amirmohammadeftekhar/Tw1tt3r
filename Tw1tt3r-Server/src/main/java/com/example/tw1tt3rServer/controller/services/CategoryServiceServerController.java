package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Category;
import com.example.tw1tt3rServer.repository.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryServiceServerController extends AbstractServerController {
    @PostMapping("api/categoryservice/sendmessage")
    public ResponseEntity<Void> sendMessage(@RequestParam int categoryId, @RequestParam int currentPersonId, @RequestParam String text){
        Person currentPerson = personService.findById(currentPersonId);
        Category category = categoryService.findById(categoryId);
        categoryService.sendMessage(category, currentPerson, text);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}
