package controller;

import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import dtos.MessageDto;
import dtos.PersonDto;
import dtos.RoomDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.ViewFactory;
import view.ViewObjects;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.MessagingMainMenuControllerService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static controller.utility.ModelAccess.currentPersonId;

public class MessagingMainMenuController extends AbstractController implements Initializable {

    private final Map<RoomDto, Parent> roomToChatBoxParent = new HashMap<RoomDto, Parent>();
    private final Map<RoomDto, RoomChatBoxController> roomToController = new HashMap<RoomDto, RoomChatBoxController>();
    private int chatsPointer = 0;


    @FXML
    private BorderPane chatRoomPane;

    @FXML
    private GridPane chatsGridPane;

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
        outViewParent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openChatBox(room);
            openedChat = room;
        });
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


    @Override
    public void reload() {
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
        if(openedChat != null){
            RoomChatBoxController controller = roomToController.get(openedChat);
            controller.reload();
            openChatBox(openedChat);
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
