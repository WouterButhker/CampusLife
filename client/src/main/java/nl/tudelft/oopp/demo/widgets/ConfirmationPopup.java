package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ConfirmationPopup extends Group {

    /**
     * A popup with text and a confirm and cancel button.
     * @param text the text of the popup
     * @param listener the listener with a callback
     */
    public ConfirmationPopup(String title, String text, Listener listener) {
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
        buttonsContainer.setSpacing(32);
        buttonsContainer.setAlignment(Pos.CENTER);
        Button confirmButton = new Button("Confirm");
        confirmButton.getStyleClass().add("popup-button");
        confirmButton.setPadding(new Insets(8));
        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listener.onConfirmClicked();
            }
        });
        buttonsContainer.getChildren().add(confirmButton);

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("popup-button");
        cancelButton.setPadding(new Insets(8));
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listener.onCancelClicked();
            }
        });
        buttonsContainer.getChildren().add(cancelButton);
        container.getChildren().add(buttonsContainer);

        getChildren().add(container);
    }

    public interface Listener {
        void onConfirmClicked();

        void onCancelClicked();
    }
}
