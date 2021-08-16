package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import dtos.PersonDto;
import dtos.RoomDto;
import entities.enums.LastSeenType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.ViewUtility;
import web.BaseResponse;
import web.ResponseHeader;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.PersonalPageControllerService;
import web.serviceinterfaces.services.ActionServiceControllerService;
import web.serviceinterfaces.services.PersonServiceControllerService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.*;

public class PersonalPageController extends AbstractController implements Initializable {


    private PersonDto person;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private VBox followButton;

    @FXML
    private Label lastSeenLabel;


    @FXML
    private Label followLabel;

    @FXML
    private HBox blockButton;

    @FXML
    private HBox muteButton;

    @FXML
    private HBox messageButton;

    @FXML
    private Label followingCount;

    @FXML
    private Label followerCount;


    @SneakyThrows
    @FXML
    void blockButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(PersonalPageControllerService.class).blockButtonAction(currentPersonId, person.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {

            }
        });
        Thread.sleep(1000);
        reload();
    }

    private void reloadFollowButton(){
        TransactionServiceGenerator.getInstance().createService(PersonalPageControllerService.class).getFollowStatus(currentPersonId, person.getId()).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void DoOnResponse(Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                switch (baseResponse.getResponseHeader()){
                    case REQUESTED_STATUS: {
                        followLabel.setText(ConfigInstance.getInstance().getProperty("requested"));
                        ViewUtility.makeSelected(followButton);
                        break;
                    }
                    case FOLLOWING_STATUS: {
                        followLabel.setText(ConfigInstance.getInstance().getProperty("following"));
                        ViewUtility.makeSelected(followButton);
                        break;
                    }
                    case FOLLOW_STATUS: {
                        followLabel.setText(ConfigInstance.getInstance().getProperty("follow"));
                        ViewUtility.makeUnSelected(followButton);
                        break;
                    }
                }
            }
        });
    }

    private void reloadBlockButton(){
        TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).isBlocking(currentPersonId, person.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                final boolean isBlocking = response.body();
                Platform.runLater(() -> {
                    if(isBlocking){
                        ViewUtility.makeSelected(blockButton);
                    }
                    else{
                        ViewUtility.makeUnSelected(blockButton);
                    }
                });
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
            }
        });

    }

    @SneakyThrows
    @FXML
    void followButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(PersonalPageControllerService.class).followButtonAction(currentPersonId, person.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reload();
    }

    @FXML
    void messageButtonAction(MouseEvent event) {
        mainMenuController.messageButtonAction(null);
        TransactionServiceGenerator.getInstance().createService(PersonalPageControllerService.class).messageButtonAction(currentPersonId, person.getId()).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void DoOnResponse(Response<BaseResponse> response) {
                RoomDto room = (RoomDto) response.body().getDto();
                if(response.body().getResponseHeader() == ResponseHeader.ROOM_NOT_EXISTS){

                    messagingMainMenuController.addRoomToChatsWindow(room, null);
                }
                messagingMainMenuController.openChatBox(room);
                messagingMainMenuController.setOpenedChat(room);
            }
        });
        reload();
    }

    @SneakyThrows
    @FXML
    void muteButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(PersonalPageControllerService.class).muteButtonAction(currentPersonId, person.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reload();
    }

    private void reloadMuteButton(){
        TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).isSourceMuting(currentPersonId, person.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                final boolean isSourceMuting = response.body();
                Platform.runLater(() -> {
                    if(isSourceMuting){
                        ViewUtility.makeSelected(muteButton);
                    }
                    else{
                        ViewUtility.makeUnSelected(muteButton);
                    }
                });
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        person = ModelAccess.personToPersonalPageController;
        reload();
//        super.initialize(location, resources);
    }

    @Override
    public void reload() {
        PersonDto currentPerson = null;
        try {
            currentPerson = (PersonDto) TransactionServiceGenerator.getInstance().createService(PersonServiceControllerService.class).getPerson(currentPersonId).execute().body();
        } catch (IOException e) {
            return;
        }
        reloadFollowButton();
        reloadBlockButton();
        reloadMuteButton();
        userNameLabel.setText("@" + person.getUserName());
        nameLabel.setText(person.getFirstname() + person.getLastName());
        if(person.getPicture() != null){
            profileImage.setImage(ViewUtility.getPicture(person.getPicture().getId()));
        }
        try {
            followerCount.setText(TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowersPersonsCount(person.getId()).execute().body().toString());
        } catch (IOException ignored) {
        }
        try {
            followingCount.setText(TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowingsPersonsCount(person.getId()).execute().body().toString());
        } catch (IOException ignored) {
        }
        if(currentPerson.isToShowEmail()){
            emailLabel.setText(currentPerson.getEmailAddress());
        }
        Date date = new Date(person.getLastSeen().getTime());
        if(person.getLastSeenType() == LastSeenType.EVERYBODY){
            lastSeenLabel.setText("last seen: " + date.toString());
        }
        try {
            if(person.getLastSeenType() == LastSeenType.MYCONTACTS && TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).isFollowing(currentPersonId, person.getId()).execute().body()){
                lastSeenLabel.setText("last seen: " + date.toString());
            }
        } catch (IOException ignored) {
        }
    }
}
