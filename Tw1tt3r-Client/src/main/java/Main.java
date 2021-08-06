import controller.utility.Worker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import view.ViewFactory;
import view.ViewUtility;

public class Main extends Application {
    @SneakyThrows
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Entering");
        ViewFactory viewFactory = ViewFactory.viewFactory;
        viewFactory.setStage(primaryStage);
        Scene scene = viewFactory.getEnteringScene();
        ViewUtility.makeStageAtCenter(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        Worker.begin();
    }
}






















