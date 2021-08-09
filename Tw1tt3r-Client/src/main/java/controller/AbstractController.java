package controller;

import dtos.PersonDto;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

@Getter
public abstract class AbstractController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    Platform.runLater(() -> reload());
                    Thread.sleep(3000);
                }
            }
        });
//        thread.setDaemon(true);
        thread.start();
    }

    @Setter
    protected PersonDto personDto;

    public Timestamp getTimeStamp() {
        return (new Timestamp(System.currentTimeMillis()));
    }

    public abstract void reload();

    public AbstractController(){
    }

}
