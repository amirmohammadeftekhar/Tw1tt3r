package controller;

import controller.utility.ModelAccess;
import dtos.TweetDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utility.TimeLineParent;
import view.ViewFactory;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.TimeLineControllerService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import static controller.utility.ModelAccess.currentPersonId;

public class TimeLineController extends AbstractController implements Initializable {

    @Getter
    @Setter
    private Stack<TimeLineParent> parents = new Stack<TimeLineParent>();


    @FXML
    private HBox backButton;

    @FXML
    private GridPane tweetGridPane;

    @Setter
    public int t = 0;

    @SneakyThrows
    @Override
    public void reload(){

        if(t==0){
            tweetGridPane.getChildren().clear();
        }
        List<TweetDto> tweetList;
        if(parents.empty()){
            tweetList = TransactionServiceGenerator.getInstance().createService(TimeLineControllerService.class).getTweetList(currentPersonId, new TimeLineParent(), t).execute().body();
        }
        else{
            tweetList = TransactionServiceGenerator.getInstance().createService(TimeLineControllerService.class).getTweetList(currentPersonId, parents.peek(), t).execute().body();
        }
        for(TweetDto tweet:tweetList){
            ModelAccess.tweetIdToTweetController = tweet.getId();
            Parent parent = ViewFactory.viewFactory.getTweetParent();
            tweetGridPane.add(parent, 0, ++t);
            GridPane.setMargin(parent, new Insets(10));
        }
    }

    @FXML
    void backButtonAction(MouseEvent event) {
        if(parents.size()>0){
            parents.pop();
            setT(0);
            reload();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        reload();
        super.initialize(location, resources);
    }
}
