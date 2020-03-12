package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The DayBox widget.
 * A transparent square with a character in the middle.
 * Used to display the day of the month in the CalendarWidget.
 */
public class DayBox extends StackPane {
    private char day;

    private VBox container;
    private Text numberText;

    /**
     * Creates a DayBox.
     * @param day the char supposed to be the initial of the day
     */
    public DayBox(char day) {
        this.day = day;

        createComponents();

        resizeDisplay(getWidth());
        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
        });
    }

    private void createComponents() {
        numberText = new Text("" + day);

        container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(numberText);
        getChildren().add(container);
    }

    private void resizeDisplay(double newWidth) {
        container.setMinWidth(newWidth);
        container.setMinHeight(newWidth);
        container.setMaxWidth(newWidth);
        container.setMaxHeight(newWidth);
    }
}

