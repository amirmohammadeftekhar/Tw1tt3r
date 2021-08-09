package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import dtos.PersonDto;
import dtos.PersonIniDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import retrofit2.Response;
import view.ViewFactory;
import view.ViewObjects;
import web.BaseResponse;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.EntryControllerService;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController implements Initializable {


    @FXML
    private AnchorPane signupPane;

    @FXML
    private CheckBox toShowEmailCheckBox;

    @FXML
    private CheckBox toBePrivateAccountCheckBox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField emailField;

    @FXML
    private javafx.scene.control.PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label infoLabel;

    @FXML
    void signUpButtonAction(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String userName = userNameField.getText();
        String emailAddr = emailField.getText();
        String password = passwordField.getText();
        boolean toShowEmail = toShowEmailCheckBox.isSelected();
        boolean toBePrivate = toBePrivateAccountCheckBox.isSelected();
        TransactionServiceGenerator.getInstance().createService(EntryControllerService.class).signup(
                new PersonIniDto(firstName, lastName, userName, password, emailAddr, toBePrivate, toShowEmail)).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void DoOnResponse(Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                switch (baseResponse.getResponseHeader()){
                    case USERNAME_NOT_EXISTS: {
                        infoLabel.setText(ConfigInstance.getInstance().getProperty("userNameNotAvailable"));
                        break;
                    }
                    case EMAIL_NOT_EXISTS: {
                        infoLabel.setText(ConfigInstance.getInstance().getProperty("addressNotAvailable"));
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
