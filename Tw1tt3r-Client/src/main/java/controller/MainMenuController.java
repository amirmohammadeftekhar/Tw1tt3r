package controller;

import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import controller.utility.enums.MainMenuItems;
import dtos.PersonDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import view.ViewFactory;
import view.ViewObjects;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.currentPersonId;

public class MainMenuController extends AbstractController implements Initializable {


    private final Map<MainMenuItems, Parent> items = new HashMap<MainMenuItems, Parent>();

    @FXML
    private Label nameLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private HBox exploreButton;

    @FXML
    private HBox messagingButton;

    @FXML
    private HBox profileButton;

    @FXML
    private HBox settingButton;

    @FXML
    private HBox homeButton;

    @FXML
    private HBox exitButton;

    @FXML
    private BorderPane MainWindowPane;

    @Override
    public void reload() {
        PersonDto currentPerson = WebUtil.getPerson(currentPersonId);
        nameLabel.setText(currentPerson.getFirstname() + " " + currentPerson.getLastName());
        userNameLabel.setText("@" + currentPerson.getUserName());
    }

    @FXML
    void exitButtonAction(MouseEvent event) {
        ViewFactory.viewFactory.getStage().close();
    }

    @FXML
    void exploreButtonAction(MouseEvent event) {
        MainWindowPane.setCenter(items.get(MainMenuItems.EXPLORE));
    }

    @FXML
    void homeButtonAction(MouseEvent event) {
        MainWindowPane.setCenter(items.get(MainMenuItems.TIMELINE));
    }

    @FXML
    void messageButtonAction(MouseEvent event) {
        MainWindowPane.setCenter(items.get(MainMenuItems.MESSAGING));
    }

    @FXML
    void profileButtonAction(MouseEvent event) {
        MainWindowPane.setCenter(items.get(MainMenuItems.PROFILE));
    }

    @FXML
    void settingButtonAction(MouseEvent event) {
        MainWindowPane.setCenter(items.get(MainMenuItems.SETTING));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        ViewObjects viewObjects;

        viewObjects = ViewFactory.viewFactory.getTimeLineViewObjects();
        ModelAccess.timeLineController = (TimeLineController) viewObjects.getAbstractController();
        items.put(MainMenuItems.TIMELINE, viewObjects.getParent());

        viewObjects = ViewFactory.viewFactory.getMessagingMainMenuViewObjects();
        ModelAccess.messagingMainMenuController = (MessagingMainMenuController) viewObjects.getAbstractController();
        items.put(MainMenuItems.MESSAGING, viewObjects.getParent());

        items.put(MainMenuItems.PROFILE, ViewFactory.viewFactory.getProfileParent());

        viewObjects = ViewFactory.viewFactory.getExploreViewObjects();
        ModelAccess.exploreController = (ExploreController) viewObjects.getAbstractController();
        items.put(MainMenuItems.EXPLORE, viewObjects.getParent());

/*
        items.put(MainMenuItems.SETTING, ViewFactory.viewFactory.getSettingParent());
*/
        super.initialize(location, resources);
    }
}
