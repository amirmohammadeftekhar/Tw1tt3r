package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import database.DataBaseUtil;
import dtos.MessageDto;
import dtos.RoomDto;
import entity.MessageToSendEntity;
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
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utility.enums.MessageStatus;
import view.ViewFactory;
import web.BaseResponse;
import web.ResponseHeader;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.RoomChatBoxControllerService;
import web.serviceinterfaces.services.MessageServiceControllerService;

import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.currentPersonId;
import static controller.utility.ModelAccess.messagingMainMenuController;
import static view.ViewUtility.isImage;

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
        Session session = DataBaseUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        MessageToSendEntity message = new MessageToSendEntity();
        message.setRoomId(room.getId());
        message.setText(toSend);
        message.setPersonId(currentPersonId);
        message.setTimestamp(getTimeStamp());
        if(isImage(file)){
            message.setFileName(file.getName());
        }
        session.saveOrUpdate(message);
//        session.save(message);
        transaction.commit();
        session.close();
        file = null;

/*
        TransactionServiceGenerator.getInstance().createService(RoomChatBoxControllerService.class).sendButtonAction(toSend, currentPersonId, room.getId(), pictureId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
*/
    }

    private final FileChooser fileChooser = new FileChooser();
    private File file = null;


    @SneakyThrows
    @FXML
    void attachButtonAction(MouseEvent event) {
        file = fileChooser.showOpenDialog(attachButton.getScene().getWindow());
        Files.copy(file.toPath(), (new File("/tmp/"+file.getName()).toPath()), StandardCopyOption.REPLACE_EXISTING);
/*
        if(!isImage(file)){
            return;
        }
        pictureToSend = makePicture(file);
*/
    }
    private int showedUnsent = 0;
    private int t = 0;

    @Override
    public void reload() {
        try {
            room = WebUtil.getRoom(room.getId());
        } catch (IOException e) {
            Session session = DataBaseUtil.getSessionFactory().openSession();
            String hql = "SELECT m FROM MessageToSendEntity m where m.RoomId=" + room.getId() +" and m.personId="+ currentPersonId +" order by m.timestamp asc";
            Query query = session.createQuery(hql);
            List<MessageToSendEntity> list = query.getResultList();
            for(int i=showedUnsent;i<list.size();i++){
                MessageToSendEntity messageToSendEntity = list.get(i);
                MessageDto message = new MessageDto();
                message.setTimestamp(messageToSendEntity.getTimestamp());
                message.setText(messageToSendEntity.getText());
                message.setStatus(MessageStatus.SENDING);
                Parent messageParent = ViewFactory.viewFactory.getMessageParent(message, messageToSendEntity.getFileName()==null?null:new File("/tmp/"+messageToSendEntity.getFileName()));
                messagesGridPane.add(messageParent, 1, ++t);
                showedUnsent++;
            }
            return;
        }
        messagesGridPane.getChildren().clear();
        t = showedUnsent = 0;
        for(MessageDto message:room.getMessages()){
            Parent messageParent = ViewFactory.viewFactory.getMessageParent(message, null);
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
            String[] split = message.getText().split(" ");
            if(message.getText().length()>7 && split.length==2 && message.getText().substring(0, 8).equals("@room-pv")){
                messageParent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    TransactionServiceGenerator.getInstance().createService(RoomChatBoxControllerService.class)
                            .messageButtonActionUserName(currentPersonId, split[1]).enqueue(new TransactionCallBack<BaseResponse>() {
                        @Override
                        public void DoOnResponse(Response<BaseResponse> response) {
                            if(response.body().getResponseHeader()== ResponseHeader.ROOM_EXISTS){
                                RoomDto room = (RoomDto) response.body().getDto();
                                messagingMainMenuController.setOpenedChat(room);
                            }
                        }
                    });
                });
            }
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
//        super.initialize(location, resources);
    }


}
