package view;

import controller.AbstractController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewObjects {
    private AbstractController abstractController;
    private Scene scene;
    private Parent parent;

    ViewObjects(AbstractController abstractController, Scene scene, Parent parent) {
        this.abstractController = abstractController;
        this.scene = scene;
        this.parent = parent;
    }
}