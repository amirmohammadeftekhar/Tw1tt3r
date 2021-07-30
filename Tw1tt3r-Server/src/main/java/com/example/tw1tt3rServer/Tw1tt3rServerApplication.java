package com.example.tw1tt3rServer;

import com.example.tw1tt3rServer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tw1tt3rServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tw1tt3rServerApplication.class, args);
    }

}
