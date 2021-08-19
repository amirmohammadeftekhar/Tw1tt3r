package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import database.DataBaseUtil;
import dtos.MessageDto;
import dtos.PersonDto;
import dtos.PictureDto;
import dtos.RoomDto;
import entity.MessageToSendEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.ViewFactory;
import view.ViewObjects;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.MessagingMainMenuControllerService;
import web.serviceinterfaces.RoomChatBoxControllerService;

import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static controller.utility.ModelAccess.currentPersonId;
import static controller.utility.ModelAccess.messagingMainMenuController;
import static view.ViewUtility.isImage;
import static view.ViewUtility.makePicture;

public class MessagingMainMenuController extends AbstractController implements Initializable {

    private final Map<RoomDto, Parent> roomToChatBoxParent = new HashMap<RoomDto, Parent>();
    private final Map<RoomDto, RoomChatBoxController> roomToController = new HashMap<RoomDto, RoomChatBoxController>();
    private int chatsPointer = 0;


    @FXML
    private BorderPane chatRoomPane;

    @FXML
    private GridPane chatsGridPane;

    @Setter
    private RoomDto openedChat;

    public void addRoomToChatsWindow(RoomDto room, PersonDto currentPerson){
        if(currentPerson == null){
            try {
                currentPerson = WebUtil.getPerson(currentPersonId);
            } catch (IOException e) {
                return;
            }
        }
        int unreadMessageCount = 0;
        for(MessageDto message:room.getMessages()){
            if(message.getSourcePerson().getId() == currentPersonId){
                continue;
            }
            unreadMessageCount += message.getWhoHasRead().contains(currentPerson)?0:1;
        }
        Parent outViewParent = ViewFactory.viewFactory.GetRoomOutViewParent(room, unreadMessageCount);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem(ConfigInstance.getInstance().getProperty("delete"));
        contextMenu.getItems().addAll(delete);
        delete.setOnAction(event -> {
            TransactionServiceGenerator.getInstance().createService(MessagingMainMenuControllerService.class).deleteRoom(currentPersonId, room.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                }
            });
            chatRoomPane.setCenter(null);
            openedChat = null;
            reload();
            messagingMainMenuController.reload();
        });
        outViewParent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openChatBox(room);
            openedChat = room;
        });
        outViewParent.setOnContextMenuRequested(event -> contextMenu.show(outViewParent, event.getScreenX(), event.getScreenY()));
        chatsGridPane.add(outViewParent, 0, ++chatsPointer);
        if(roomToChatBoxParent.containsKey(room)){
            return;
        }
        ModelAccess.roomToChatBox = room;
        ViewObjects viewObjects = ViewFactory.viewFactory.getRoomChatBoxViewObjects();
        Parent parent = viewObjects.getParent();
        RoomChatBoxController controller = (RoomChatBoxController) viewObjects.getAbstractController();
        roomToChatBoxParent.put(room, parent);
        roomToController.put(room, controller);
        controller.reload();
    }

    void sendAll() {
        Session session1 = DataBaseUtil.getSessionFactory().openSession();
        String hql = "SELECT m FROM MessageToSendEntity m where m.personId="+Integer.toString(currentPersonId)+" order by m.timestamp asc";
        Query query = session1.createQuery(hql);
        List<MessageToSendEntity> list = query.getResultList();
        session1.close();
        for(MessageToSendEntity message:list){
            try {
                Thread.sleep(400);
            } catch (InterruptedException ignored) {
            }
            int pictureId = -1;
            File file = new File("/tmp/"+message.getFileName());
            if(isImage(file)){
                PictureDto pictureToSend = null;
                try {
                    pictureToSend = makePicture(file);
                } catch (IOException e) {
                    continue;
                }
                if(pictureToSend!=null && pictureToSend.getId()>0) pictureId = pictureToSend.getId();
            }
            TransactionServiceGenerator.getInstance().createService(RoomChatBoxControllerService.class).sendButtonAction(message.getText(), currentPersonId, message.getRoomId(), pictureId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Session session2 = DataBaseUtil.getSessionFactory().openSession();
                    Transaction transaction = session2.getTransaction();
                    transaction.begin();
                    session2.delete(message);
                    transaction.commit();
                    session2.close();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                }
            });
        }

    }


    @Override
    public void reload() {
        sendAll();
        if(openedChat != null){
            RoomChatBoxController controller = roomToController.get(openedChat);
            controller.reload();
            openChatBox(openedChat);
        }
        PersonDto currentPerson = null;
        try {
            currentPerson = WebUtil.getPerson(currentPersonId);
        } catch (IOException e) {
            return;
        }
        chatsGridPane.getChildren().clear();
        chatsPointer = 0;
        List<RoomDto> rooms = new LinkedList<RoomDto>(currentPerson.getRooms());
        rooms.sort((a, b) -> b.lastMessageTimeStamp().compareTo(a.lastMessageTimeStamp()));
        for(RoomDto room:rooms){
            addRoomToChatsWindow(room, currentPerson);
        }
    }

    public void openChatBox(RoomDto room){
        TransactionServiceGenerator.getInstance().createService(MessagingMainMenuControllerService.class).openChatBox(currentPersonId, room.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        chatRoomPane.setCenter(roomToChatBoxParent.get(room));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
}
