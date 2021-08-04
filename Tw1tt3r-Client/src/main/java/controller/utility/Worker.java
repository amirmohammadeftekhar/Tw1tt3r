package controller.utility;

import controller.AbstractController;
import javafx.application.Platform;

import static controller.utility.ModelAccess.currentPersonId;

public class Worker {
    public static void begin(){
        Thread thread = new Thread(() -> {
            while (true){
                Platform.runLater(AbstractController::reloadAll);
                WebUtil.updateLastSeen(currentPersonId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
