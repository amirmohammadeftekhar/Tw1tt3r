package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import dtos.PersonDto;
import dtos.TweetDto;
import dtos.TweetListDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;
import utility.TimeLineParent;
import view.ViewFactory;
import view.ViewUtility;
import web.BaseResponse;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.ExploreControllerService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import static controller.utility.ModelAccess.currentPersonId;

public class ExploreController extends AbstractController implements Initializable {

    @Getter
    @Setter
    private Stack<TimeLineParent> parents = new Stack<TimeLineParent>();

    @FXML
    private HBox backButton;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane tweetGridPane;

    @FXML
    void backButtonAction(MouseEvent event) {
        if(parents.size()>0){
            parents.pop();
            reload();
        }
    }

    @FXML
    void searchButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(ExploreControllerService.class).searchButtonAction(searchField.getText(), currentPersonId)
                .enqueue(new TransactionCallBack<BaseResponse>() {
                    @Override
                    public void DoOnResponse(Response<BaseResponse> response) {
                        BaseResponse baseResponse = response.body();
                        PersonDto person = (PersonDto) baseResponse.getDto();
                        ModelAccess.personToPersonalPageController = person;
                        Stage stage = ViewUtility.getNewStage(backButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("personalPage"));
                        Scene scene = ViewFactory.viewFactory.getPersonalPageScene();
                        stage.setScene(scene);
                        stage.show();
                    }
                });
    }


    @Override
    public void reload() {
        tweetGridPane.getChildren().clear();
        TransactionServiceGenerator.getInstance().createService(ExploreControllerService.class)
                .getTweetList(currentPersonId, parents.empty()?new TimeLineParent():parents.peek()).enqueue(new TransactionCallBack<BaseResponse>() {
            @Override
            public void DoOnResponse(Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                List<TweetDto> tweetList = ((TweetListDto)baseResponse.getDto()).getTweetList();
                int t = 0;
                for(TweetDto tweet:tweetList){
                    ModelAccess.tweetToTweetController = tweet;
                    Parent parent = ViewFactory.viewFactory.getTweetParent();
                    tweetGridPane.add(parent, 0, ++t);
                    GridPane.setMargin(parent, new Insets(10));
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);
    }
}
