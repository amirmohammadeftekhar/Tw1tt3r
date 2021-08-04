import controller.utility.WebUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import web.TransactionServiceGenerator;

public class Main extends Application {
    @SneakyThrows
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TransactionServiceGenerator.setToken("c96cae99-9ba9-4e8d-bfd1-a0eb71950d8e");
        System.out.println(WebUtil.getPerson(4).getToken());
/*
        primaryStage.setTitle("Entering");
        ViewFactory viewFactory = ViewFactory.viewFactory;
        viewFactory.setStage(primaryStage);
        Scene scene = viewFactory.getEnteringScene();
        ViewUtility.makeStageAtCenter(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        Worker.begin();
*/
    }
}






















