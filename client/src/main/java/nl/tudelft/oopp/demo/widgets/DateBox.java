package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DateBox extends StackPane {
    private int day;
    private boolean isAvailable;
    private boolean isSelected;
    private boolean isToday;

    private VBox background;
    private Text numberText;
    private VBox hoverGlow;
    private Rectangle hoverGlowRectangle;

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
                hoverGlow.setVisible(true);
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

