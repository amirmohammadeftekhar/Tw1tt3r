package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.bot.BotManager;
import com.example.tw1tt3rServer.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public abstract class AbstractServerController {
    protected Timestamp getTimeStamp(){
        return(new Timestamp(System.currentTimeMillis()));
    }
    @Autowired protected PersonService personService;
    @Autowired protected CategoryService categoryService;
    @Autowired protected TweetService tweetService;
    @Autowired protected PictureService pictureService;
    @Autowired protected ActionService actionService;
    @Autowired protected MessageService messageService;
    @Autowired protected RoomService roomService;
    @Autowired protected BotManager botManager;
}
