package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class InformationPopup extends Group {

    /**
     * A popup with text and an ok button.
     * @param text the text of the popup
     * @param listener the listener with a callback
     */
    public InformationPopup(String title, String text, Listener listener) {
        VBox container = new VBox();
        container.getStyleClass().add("popup-bg");
        container.setPadding(new Insets(16, 0, 16, 0));

        Text titleText = new Text("  " + title);
        titleText.getStyleClass().add("list-popup-title");
        container.getChildren().add(titleText);

        VBox bodyContainer = new VBox();
        bodyContainer.setPadding(new Insets(24));
        container.getChildren().add(bodyContainer);
        Text body = new Text(text);
        body.setWrappingWidth(300 - 48);
        body.getStyleClass().add("list-popup-body");
        bodyContainer.getChildren().add(body);

        HBox buttonsContainer = new HBox();
        buttonsContainer.setAlignment(Pos.CENTER);
        Button okButton = new Button("OK");
        okButton.getStyleClass().add("popup-button");
        okButton.setPadding(new Insets(8));
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listener.onOkClicked();
            }
        });
        buttonsContainer.getChildren().add(okButton);
        container.getChildren().add(buttonsContainer);

        getChildren().add(container);
    }

    public interface Listener {
        void onOkClicked();
    }
}
