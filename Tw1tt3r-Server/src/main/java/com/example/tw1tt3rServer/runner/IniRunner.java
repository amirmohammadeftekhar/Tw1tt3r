package com.example.tw1tt3rServer.runner;

import com.example.tw1tt3rServer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class IniRunner implements CommandLineRunner {

    @Autowired PersonService personService;

    @Override
    public void run(String... args) throws Exception {
//        personService.makePerson("a", "a", "a", "a", "a", true, null, false, LastSeenType.EVERYBODY, true);
    }
}
