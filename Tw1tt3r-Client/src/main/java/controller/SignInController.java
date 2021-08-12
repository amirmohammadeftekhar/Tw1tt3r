package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import dtos.PersonDto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Response;
import view.ViewFactory;
import view.ViewObjects;
import web.BaseResponse;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.EntryControllerService;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController extends AbstractController implements Initializable {

    @FXML
    private Label infoLabel;

    @FXML
    private TextField userNameField;

    @FXML
    private javafx.scene.control.PasswordField passwordField;

    @FXML
    private Button signinButton;


    @FXML
    void signinAction(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        TransactionServiceGenerator.getInstance().createService(EntryControllerService.class)
                .signin(userName, password).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable throwable) {
                super.onFailure(call, throwable);
            }

            @Override
            public void DoOnResponse(Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                switch (baseResponse.getResponseHeader()){
                    case USERNAME_NOT_EXISTS: {
                        Platform.runLater(() -> infoLabel.setText(ConfigInstance.getInstance().getProperty("userNameNotAvailable")));
                        break;
                    }
                    case WRONG_PASSWORD: {
                        infoLabel.setText(ConfigInstance.getInstance().getProperty("wrongPassword"));
                        break;
                    }
                    case OK: {
                        PersonDto person = (PersonDto) baseResponse.getDto();
                        TransactionServiceGenerator.setToken(person.getToken());
                        ModelAccess.currentPersonId = person.getId();
                        ViewObjects viewObjects = ViewFactory.viewFactory.getMainMenuViewObjects();
                        Scene scene = viewObjects.getScene();
                        ModelAccess.mainMenuController = (MainMenuController) viewObjects.getAbstractController();
                        Stage stage = ViewFactory.viewFactory.getStage();
                        stage.setTitle("Main window");
                        stage.setScene(scene);
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        super.initialize(location, resources);
    }

    @Override
    public void reload() {

    }
}


















