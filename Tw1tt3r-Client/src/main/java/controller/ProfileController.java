package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import controller.utility.enums.ShowListChoices;
import dtos.ActionDto;
import dtos.PersonDto;
import dtos.ProfileReloadDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Response;
import utility.TimeLineParent;
import utility.enums.TimeLineParents;
import view.ViewFactory;
import view.ViewUtility;
import web.BaseResponse;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.ProfileControllerService;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.*;

public class ProfileController extends AbstractController implements Initializable {

    @FXML
    private ImageView profileImage;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private VBox tweetButton;

    @FXML
    private Label tweetCount;

    @FXML
    private VBox followingButton;

    @FXML
    private Label followingCount;

    @FXML
    private VBox followerButton;

    @FXML
    private Label followerCount;

    @FXML
    private VBox blackListButton;

    @FXML
    private Label blockListCount;

    @FXML
    private Button makeTweetButton;

    @FXML
    private GridPane yourRequestsGridPane;

    @FXML
    private GridPane otherRequestsPane;

    @FXML
    private GridPane newFollowerGridPane;

    @FXML
    private GridPane newUnfollowerGridPane;

    @FXML
    private GridPane newRejectorGridPane;


    @SneakyThrows
    @FXML
    void blackListButtonAction(MouseEvent event) {
        ModelAccess.showListChoice = ShowListChoices.BLACKLIST;
        ModelAccess.peopleListToShow = TransactionServiceGenerator.getInstance().createService(ProfileControllerService.class).getBlockingPersons(currentPersonId).execute().body();
        Scene scene = ViewFactory.viewFactory.getShowListScene();
        Stage stage = ViewUtility.getNewStage(blackListButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("blackList"));
        stage.setScene(scene);
        stage.show();
        reload();
    }

    @SneakyThrows
    @FXML
    void followerButtonAction(MouseEvent event) {
        ModelAccess.showListChoice = ShowListChoices.FOLLOWER;
        ModelAccess.peopleListToShow = TransactionServiceGenerator.getInstance().createService(ProfileControllerService.class).getFollowersPersons(currentPersonId).execute().body();
        Scene scene = ViewFactory.viewFactory.getShowListScene();
        Stage stage = ViewUtility.getNewStage(followerButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("followers"));
        stage.setScene(scene);
        stage.show();
        reload();
    }

    @SneakyThrows
    @FXML
    void followingButtonAction(MouseEvent event) {
        ModelAccess.showListChoice = ShowListChoices.FOLLOWING;
        ModelAccess.peopleListToShow = TransactionServiceGenerator.getInstance().createService(ProfileControllerService.class).getFollowingspersons(currentPersonId).execute().body();
        Scene scene = ViewFactory.viewFactory.getShowListScene();
        Stage stage = ViewUtility.getNewStage(followingButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("following"));
        stage.setScene(scene);
        stage.show();
        reload();
    }

    @FXML
    void makeTweetButtonAction(MouseEvent event) {
        Scene scene = ViewFactory.viewFactory.getTweetMakingScene();
        Stage stage = ViewUtility.getNewStage(tweetButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("tweetmaking"));
        stage.setScene(scene);
        stage.show();
        reload();
    }

    @FXML
    void tweetButtonAction(MouseEvent event) {
        timeLineController.getParents().add(new TimeLineParent(currentPersonId, TimeLineParents.PERSON));
        timeLineController.reload();
        mainMenuController.homeButtonAction(null);
        reload();
    }

    @Override
    public void reload() {

        PersonDto currentPerson = WebUtil.getPerson(currentPersonId);
        TransactionServiceGenerator.getInstance().createService(ProfileControllerService.class).reload(currentPersonId).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable throwable) {
                super.onFailure(call, throwable);
                System.out.println(throwable);
            }

            @Override
            public void DoOnResponse(Response<BaseResponse> response) {
                ProfileReloadDto dto = (ProfileReloadDto) response.body().getDto();
                userNameLabel.setText(currentPerson.getUserName());
                nameLabel.setText(currentPerson.getFirstname() + " " + currentPerson.getLastName());
                mailLabel.setText(currentPerson.getEmailAddress());
                newRejectorGridPane.getChildren().clear();
                newUnfollowerGridPane.getChildren().clear();
                newFollowerGridPane.getChildren().clear();
                yourRequestsGridPane.getChildren().clear();
                otherRequestsPane.getChildren().clear();
                blockListCount.setText(Integer.toString(dto.getBlockingsCount()));
                followerCount.setText(Integer.toString(dto.getFollowersCount()));
                followingCount.setText(Integer.toString(dto.getFollowingsCount()));
                tweetCount.setText(Integer.toString(dto.getTweetsCount()));
                if(currentPerson.getPicture() != null){
                    profileImage.setImage(ViewUtility.getPicture(currentPerson.getPicture().getId()));
                }
                int t_followers = 0;
                for(PersonDto person:dto.getNewFollowers()) {
                    Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
                    newFollowerGridPane.add(parent, 0, ++t_followers);
                }
                int t_unfollowers = 0;
                for(PersonDto person:dto.getNewUnfollowers()) {
                    Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
                    newUnfollowerGridPane.add(parent, 0, ++t_unfollowers);
                }
                int t_rejectors = 0;
                for(PersonDto person:dto.getNewRejectors()) {
                    Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
                    newRejectorGridPane.add(parent, 0, ++t_rejectors);
                }
                int t_your_requests = 0;
                for(PersonDto person:dto.getYourRequests()){
                    Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
                    yourRequestsGridPane.add(parent, 0, ++t_your_requests);
                }
                int t_others_requests = 0;
                System.out.println(dto.getOtherRequests().size());
                for(ActionDto action:dto.getOtherRequests()) {
                    PersonDto person = action.getSourcePerson();
                    Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
                    otherRequestsPane.add(parent, 0, ++t_others_requests);
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem confirm = new MenuItem(ConfigInstance.getInstance().getProperty("confirm"));
                    MenuItem rejectAndNotify = new MenuItem(ConfigInstance.getInstance().getProperty("rejectAndNotify"));
                    MenuItem rejectAndNotNotify = new MenuItem(ConfigInstance.getInstance().getProperty("rejectAndNotNotify"));
                    contextMenu.getItems().addAll(confirm, rejectAndNotNotify, rejectAndNotify);
                    confirm.setOnAction(event -> {
                        WebUtil.makeFollow(action.getSourcePerson().getId(), action.getDestinationPerson().getId());
                        WebUtil.deleteAction(action.getId());
                        reload();
                    });
                    rejectAndNotify.setOnAction(event -> {
                        WebUtil.makeReject(action.getDestinationPerson().getId(), action.getSourcePerson().getId());
                        WebUtil.deleteAction(action.getId());
                        reload();
                    });
                    rejectAndNotNotify.setOnAction(event -> {
                        WebUtil.deleteAction(action.getId());
                        reload();
                    });
                    parent.setOnContextMenuRequested(event -> contextMenu.show(parent, event.getScreenX(), event.getScreenY()));
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        reload();
//        super.initialize(location, resources);
    }
}
