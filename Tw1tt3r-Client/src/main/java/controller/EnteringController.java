package controller;

import config.ConfigInstance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EnteringController extends AbstractController implements Initializable {
    @FXML
    private Button signinButton;

    @FXML
    private Button signupButton;

    @FXML
    void signinButtonAction(ActionEvent event) {
        Scene scene = ViewFactory.viewFactory.getSignInScene();
        Stage stage = ViewFactory.viewFactory.getStage();
        stage.setTitle(ConfigInstance.getInstance().getProperty("signin"));
        stage.setScene(scene);
    }

    @FXML
    void signupButtonAction(ActionEvent event) {
        Scene scene = ViewFactory.viewFactory.getSignUpScene();
        Stage stage = ViewFactory.viewFactory.getStage();
        stage.setTitle("Sign up");
        stage.setScene(scene);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    protected void reload() {

    }
}
