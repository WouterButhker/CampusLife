package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The DateBox widget is simply a square with rounder corners.
 * It has a number in the middle representing the date and has various states:
 * available, selected and if the date displayed is today. This changes it's colour.
 * It is a component of the CalendarWidget.
 */
public class DateBox extends StackPane {
    private int day;
    private boolean isAvailable;
    private boolean isSelected;
    private boolean isToday;

    private VBox background;
    private Text numberText;
    private VBox hoverGlow;
    private Rectangle hoverGlowRectangle;

    /**
     * Creates a new DateBox.
     * @param day the day to be shown
     * @param isAvailable if it is clickable
     * @param isSelected if it is selected
     * @param isToday true if it is today's date
     */
    public DateBox(int day, boolean isAvailable, boolean isSelected, boolean isToday) {
        this.day = day;
        this.isAvailable = isAvailable;
        this.isSelected = isSelected;
        this.isToday = isToday;

        createComponents();

        resizeDisplay(getWidth());
        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (DateBox.this.isAvailable()) {
                    hoverGlow.setVisible(true);
                }
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverGlow.setVisible(false);
            }
        });

        updateBackground();
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        updateBackground();
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
        updateBackground();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setToday(boolean today) {
        this.isToday = today;
        updateBackground();
    }

    private void updateBackground() {
        background.getStyleClass().clear();
        if (isSelected) {
            background.getStyleClass().add("selected-date-box");
        } else if (isAvailable) {
            background.getStyleClass().add("available-date-box");
        } else {
            background.getStyleClass().add("unavailable-date-box");
        }
    }

    private void createComponents() {
        background = new VBox();
        background.setAlignment(Pos.CENTER);
        getChildren().add(background);

        numberText = new Text("" + day);
        background.getChildren().add(numberText);

        hoverGlow = new VBox();
        hoverGlowRectangle = new Rectangle();
        hoverGlow.getChildren().add(hoverGlowRectangle);
        hoverGlowRectangle.setFill(Color.TRANSPARENT);
        hoverGlow.getStyleClass().add("hover-glow");
        hoverGlow.setVisible(false);
        getChildren().add(hoverGlow);
    }

    private void resizeDisplay(double newWidth) {
        background.setMinWidth(newWidth);
        background.setMinHeight(newWidth);
        background.setMaxWidth(newWidth);
        background.setMaxHeight(newWidth);

        hoverGlowRectangle.setWidth(newWidth);
        hoverGlowRectangle.setHeight(newWidth);
    }
}

