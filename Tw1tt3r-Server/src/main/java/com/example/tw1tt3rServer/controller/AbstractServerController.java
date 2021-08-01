package com.example.tw1tt3rServer.controller;

import java.sql.Timestamp;

public abstract class AbstractServerController {
    protected Timestamp getTimeStamp(){
        return(new Timestamp(System.currentTimeMillis()));
    }
}
