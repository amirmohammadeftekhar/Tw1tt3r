package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import dtos.CategoryDto;
import dtos.PersonDto;
import dtos.PersonIniDto;
import dtos.PictureDto;
import entities.enums.LastSeenType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.ViewFactory;
import view.ViewObjects;
import view.ViewUtility;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.SettingControllerService;
import web.serviceinterfaces.services.ActionServiceControllerService;

import java.io.File;
import java.net.URL;
import java.util.*;

import static controller.utility.ModelAccess.currentPersonId;
import static controller.utility.ModelAccess.mainMenuController;
import static view.ViewUtility.isImage;
import static view.ViewUtility.makePicture;

public class SettingController extends AbstractController implements Initializable {


    private final Set<PersonDto> selectedPeopleFromRoomCreating = new HashSet<PersonDto>();
    private final Set<PersonDto> selectedPeopleFromCategoryCreating = new HashSet<PersonDto>();

    @FXML
    private TextField roomMakingNameField;

    @FXML
    private CheckBox pvCheckBox;

    @FXML
    private HBox roomCreateChoosingButton;

    @FXML
    private Button roomCreateButton;

    @FXML
    private TextField categoryMakingNameField;

    @FXML
    private HBox categoryCreateChoosingButton;

    @FXML
    private ComboBox<CategoryDto> choosingCategoryComboBox;

    @FXML
    private Button categoryCreateButton;

    @FXML
    private HBox categoryManagingChooserButton;

    @FXML
    private Button deleteCategoryButton;

    @FXML
    private Button addPersonToCategoryButton;

    @FXML
    private Button removePersonFromCategoryButton;

    @FXML
    private TextField firstNameFiled;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showEmailCheckBox;

    @FXML
    private CheckBox privateAccountField;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button updateProfileButton;

    @FXML
    private ChoiceBox<LastSeenType> lastSeenTypeChoiceBox;

    @FXML
    private Button messageSendingButton;

    @FXML
    private Button LastSeenTypeUpdateButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button deactivateButton;

    @FXML
    private Button logOutButton;

    private void clearGroupMakingFields(){
        selectedPeopleFromRoomCreating.clear();
        pvCheckBox.setSelected(false);
        roomMakingNameField.clear();
        reload();
    }

    private void clearCategoryMakingFields(){
        selectedPeopleFromCategoryCreating.clear();
        categoryMakingNameField.clear();
        reload();
    }

    @SneakyThrows
    @FXML
    void categoryCreateButtonAction(MouseEvent event) {
        List<Integer> peopleToAdd = new LinkedList<Integer>();
        for(PersonDto person:selectedPeopleFromCategoryCreating) peopleToAdd.add(person.getId());
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).categoryCreateButtonAction(categoryMakingNameField.getText(), currentPersonId, peopleToAdd).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        clearCategoryMakingFields();
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void categoryCreateChoosingButtonAction(MouseEvent event) {
        Stage stage = ViewUtility.getNewStage(categoryCreateButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("choosingWindow"));
        ViewObjects viewObjects = ViewFactory.viewFactory.getChoosingMenuViewObjects();
        ChoosingMenuController controller = (ChoosingMenuController) viewObjects.getAbstractController();
        ((ChoosingMenuController)controller).setPeopleToChoose(new HashSet<>(
                TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowingsPersons(currentPersonId).execute().body()
        ));
        ((ChoosingMenuController)controller).setSelectedPeople(selectedPeopleFromCategoryCreating);
        ((ChoosingMenuController)controller).reload();
        Scene scene = viewObjects.getScene();
        stage.setScene(scene);
        stage.show();
        Thread.sleep(1000);
        reload();

    }

    @SneakyThrows
    @FXML
    void roomCreateButtonAction(MouseEvent event) {
        List<Integer> peopleToAdd = new LinkedList<Integer>();
        for(PersonDto person:selectedPeopleFromRoomCreating) peopleToAdd.add(person.getId());
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).roomCreateButtonAction(roomMakingNameField.getText(), currentPersonId, pvCheckBox.isSelected(), peopleToAdd).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        clearGroupMakingFields();
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void roomCreateChoosingButtonAction(MouseEvent event) {
        Stage stage = ViewUtility.getNewStage(roomCreateButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("choosingWindow"));
        ViewObjects viewObjects = ViewFactory.viewFactory.getChoosingMenuViewObjects();
        ChoosingMenuController controller = (ChoosingMenuController) viewObjects.getAbstractController();
        ((ChoosingMenuController)controller).setPeopleToChoose(new HashSet<>(
                TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowingsPersons(currentPersonId).execute().body()
        ));
        ((ChoosingMenuController)controller).setSelectedPeople(selectedPeopleFromRoomCreating);
        ((ChoosingMenuController)controller).reload();
        Scene scene = viewObjects.getScene();
        stage.setScene(scene);
        stage.show();
        Thread.sleep(1000);
        reload();
    }

    private final FileChooser fileChooser = new FileChooser();


    @SneakyThrows
    @FXML
    void changeProfileImageAction(MouseEvent event) {
        File file = fileChooser.showOpenDialog(updateProfileButton.getScene().getWindow());
        if(!isImage(file)){
            return;
        }
        PictureDto picture = makePicture(file);
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).changePicture(currentPersonId, picture).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reload();

    }

    @SneakyThrows
    @FXML
    void updateProfileButtonAction(MouseEvent event) {
        PersonIniDto person = new PersonIniDto();
        person.setFirstname(firstNameFiled.getText());
        person.setLastName(lastNameField.getText());
        person.setUserName(userNameField.getText());
        person.setPassword(passwordField.getText());
        person.setEmailAddress(emailField.getText());
        person.setPrivate(privateAccountField.isSelected());
        person.setToShowEmail(showEmailCheckBox.isSelected());
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).updateProfileButtonAction(currentPersonId, person).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reload();

    }

    @SneakyThrows
    @FXML
    void LastSeenTypeUpdateButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).lastSeenTypeUpdateButtonAction(currentPersonId, lastSeenTypeChoiceBox.getValue()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void deactivateButtonAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).deactivateButtonAction(currentPersonId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        mainMenuController.exitButtonAction(null);
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void messageSendingButtonAction(MouseEvent event) {
        CategoryDto category = choosingCategoryComboBox.getValue();
        if(category==null){
            return;
        }
        ModelAccess.categoryToCategoryMessaging = category;
        Stage stage = ViewUtility.getNewStage(messageSendingButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("categoryMessaging"));
        Scene scene = ViewFactory.viewFactory.getCategoryMessagingScene();
        stage.setScene(scene);
        stage.show();
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void deleteAccountAction(MouseEvent event) {
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).deleteAccountAction(currentPersonId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        mainMenuController.exitButtonAction(null);
        Thread.sleep(1000);
        reload();

    }

    @SneakyThrows
    @FXML
    void logOutButtonAction(MouseEvent event) {
        Scene scene = ViewFactory.viewFactory.getEnteringScene();
        Stage stage = ViewFactory.viewFactory.getStage();
        stage.setScene(scene);
        Thread.sleep(1000);
        reload();

    }

    @SneakyThrows
    @FXML
    void addPersonToCategoryButtonAction(MouseEvent event) {
        if(choosingCategoryComboBox.getValue()==null){
            return;
        }
        List<Integer> peopleToAdd = new LinkedList<Integer>();
        for(PersonDto person:selectedPeopleFromCategoryManaging) peopleToAdd.add(person.getId());
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).addPersonToCategoryButtonAction(choosingCategoryComboBox.getValue().getId(), peopleToAdd).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        choosingCategoryComboBox.setValue(null);
        selectedPeopleFromCategoryManaging.clear();
        Thread.sleep(1000);
        reload();
    }

    private final Set<PersonDto> selectedPeopleFromCategoryManaging = new HashSet<PersonDto>();
    @SneakyThrows
    @FXML
    void categoryManagingChooserButtonAction(MouseEvent event) {
        Stage stage = ViewUtility.getNewStage(categoryCreateButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("choosingWindow"));
        ViewObjects viewObjects = ViewFactory.viewFactory.getChoosingMenuViewObjects();
        ChoosingMenuController controller = (ChoosingMenuController) viewObjects.getAbstractController();
        ((ChoosingMenuController)controller).setPeopleToChoose(new HashSet<>(
                TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowingsPersons(currentPersonId).execute().body()
        ));
        ((ChoosingMenuController)controller).setSelectedPeople(selectedPeopleFromCategoryManaging);
        ((ChoosingMenuController)controller).reload();
        Scene scene = viewObjects.getScene();
        stage.setScene(scene);
        stage.show();
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void deleteCategoryButtonAction(MouseEvent event) {
        CategoryDto category = choosingCategoryComboBox.getValue();
        choosingCategoryComboBox.setValue(null);
        if(category==null){
            return;
        }
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).deleteCategoryButtonAction(currentPersonId, category.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reload();
    }

    @SneakyThrows
    @FXML
    void removePersonFromCategoryButtonAction(MouseEvent event) {
        CategoryDto category = choosingCategoryComboBox.getValue();
        if(category==null){
            return;
        }
        choosingCategoryComboBox.setValue(null);
        selectedPeopleFromCategoryManaging.clear();
        Thread.sleep(1000);
        reload();
    }

    @Override
    public void reload() {
        PersonDto currentPerson = WebUtil.getPerson(currentPersonId);

        firstNameFiled.setText(currentPerson.getFirstname());
        lastNameField.setText(currentPerson.getLastName());
        userNameField.setText(currentPerson.getUserName());
//        passwordField.setText(currentPerson.getPassword());
        emailField.setText(currentPerson.getEmailAddress());
        if(currentPerson.getPicture() != null){
            profileImage.setImage(ViewUtility.getPicture(currentPerson.getPicture().getId()));
        }
        privateAccountField.setSelected(currentPerson.isPrivate());
        showEmailCheckBox.setSelected(currentPerson.isToShowEmail());
        lastSeenTypeChoiceBox.setItems(FXCollections.observableArrayList(LastSeenType.values()));
        lastSeenTypeChoiceBox.getSelectionModel().select(currentPerson.getLastSeenType());
        choosingCategoryComboBox.setItems(FXCollections.observableArrayList(currentPerson.getOwnedCategories()));
        choosingCategoryComboBox.setConverter(new StringConverter<CategoryDto>() {
            @Override
            public String toString(CategoryDto object) {
                return(object==null?null: object.getName());
            }

            @Override
            public CategoryDto fromString(String string) {
                return null;
            }
        });
        if(mainMenuController!=null){
            mainMenuController.reload();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
     //   super.initialize(location, resources);
    }
}
