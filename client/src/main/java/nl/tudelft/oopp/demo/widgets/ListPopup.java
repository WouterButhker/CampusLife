package nl.tudelft.oopp.demo.widgets;

import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ListPopup extends Group {

    /**
     * A popup with a list, useful for asking input from user.
     * @param title the title of the popup
     * @param items the list items
     * @param listener the listener with a callback
     */
    public ListPopup(String title, List<ListItem> items, Listener listener) {
        VBox container = new VBox();
        container.setSpacing(16);
        container.getStyleClass().add("popup-bg");
        container.setPadding(new Insets(16, 0, 0, 0));

        Text titleText = new Text("  " + title);
        titleText.getStyleClass().add("list-popup-title");
        container.getChildren().add(titleText);

        VBox listContainer = new VBox();
        listContainer.getChildren().addAll(items);
        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(350);
        scrollPane.setStyle("-fx-background-color:transparent;");
        container.getChildren().add(scrollPane);
        for (int i = 0; i < items.size(); i++) {
            ListItem item = items.get(i);
            int finalI = i;
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    listener.onItemClicked(finalI);
                }
            });
        }

        getChildren().add(container);
    }

    public interface Listener {
        void onItemClicked(int index);
    }
}
