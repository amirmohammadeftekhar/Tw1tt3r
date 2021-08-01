package controller;

import dtos.PersonDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
public abstract class AbstractController {
    @Setter
    protected PersonDto personDto;

    public Timestamp getTimeStamp() {
        return (new Timestamp(System.currentTimeMillis()));
    }
}
