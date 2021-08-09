package controller;

import controller.utility.ModelAccess;
import dtos.PictureDto;
import dtos.TweetDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.services.TweetServiceControllerService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.currentPersonId;
import static view.ViewUtility.isImage;
import static view.ViewUtility.makePicture;

public class TweetMakingController extends AbstractController implements Initializable {

    private TweetDto parentTweet;

    @FXML
    private TextArea tweetTextArea;

    @FXML
    private Button uploadButton;

    @FXML
    private Button createButton;

    private PictureDto picture;

    @FXML
    void createButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(TweetServiceControllerService.class).makeTweet(
                tweetTextArea.getText(), parentTweet==null?-1:parentTweet.getId(), currentPersonId, picture==null?new PictureDto():picture).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    void uploadButtonAction(MouseEvent event) {
        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if(!isImage(file)){
            return;
        }
        picture = makePicture(file);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
        this.parentTweet = ModelAccess.parentTweetToTweetMakingController;
    }

    @Override
    public void reload() {

    }
}
