package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import dtos.CategoryDto;
import dtos.PersonDto;
import dtos.RoomDto;
import dtos.TweetDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utility.TimeLineParent;
import utility.enums.TimeLineParents;
import view.ViewFactory;
import view.ViewObjects;
import view.ViewUtility;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.RoomChatBoxControllerService;
import web.serviceinterfaces.TweetControllerService;
import web.serviceinterfaces.services.CategoryServiceControllerService;
import web.serviceinterfaces.services.TweetServiceControllerService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static controller.utility.ModelAccess.*;

public class TweetController extends AbstractController implements Initializable {

    private TweetDto tweet;

    @FXML
    private VBox tweetArea;

    @FXML
    private ImageView profileImage;

    @FXML
    private TextArea textArea;

    @FXML
    private Pane tweetImagePane;

    @FXML
    private HBox saveTweetButton;

    @FXML
    private HBox retweetButton;

    @FXML
    private HBox choosingButton;

    @FXML
    private HBox forwardButton;

    @FXML
    private HBox blockWriterButton;

    @FXML
    private HBox muteWriterButton;

    @FXML
    private HBox reportButton;

    @FXML
    private HBox personalPageButton;

    @FXML
    private HBox commentButton;

    @FXML
    private ImageView tweetImage;

    @FXML
    private HBox commentsButton;

    @FXML
    private HBox likeButton;

    @FXML
    private ImageView likeImage;

    @FXML
    private Label numberOfLikeLabel;

    @FXML
    void blockWriterButtonAction(MouseEvent event) {
        WebUtil.block(currentPersonId, tweet.getPersonWhoMadeThis().getId());
        timeLineController.reload();
    }

    @FXML
    void choosingButtonAction(MouseEvent event) {
        PersonDto currentPerson = null;
        try {
            currentPerson = WebUtil.getPerson(currentPersonId);
        } catch (IOException e) {
            return;
        }
        Stage stage = ViewUtility.getNewStage(forwardButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("choosingWindow"));
        ViewObjects viewObjects = ViewFactory.viewFactory.getChoosingMenuViewObjects();
        ChoosingMenuController controller = (ChoosingMenuController) viewObjects.getAbstractController();
        ((ChoosingMenuController)controller).setCategoriesToChoose(currentPerson.getOwnedCategories());
        ((ChoosingMenuController)controller).setRoomsToChoose(currentPerson.getRooms());
        ((ChoosingMenuController)controller).setSelectedRooms(selectedRooms);
        ((ChoosingMenuController)controller).setSelectedCategories(selectedCategories);
        ((ChoosingMenuController)controller).reload();
        Scene scene = viewObjects.getScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void forwardButtonAction(MouseEvent event) {
        for(CategoryDto category:selectedCategories){
            TransactionServiceGenerator.getInstance().createService(CategoryServiceControllerService.class).sendMessage(category.getId(), currentPersonId, tweet.getText()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                }
            });
        }
        for(RoomDto room:selectedRooms){
            TransactionServiceGenerator.getInstance().createService(RoomChatBoxControllerService .class).sendButtonAction(tweet.getText(), currentPersonId, room.getId(), tweet.getPicture()==null?-1:tweet.getPicture().getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    System.out.println(throwable);
                }
            });
        }
        selectedRooms.clear();
        selectedCategories.clear();
    }


    @FXML
    void commentButtonAction(MouseEvent event) {
        ModelAccess.parentTweetToTweetMakingController = tweet;
        Scene scene = ViewFactory.viewFactory.getTweetMakingScene();
        Stage stage = ViewUtility.getNewStage(commentButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("retweet"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void commentsButtonAction(MouseEvent event) {
        timeLineController.getParents().add(new TimeLineParent(tweet.getId(), TimeLineParents.TWEET));
        timeLineController.setT(0);
        exploreController.setT(0);
        timeLineController.reload();
    }

    private final Set<CategoryDto> selectedCategories = new HashSet<CategoryDto>();
    private final Set<RoomDto> selectedRooms = new HashSet<RoomDto>();

    @FXML
    void likeButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(TweetControllerService.class).likeButtonAction(tweet.getId(), currentPersonId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {

            }
        });
    }

    @FXML
    void muteWriterButtonAction(MouseEvent event) {
        timeLineController.setT(0);
        exploreController.setT(0);
        WebUtil.makeMute(currentPersonId, tweet.getPersonWhoMadeThis().getId());
    }

    @FXML
    void personalPageButtonAction(MouseEvent event) {
        ModelAccess.personToPersonalPageController = tweet.getPersonWhoMadeThis();
        Stage stage = ViewUtility.getNewStage(personalPageButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("personalPage"));
        Scene scene = ViewFactory.viewFactory.getPersonalPageScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void reTweetButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(TweetServiceControllerService.class).makeTweet(tweet.getText(), tweet.getId(), currentPersonId, tweet.getPicture()==null?-1:tweet.getPicture().getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    @FXML
    void reportButtonAction(MouseEvent event) {
        timeLineController.setT(0);
        exploreController.setT(0);
        WebUtil.report(tweet.getId(), currentPersonId);
    }

    @FXML
    void saveTweetButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(TweetControllerService.class).saveTweetButtonAction(tweet.getId(), currentPersonId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    @FXML
    void tweetAreaAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tweet = WebUtil.getTweet(ModelAccess.tweetIdToTweetController);
        } catch (IOException e) {
            return;
        }
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    Platform.runLater(() -> reload());
                    Thread.sleep(5000);
                }
            }
        });
//        thread.setDaemon(true);
        thread.start();
//        super.initialize(location, resources);
        textArea.setEditable(false);
    }

    @SneakyThrows
    private void setLiked(){
        File file = ResourceUtils.getFile(ConfigInstance.getInstance().getProperty("like_icon"));
        Image image = new Image(file.toURI().toString());
        likeImage.setImage(image);
        numberOfLikeLabel.setText(Integer.toString(tweet.getWhoLiked().size()));
    }

    @SneakyThrows
    private void setUnLiked(){

        File file = ResourceUtils.getFile(ConfigInstance.getInstance().getProperty("unlike_icon"));
        Image image = new Image(file.toURI().toString());
        likeImage.setImage(image);
        numberOfLikeLabel.setText(Integer.toString(tweet.getWhoLiked().size()));
    }

    @Override
    public void reload() {
        try {
            tweet = WebUtil.getTweet(tweet.getId());
        } catch (IOException e) {
            return;
        }
        if(tweet.getPersonWhoMadeThis().getPicture() != null){
            profileImage.setImage(ViewUtility.getPicture(tweet.getPersonWhoMadeThis().getPicture().getId()));
        }
        if(tweet.getPicture() != null){
            tweetImage.setImage(ViewUtility.getPicture(tweet.getPicture().getId()));
        }
        else{
            tweetImage.setFitWidth(0);
            tweetImage.setFitHeight(0);
        }
        textArea.setText(tweet.getPersonWhoMadeThis().getUserName() + ": " + tweet.getText());
        boolean isLiked = false;
        for(PersonDto personDto:tweet.getWhoLiked()){
            if(personDto.getId() == currentPersonId){
                isLiked = true;
            }
        }
        if(isLiked){
            setLiked();
        }
        else{
            setUnLiked();
        }
    }
}
