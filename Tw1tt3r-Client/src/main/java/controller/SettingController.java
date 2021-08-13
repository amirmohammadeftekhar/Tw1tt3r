package controller;

import config.ConfigInstance;
import controller.utility.ModelAccess;
import controller.utility.WebUtil;
import database.DataBaseUtil;
import dtos.*;
import entities.enums.LastSeenType;
import entity.SettingEntity;
import javafx.application.Platform;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utility.ModelMapperInstance;
import view.ViewFactory;
import view.ViewObjects;
import view.ViewUtility;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.SettingControllerService;
import web.serviceinterfaces.services.ActionServiceControllerService;

import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
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
    private Button privacyUpdateButton;

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
        reloadData();
    }

    private void clearCategoryMakingFields(){
        selectedPeopleFromCategoryCreating.clear();
        categoryMakingNameField.clear();
        reloadData();
    }

    @FXML
    void categoryCreateButtonAction(MouseEvent event) throws InterruptedException {
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
        reloadData();
    }

    @FXML
    void categoryCreateChoosingButtonAction(MouseEvent event) throws InterruptedException {
        Stage stage = ViewUtility.getNewStage(categoryCreateButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("choosingWindow"));
        ViewObjects viewObjects = ViewFactory.viewFactory.getChoosingMenuViewObjects();
        ChoosingMenuController controller = (ChoosingMenuController) viewObjects.getAbstractController();
        try {
            ((ChoosingMenuController)controller).setPeopleToChoose(new HashSet<>(
                    TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowingsPersons(currentPersonId).execute().body()
            ));
        } catch (IOException e) {
            return;
        }
        ((ChoosingMenuController)controller).setSelectedPeople(selectedPeopleFromCategoryCreating);
        ((ChoosingMenuController)controller).reload();
        Scene scene = viewObjects.getScene();
        stage.setScene(scene);
        stage.show();
        Thread.sleep(1000);
        reloadData();

    }

    @FXML
    void roomCreateButtonAction(MouseEvent event) throws InterruptedException {
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
        reloadData();
    }

    @FXML
    void roomCreateChoosingButtonAction(MouseEvent event) throws InterruptedException {
        Stage stage = ViewUtility.getNewStage(roomCreateButton.getScene().getWindow(), ConfigInstance.getInstance().getProperty("choosingWindow"));
        ViewObjects viewObjects = ViewFactory.viewFactory.getChoosingMenuViewObjects();
        ChoosingMenuController controller = (ChoosingMenuController) viewObjects.getAbstractController();
        try {
            ((ChoosingMenuController)controller).setPeopleToChoose(new HashSet<>(
                    TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).getFollowingsPersons(currentPersonId).execute().body()
            ));
        } catch (IOException e) {
            return;
        }
        ((ChoosingMenuController)controller).setSelectedPeople(selectedPeopleFromRoomCreating);
        ((ChoosingMenuController)controller).reload();
        Scene scene = viewObjects.getScene();
        stage.setScene(scene);
        stage.show();
        Thread.sleep(1000);
        reloadData();
    }

    private final FileChooser fileChooser = new FileChooser();

    private PictureDto picture;

    @FXML
    void changeProfileImageAction(MouseEvent event) throws InterruptedException {
        File file = fileChooser.showOpenDialog(updateProfileButton.getScene().getWindow());
        if(!isImage(file)){
            return;
        }
        try {
            picture = makePicture(file);
        } catch (IOException e) {
            return;
        }
        TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).changePicture(currentPersonId, picture).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
        Thread.sleep(1000);
        reloadData();

    }

    @FXML
    void updateProfileButtonAction(MouseEvent event) throws InterruptedException {
        PersonIniDto person = new PersonIniDto();
        person.setFirstname(firstNameFiled.getText());
        person.setLastName(lastNameField.getText());
        person.setUserName(userNameField.getText());
        person.setEmailAddress(emailField.getText());
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
        reloadData();

    }

    @FXML
    void privacyUpdateButtonAction(MouseEvent event) throws InterruptedException {
        Session session = DataBaseUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        SettingEntity settingEntity = new SettingEntity();
        settingEntity.setPrivate(privateAccountField.isSelected());
        settingEntity.setPassword(passwordField.getText());
        settingEntity.setLastSeenType(lastSeenTypeChoiceBox.getValue());
        settingEntity.setTimestamp(getTimeStamp());
        settingEntity.setPersonId(currentPersonId);
        session.save(settingEntity);
        transaction.commit();
        session.close();
        Thread.sleep(1000);
        reloadData();
    }

    @FXML
    void deactivateButtonAction(MouseEvent event) throws InterruptedException {
        Session session = DataBaseUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        SettingEntity settingEntity = new SettingEntity();
        settingEntity.setTimestamp(getTimeStamp());
        settingEntity.setPersonId(currentPersonId);
        settingEntity.setToDeactivate(true);
        session.save(settingEntity);
        transaction.commit();
        session.close();
        Thread.sleep(1000);
        reloadData();
    }

    @FXML
    void deleteAccountAction(MouseEvent event) throws InterruptedException {
        Session session = DataBaseUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        SettingEntity settingEntity = new SettingEntity();
        settingEntity.setTimestamp(getTimeStamp());
        settingEntity.setPersonId(currentPersonId);
        settingEntity.setToDelete(true);
        session.save(settingEntity);
        transaction.commit();
        session.close();
        Thread.sleep(1000);
        reloadData();

    }

    @FXML
    void messageSendingButtonAction(MouseEvent event) throws InterruptedException {
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
        reloadData();
    }

    @FXML
    void logOutButtonAction(MouseEvent event) throws InterruptedException {
        Scene scene = ViewFactory.viewFactory.getEnteringScene();
        Stage stage = ViewFactory.viewFactory.getStage();
        stage.setScene(scene);
        Thread.sleep(1000);
        reloadData();

    }

    @FXML
    void addPersonToCategoryButtonAction(MouseEvent event) throws InterruptedException {
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
        reloadData();
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
        reloadData();
    }

    @FXML
    void deleteCategoryButtonAction(MouseEvent event) throws InterruptedException {
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
        reloadData();
    }

    @FXML
    void removePersonFromCategoryButtonAction(MouseEvent event) throws InterruptedException {
        CategoryDto category = choosingCategoryComboBox.getValue();
        if(category==null){
            return;
        }
        choosingCategoryComboBox.setValue(null);
        selectedPeopleFromCategoryManaging.clear();
        Thread.sleep(1000);
        reloadData();
    }

    @Override
    public void reload() {
        Session session1 = DataBaseUtil.getSessionFactory().openSession();
        String hql = "SELECT s FROM SettingEntity s order by s.timestamp asc";
        Query query = session1.createQuery(hql);
        List<SettingEntity> list = query.getResultList();
        session1.close();
        for(SettingEntity settingEntity:list){
            TransactionServiceGenerator.getInstance().createService(SettingControllerService.class).privacyUpdate(
                    ModelMapperInstance.getModelMapper().map(settingEntity, SettingEntityDto.class)).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Session session2 = DataBaseUtil.getSessionFactory().openSession();
                    Transaction transaction = session2.getTransaction();
                    transaction.begin();
                    session2.delete(settingEntity);
                    transaction.commit();
                    session2.close();
                    if(settingEntity.isToDeactivate() || settingEntity.isToDelete()){
                        Platform.runLater(() -> mainMenuController.exitButtonAction(null));
                    }
                    Platform.runLater(() -> reloadData());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {

                }
            });
        }
    }

    public void reloadData(){
        PersonDto currentPerson = null;
        try {
            currentPerson = WebUtil.getPerson(currentPersonId);
        } catch (IOException e) {
            return;
        }

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
        reloadData();
        super.initialize(location, resources);
    }
}
