package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoadingPopup extends Group {

    /**
     * A popup with a loading animation.
     */
    public LoadingPopup() {
        VBox container = new VBox();
        container.getStyleClass().add("popup-bg");
        container.setPadding(new Insets(16, 0, 16, 0));

        Image image = new Image("images/loading_ripple.gif");
        ImageView loadingAnimation = new ImageView(image);
        container.getChildren().add(loadingAnimation);

        getChildren().add(container);
    }
}
