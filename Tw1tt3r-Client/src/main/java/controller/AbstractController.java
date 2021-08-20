package controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import lombok.Getter;
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
                    Thread.sleep(4000);
                }
            }
        });
//        thread.setDaemon(true);
        thread.start();
    }

    public Timestamp getTimeStamp() {
        return (new Timestamp(System.currentTimeMillis()));
    }

    public abstract void reload();

    public AbstractController(){
    }

}
