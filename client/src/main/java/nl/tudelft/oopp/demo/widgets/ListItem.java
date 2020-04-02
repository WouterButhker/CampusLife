package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ListItem extends Group {

    /**
     * A list item with primary and secondary text.
     * @param primaryText the big bold text
     * @param secondaryText the small secondary text
     */
    public ListItem(String primaryText, String secondaryText) {
        VBox container = new VBox();
        container.setSpacing(8);
        container.getStyleClass().add("list-item-bg");
        container.setPadding(new Insets(16, 32, 16, 32));

        Text primary = new Text(primaryText);
        primary.getStyleClass().add("list-item-ptext");
        primary.setWrappingWidth(300);
        container.getChildren().add(primary);

        Text secondary = new Text(secondaryText);
        secondary.getStyleClass().add("list-item-stext");
        container.getChildren().add(secondary);

        getChildren().add(container);
    }
}
