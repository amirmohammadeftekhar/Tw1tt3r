package controller;

import dtos.PersonDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Getter
public abstract class AbstractController {

    private static final List<AbstractController> controllers = new LinkedList<AbstractController>();

    @Setter
    protected PersonDto personDto;

    public Timestamp getTimeStamp() {
        return (new Timestamp(System.currentTimeMillis()));
    }

    protected abstract void reload();

    public AbstractController(){
        controllers.add(this);
    }

    public static void reloadAll(){
        controllers.forEach(AbstractController::reload);
    }
}
