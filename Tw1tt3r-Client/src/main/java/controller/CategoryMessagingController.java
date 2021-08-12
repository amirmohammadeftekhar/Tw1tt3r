package controller;

import controller.utility.ModelAccess;
import dtos.CategoryDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.CategoryMessagingControllerService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.currentPersonId;

public class CategoryMessagingController extends AbstractController implements Initializable {
    private CategoryDto category;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Button uploadImageButton;

    @FXML
    private Button sendButton;

    @Override
    public void reload() {

    }

    @FXML
    void sendButtonAction(MouseEvent event) {
        String toSend = messageTextArea.getText();
        try {
            TransactionServiceGenerator.getInstance().createService(CategoryMessagingControllerService.class).sendMessage(category.getId(), currentPersonId, toSend).execute();
        } catch (IOException e) {
            return;
        }
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void uploadImageButtonAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
        this.category = ModelAccess.categoryToCategoryMessaging;
    }
}
