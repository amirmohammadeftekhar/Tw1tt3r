package controller;

import dtos.CategoryDto;
import dtos.PersonDto;
import dtos.RoomDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Setter;
import view.ViewFactory;
import view.ViewUtility;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ChoosingMenuController extends AbstractController implements Initializable {


    @Setter
    private Set<PersonDto> selectedPeople = new HashSet<PersonDto>();
    @Setter
    private Set<CategoryDto> selectedCategories = new HashSet<CategoryDto>();
    @Setter
    private Set<RoomDto> selectedRooms = new HashSet<RoomDto>();
    @Setter
    private Set<PersonDto> peopleToChoose = new HashSet<PersonDto>();
    @Setter
    private Set<CategoryDto> categoriesToChoose = new HashSet<CategoryDto>();
    @Setter
    private Set<RoomDto> roomsToChoose = new HashSet<RoomDto>();

    @FXML
    private GridPane gridPane;

    @FXML
    private Button doneButton;

    @FXML
    void doneButtonAction(MouseEvent event) {
        Stage stage = (Stage)doneButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void reload() {
        int t=0;
        for(RoomDto room:roomsToChoose){
            Parent parent = ViewFactory.viewFactory.GetRoomOutViewParent(room, -1);
            if(selectedRooms.contains(room)){
                ViewUtility.makeSelected((HBox) parent);
            }
            else{
                ViewUtility.makeUnSelected((HBox) parent);
            }
            parent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if(selectedRooms.contains(room)){
                    ViewUtility.makeUnSelected((HBox) parent);
                    selectedRooms.remove(room);
                }
                else{
                    ViewUtility.makeSelected((HBox) parent);
                    selectedRooms.add(room);
                }
            });
            gridPane.add(parent, 0, ++t);
        }

        for(CategoryDto category:categoriesToChoose){
            Parent parent = ViewFactory.viewFactory.GetRoomOutViewParent(category);
            if(selectedCategories.contains(category)){
                ViewUtility.makeSelected((HBox) parent);
            }
            else{
                ViewUtility.makeUnSelected((HBox) parent);
            }
            parent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if(selectedCategories.contains(category)){
                    ViewUtility.makeUnSelected((HBox) parent);
                    selectedCategories.remove(category);
                }
                else{
                    ViewUtility.makeSelected((HBox) parent);
                    selectedCategories.add(category);
                }
            });
            gridPane.add(parent, 0, ++t);
        }

        for(PersonDto person:peopleToChoose){
            Parent parent = ViewFactory.viewFactory.GetPersonOutViewParent(person);
            if(selectedPeople.contains(person)){
                ViewUtility.makeSelected((HBox) parent);
            }
            else{
                ViewUtility.makeUnSelected((HBox) parent);
            }
            parent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if(selectedPeople.contains(person)){
                    ViewUtility.makeUnSelected((HBox) parent);
                    selectedPeople.remove(person);
                }
                else{
                    ViewUtility.makeSelected((HBox) parent);
                    selectedPeople.add(person);
                }
            });
            gridPane.add(parent, 0, ++t);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
        reload();
    }
}
