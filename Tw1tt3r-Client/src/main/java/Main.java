import database.DataBaseUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.hibernate.Session;

public class Main extends Application {
    @SneakyThrows
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Session session = DataBaseUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.close();
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






















