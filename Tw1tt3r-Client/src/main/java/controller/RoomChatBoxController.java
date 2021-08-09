package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import dtos.MessageDto;
import dtos.PictureDto;
import dtos.RoomDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.ViewFactory;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.RoomChatBoxControllerService;
import web.serviceinterfaces.services.MessageServiceControllerService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.currentPersonId;
import static controller.utility.ModelAccess.messagingMainMenuController;
import static view.ViewUtility.isImage;
import static view.ViewUtility.makePicture;

public class RoomChatBoxController extends AbstractController implements Initializable {

    private RoomDto room;

    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    private HBox attachButton;

    @FXML
    private GridPane messagesGridPane;

    @FXML
    private TextArea messageToSendArea;

    @FXML
    private HBox sendButton;


    @FXML
    void sendButtonAction(MouseEvent event) {
        String toSend = messageToSendArea.getText();
        messageToSendArea.clear();
        int pictureId=-1;
        if(pictureToSend!=null && pictureToSend.getId()>0){
            pictureId = pictureToSend.getId();
        }
        TransactionServiceGenerator.getInstance().createService(RoomChatBoxControllerService.class).sendButtonAction(toSend, currentPersonId, room.getId(), pictureId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    private final FileChooser fileChooser = new FileChooser();
    private PictureDto pictureToSend;


    @FXML
    void attachButtonAction(MouseEvent event) {
        File file = fileChooser.showOpenDialog(attachButton.getScene().getWindow());
        if(!isImage(file)){
            return;
        }
        pictureToSend = makePicture(file);
    }

    @Override
    public void reload() {
        room = WebUtil.getRoom(room.getId());
        messagesGridPane.getChildren().clear();
        int t = 0;
        for(MessageDto message:room.getMessages()){
            Parent messageParent = ViewFactory.viewFactory.getMessageParent(message);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem delete = new MenuItem(ConfigInstance.getInstance().getProperty("delete"));
            MenuItem edit = new MenuItem(ConfigInstance.getInstance().getProperty("edit"));
            contextMenu.getItems().addAll(edit, delete);
            delete.setOnAction(event -> {
                if(message.getSourcePerson().getId() != currentPersonId){
                    return;
                }
                room.getMessages().remove(message);
                message.setDestinationRoom(null);
                TransactionServiceGenerator.getInstance().createService(MessageServiceControllerService.class).saveMessage(message).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                    }
                });
                reload();
                messagingMainMenuController.reload();
            });
            edit.setOnAction(event -> {
                if(message.getSourcePerson().getId() != currentPersonId){
                    return;
                }
                message.setText(messageToSendArea.getText());
                TransactionServiceGenerator.getInstance().createService(MessageServiceControllerService.class).saveMessage(message).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                    }
                });
                messageToSendArea.clear();
                reload();
                messagingMainMenuController.reload();
            });
            messageParent.setOnContextMenuRequested(event -> contextMenu.show(messageParent, event.getScreenX(), event.getScreenY()));
            if(message.getSourcePerson().getId() == currentPersonId){
                messagesGridPane.add(messageParent, 1, ++t);
            }
            else{
                messagesGridPane.add(messageParent, 0, ++t);
            }
        }
        messagesScrollPane.vvalueProperty().bind(messagesGridPane.heightProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        room = ModelAccess.roomToChatBox;
        super.initialize(location, resources);
    }


}
