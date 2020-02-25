package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class RoomsGridView extends GridPane {
    private List<String> rooms;
    private List<RectangularImageButton> roomButtons;

    public RoomsGridView(List<String> rooms) {
        this.rooms = rooms;

        roomButtons = new ArrayList<>();
        addButtons();

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            resizeDisplay(newScene.getWidth());
            newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeDisplay(newWidth);
            });
        });
    }

    private final int roomsPerRow = 3;
    private void addButtons() {
        for (int i = 0; i < rooms.size(); i++) {
            Image image = new Image("https://cdn.mos.cms.futurecdn.net/K5nhgMGSRCzdppKW9bQcMd.jpg");
            RectangularImageButton button = new RectangularImageButton(image, rooms.get(i));
            add(button, i % roomsPerRow, i / roomsPerRow, 1, 1);
            roomButtons.add(button);
        }
    }

    private void resizeDisplay(Number newWidth) {
        double buttonWidth = newWidth.doubleValue() / 6;
        double spacing = (newWidth.doubleValue() - buttonWidth * 5) / 6;
        for (int i = 0; i < roomButtons.size(); i++) {
            RectangularImageButton button = roomButtons.get(i);
            button.setFitWidth(buttonWidth);
        }
        setPadding(new Insets(0, 0, 0, spacing));
        setHgap(spacing);
        setVgap(spacing);
    }
}
