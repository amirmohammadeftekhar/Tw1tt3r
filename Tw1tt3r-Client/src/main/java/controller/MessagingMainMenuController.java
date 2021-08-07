package controller;

import controller.utility.ModelAccess;
import dtos.MessageDto;
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

import java.net.URL;
import java.util.*;

import static controller.utility.ModelAccess.currentPerson;
import static controller.utility.ModelAccess.currentPersonId;

public class MessagingMainMenuController extends AbstractController implements Initializable {

    private final Map<RoomDto, Parent> roomToChatBoxParent = new HashMap<RoomDto, Parent>();
    private final Map<RoomDto, RoomChatBoxController> roomToController = new HashMap<RoomDto, RoomChatBoxController>();
    private int chatsPointer = 0;


    @FXML
    private BorderPane chatRoomPane;

    @FXML
    private GridPane chatsGridPane;

    public void addRoomToChatsWindow(RoomDto room){
        int unreadMessageCount = 0;
        for(MessageDto message:room.getMessages()){
            if(message.getSourcePerson().getId() == currentPersonId){
                continue;
            }
            unreadMessageCount += message.getWhoHasRead().contains(currentPerson)?0:1;
        }
        Parent outViewParent = ViewFactory.viewFactory.GetRoomOutViewParent(room, unreadMessageCount);
        ModelAccess.roomToChatBox = room;
        ViewObjects viewObjects = ViewFactory.viewFactory.getRoomChatBoxViewObjects();
        Parent parent = viewObjects.getParent();
        RoomChatBoxController controller = (RoomChatBoxController) viewObjects.getAbstractController();
        roomToChatBoxParent.put(room, parent);
        roomToController.put(room, controller);
        outViewParent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openChatBox(room);
        });
        chatsGridPane.add(outViewParent, 0, ++chatsPointer);
        controller.reload();
    }


    @Override
    protected void reload() {

        chatsGridPane.getChildren().clear();
        chatsPointer = 0;
        List<RoomDto> rooms = new LinkedList<RoomDto>(currentPerson.getRooms());
        rooms.sort((a, b) -> b.getLastMessageTimeStamp().compareTo(a.getLastMessageTimeStamp()));
        for(RoomDto room:rooms){
            addRoomToChatsWindow(room);
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

    }
}
