package com.example.tw1tt3rServer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class secureController {
    @PostMapping
    String doSth(){
        return("ahhhhhh");
    }
}
