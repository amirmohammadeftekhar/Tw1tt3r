package view;

import config.ConfigInstance;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
}
