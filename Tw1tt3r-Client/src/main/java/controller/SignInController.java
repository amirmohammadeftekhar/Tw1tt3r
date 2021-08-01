package controller;

import config.ConfigInstance;
import dtos.PersonDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Response;
import web.BaseResponse;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.services.EntryControllerService;

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


    @SneakyThrows
    @FXML
    void signinAction(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        TransactionServiceGenerator.getInstance().createService(EntryControllerService.class)
                .signin(userName, password).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                super.onResponse(call, response);
                BaseResponse baseResponse = response.body();
                switch (baseResponse.getResponseHeader()){
                    case USERNAME_NOT_EXISTS: {
                        infoLabel.setText(ConfigInstance.getInstance().getProperty("userNameNotAvailable"));
                        break;
                    }
                    case WRONG_PASSWORD: {
                        infoLabel.setText(ConfigInstance.getInstance().getProperty("wrongPassword"));
                        break;
                    }
                    case OK: {
                        PersonDto person = (PersonDto) baseResponse.getDto();
                        TransactionServiceGenerator.setToken(person.getToken());
                        System.out.println(person.getToken());
/*
                        ModelAccess.currentPersonId = person.getId();
                        Scene scene = ViewFactory.viewFactory.getMainMenuScene();
                        Stage stage = ViewFactory.viewFactory.getStage();
                        stage.setTitle("Main window");
                        stage.setScene(scene);
*/
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


















