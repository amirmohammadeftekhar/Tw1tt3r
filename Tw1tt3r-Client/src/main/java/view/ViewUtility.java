package view;

import config.ConfigInstance;
import dtos.PictureDto;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.services.PictureServiceControllerService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ViewUtility {

    public static void makeSelected(VBox button){
        button.getStyleClass().removeAll(ConfigInstance.getInstance().getProperty("mouseMoved"));
        button.getStyleClass().add(ConfigInstance.getInstance().getProperty("selected"));
    }

    public static void makeSelected(HBox button){
        button.getStyleClass().removeAll(ConfigInstance.getInstance().getProperty("mouseMoved"));
        button.getStyleClass().add((ConfigInstance.getInstance().getProperty("selected")));
    }

    public static void makeUnSelected(HBox button){
        button.getStyleClass().removeAll(ConfigInstance.getInstance().getProperty("selected"));
        button.getStyleClass().add(ConfigInstance.getInstance().getProperty("mouseMoved"));
    }

    public static void makeUnSelected(VBox button){
        button.getStyleClass().removeAll(ConfigInstance.getInstance().getProperty("selected"));
        button.getStyleClass().add(ConfigInstance.getInstance().getProperty("mouseMoved"));
    }

    public static void makeStageAtCenter(Stage stage){
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        stage.setX(bounds.getWidth() - stage.getWidth() / 2);
        stage.setY(bounds.getHeight() - stage.getHeight() / 2);
    }

    public static Stage getNewStage(Window window, String name){
        Stage stage = new Stage();
        stage.setTitle(name);
/*
        stage.initModality(Modality.WINDOW_MODAL);
*/
        stage.initOwner(window);
        return(stage);
    }

    @SneakyThrows
    public static boolean isImage(File file){
        try{
            BufferedImage image = ImageIO.read(file);
            return(image != null);
        }
        catch (Exception e){
            return(false);
        }
    }

    public static PictureDto makePicture(File file) throws IOException {
        RequestBody fbody = RequestBody.create(file, MediaType.parse("image/*"));
        return(TransactionServiceGenerator.getInstance().createService(PictureServiceControllerService.class).makePicture(
                MultipartBody.Part.createFormData("file", file.getName(), fbody)
        ).execute().body());
    }

    @SneakyThrows
    public static Image getPicture(int pictureId){
        if(pictureId<=0){
            return(null);
        }
        ResponseBody responseBody = TransactionServiceGenerator.getInstance().createService(PictureServiceControllerService.class).getPicture(pictureId).execute().body();
        byte[] pictureByteArray = responseBody.bytes();
        return(new Image(new ByteArrayInputStream(pictureByteArray)));
    }
}













