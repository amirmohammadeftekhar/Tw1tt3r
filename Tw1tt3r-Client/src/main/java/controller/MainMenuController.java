package controller;

import controller.utility.WebUtil;
import controller.utility.enums.MainMenuItems;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import view.ViewFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static controller.utility.ModelAccess.currentPerson;
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
    protected void reload() {
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
        currentPerson = WebUtil.getPerson(currentPersonId);
        items.put(MainMenuItems.TIMELINE, ViewFactory.viewFactory.getTimeLineParent());
        items.put(MainMenuItems.MESSAGING, ViewFactory.viewFactory.getMessagingMainMenuParent());
        items.put(MainMenuItems.PROFILE, ViewFactory.viewFactory.getProfileParent());
        items.put(MainMenuItems.EXPLORE, ViewFactory.viewFactory.getExploreParent());
        items.put(MainMenuItems.SETTING, ViewFactory.viewFactory.getSettingParent());
        reload();
    }
}
