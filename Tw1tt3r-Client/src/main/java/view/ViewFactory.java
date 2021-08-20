package view;

import config.ConfigInstance;
import controller.AbstractController;
import dtos.CategoryDto;
import dtos.MessageDto;
import dtos.PersonDto;
import dtos.RoomDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Date;

public class ViewFactory{

    public static ViewFactory viewFactory = new ViewFactory();

//    final PictureService pictureService;

    @Getter
    @Setter
    private Stage stage;

    public Scene getEnteringScene() {
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("entering_fxml"));
        return (viewObjects.getScene());
    }

    public Scene getSignInScene() {
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("signin_fxml"));
        return (viewObjects.getScene());
    }

    public Scene getSignUpScene() {
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("signup_fxml"));
        return (viewObjects.getScene());
    }

    public ViewObjects getMainMenuViewObjects() {
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("mainmenu_fxml"));
        return(viewObjects);
    }

    public Parent getTweetParent() {
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("tweet_fxml"));
        return (viewObjects.getParent());
    }

    public ViewObjects getTimeLineViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("timeline_fxml"));
        return(viewObjects);
    }

    public ViewObjects getExploreViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("explore_fxml"));
        return(viewObjects);
    }

    public ViewObjects getProfileViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("profile_fxml"));
        return(viewObjects);
    }

    public ViewObjects getMessagingMainMenuViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("messagingmainmenu_fxml"));
        return(viewObjects);
    }

    private void loadMessageParent(Parent messageParent, MessageDto message, File file){
        HBox userNameHBox = (HBox) messageParent.getChildrenUnmodifiable().get(0);
        Label userNameLabel = (Label)userNameHBox.getChildrenUnmodifiable().get(0);
        VBox messageVBox = (VBox) messageParent.getChildrenUnmodifiable().get(1);
        ImageView imageView = (ImageView) messageVBox.getChildrenUnmodifiable().get(0);
        TextArea messageTextArea = (TextArea) messageParent.getChildrenUnmodifiable().get(2);
        HBox messageHBox = (HBox) messageParent.getChildrenUnmodifiable().get(3);
        Label timeLabel = (Label) messageHBox.getChildrenUnmodifiable().get(0);
        Date date = new Date(message.getTimestamp().getTime());
        if(message.getSourcePerson()!=null){
            userNameLabel.setText(message.getSourcePerson().getUserName());
        }
        timeLabel.setText(message.getStatus().toString()+" "+date.toString());
        messageTextArea.setText(message.getText());
        if(file!=null){
            imageView.setImage(new Image(file.toURI().toString()));
        }
        else if(message.getPicture()!=null && message.getPicture().getId()>0){
            imageView.setImage(ViewUtility.getPicture(message.getPicture().getId()));
        }
        else{
            imageView.setFitHeight(0);
            imageView.setFitHeight(0);
        }
    }

    public Parent getMessageParent(MessageDto messageDto, File file){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("message_fxml"));
        loadMessageParent(viewObjects.getParent(), messageDto, file);
        return(viewObjects.getParent());
    }

    public ViewObjects getSettingViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("setting_fxml"));
        return(viewObjects);
    }

    private void loadRoomOutViewParent(RoomDto room, Parent parent, int unreadCount){
        ImageView imageView = (ImageView)parent.getChildrenUnmodifiable().get(0);
        Label nameLabel = (Label)parent.getChildrenUnmodifiable().get(1);
        Label unreadLabel = (Label)parent.getChildrenUnmodifiable().get(2);
        if(unreadCount!=-1){
            unreadLabel.setText(Integer.toString(unreadCount));
        }
        nameLabel.setText(room.getName());
    }

    private void loadRoomOutViewParent(CategoryDto category, Parent parent){
        ImageView imageView = (ImageView)parent.getChildrenUnmodifiable().get(0);
        Label label = (Label)parent.getChildrenUnmodifiable().get(1);
        label.setText(category.getName());
    }

    public Parent GetRoomOutViewParent(RoomDto room, int unreadCount){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("roomoutview_fxml"));

        loadRoomOutViewParent(room, viewObjects.getParent(), unreadCount);
        return(viewObjects.getParent());
    }

    public Parent GetRoomOutViewParent(CategoryDto category){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("roomoutview_fxml"));
        loadRoomOutViewParent(category, viewObjects.getParent());
        return(viewObjects.getParent());
    }

    public Scene getTweetMakingScene(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("tweetmaking_fxml"));
        return(viewObjects.getScene());
    }

    public Scene getCategoryMessagingScene(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("categorymessaging_fxml"));
        return(viewObjects.getScene());
    }

    public ViewObjects getRoomChatBoxViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("roomchatbox_fxml"));
        return(viewObjects);
    }

    private void loadPersonOutViewParent(Parent parent, PersonDto personDto){
        VBox imageVBox = (VBox) parent.getChildrenUnmodifiable().get(0);
        Label userNameLabel = (Label) parent.getChildrenUnmodifiable().get(1);
        ImageView imageView = (ImageView) imageVBox.getChildrenUnmodifiable().get(0);
        userNameLabel.setText(personDto.getUserName());
        if(personDto.getPicture()!=null && personDto.getPicture().getId()>0){
            imageView.setImage(ViewUtility.getPicture(personDto.getPicture().getId()));
        }
    }

    public Parent GetPersonOutViewParent(PersonDto personDto){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("personoutview_fxml"));
        loadPersonOutViewParent(viewObjects.getParent(), personDto);
        return(viewObjects.getParent());
    }

    public ViewObjects getChoosingMenuViewObjects(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("choosingmenu_fxml"));
        return(viewObjects);
    }

    public Scene getPersonalPageScene(){
        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("personalpage_fxml"));
        return(viewObjects.getScene());
    }

    public Scene getShowListScene(){

        ViewObjects viewObjects = initializeScene(ConfigInstance.getInstance().getProperty("showlist_fxml"));
        return(viewObjects.getScene());
    }


    @SneakyThrows
    private ViewObjects initializeScene(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        Parent parent = loader.load();
        AbstractController abstractController = loader.getController();
        Scene scene = new Scene(parent);
        return (new ViewObjects(abstractController, scene, parent));

    }

}