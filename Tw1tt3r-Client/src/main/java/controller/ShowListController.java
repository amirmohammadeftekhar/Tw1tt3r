package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.enums.ShowListChoices;
import dtos.PersonDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.ViewFactory;
import view.ViewUtility;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowListController extends AbstractController implements Initializable {

    @FXML
    private Label showListIdentityLabel;

    @FXML
    private GridPane listGridPane;

    private ShowListChoices showListChoice;

    private List<PersonDto> personList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
        this.showListChoice = ModelAccess.showListChoice;
        this.personList = ModelAccess.peopleListToShow;
        showListIdentityLabel.setText(showListChoice.toString());
        int t = 0;
        for(PersonDto person: personList){
            Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
            parent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                ModelAccess.personToPersonalPageController = person;
                Scene scene = ViewFactory.viewFactory.getPersonalPageScene();
                Stage stage = ViewUtility.getNewStage(listGridPane.getScene().getWindow(), ConfigInstance.getInstance().getProperty("personalPage"));
                stage.setScene(scene);
                stage.show();
            });
            listGridPane.add(parent, 0, ++t);
        }
    }

    @Override
    public void reload() {

    }
}
