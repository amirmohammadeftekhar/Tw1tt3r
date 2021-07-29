package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class tokenController {
    @Autowired
    PersonService personService;
    @PostMapping
    public String getToken(@RequestParam String userName, @RequestParam String password){
        String token = personService.login(userName, password);
        if(StringUtils.isEmpty(token)){
            return("no token found");
        }
        return(token);
    }
}
