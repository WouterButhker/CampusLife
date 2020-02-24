package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BuildingsGridView extends GridPane {
    private List<String> buildings;
    private List<RectangularImageButton> buildingButtons;

    public BuildingsGridView(List<String> buildings) {
        this.buildings = buildings;

        buildingButtons = new ArrayList<>();
        addButtons();

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            resizeDisplay(newScene.getWidth());
            newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeDisplay(newWidth);
            });
        });
    }

    private final int buildingsPerRow = 5;
    private void addButtons() {
        for (int i = 0; i < buildings.size(); i++) {
            Image image = new Image("/images/main-screen-default-building.jpg");
            RectangularImageButton button = new RectangularImageButton(image, buildings.get(i));
            add(button, i % buildingsPerRow, i / buildingsPerRow, 1, 1);
            buildingButtons.add(button);
        }
    }

    private void resizeDisplay(Number newWidth) {
        double buttonWidth = newWidth.doubleValue() / 6;
        double spacing = (newWidth.doubleValue() - buttonWidth * 5) / 6;
        for (int i = 0; i < buildingButtons.size(); i++) {
            RectangularImageButton button = buildingButtons.get(i);
            button.setFitWidth(buttonWidth);
        }
        setPadding(new Insets(0, 0, 0, spacing));
        setHgap(spacing);
        setVgap(spacing);
    }
}
