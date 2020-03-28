package nl.tudelft.oopp.demo.widgets;

import static nl.tudelft.oopp.demo.communication.ImageCommunication.getBuildingImageUrl;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.RoomsListRoute;


public class BuildingsGridView extends GridPane {
    private List<Building> buildings;
    private List<RectangularImageButton> buildingButtons;
    private Listener listener;

    /**
     * Creates a GridView that displays the buildings as buttons
     * that can be pressed and throw an event.
     * @param buildings the list of buildings to be displayed
     */
    public BuildingsGridView(List<Building> buildings) {
        this.buildings = buildings;

        buildingButtons = new ArrayList<>();
        addButtons();

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth);
                });
            }
        });
    }

    /**
     * Sets the listener for click events.
     * @param listener the listener
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void addButtons() {
        for (int i = 0; i < buildings.size(); i++) {
            Image image = new Image(getBuildingImageUrl(buildings.get(i).getCode()));
            RectangularImageButton button = new RectangularImageButton(image,
                                            buildings.get(i).getNameAndCode());
            int buildingsPerRow = 5;
            this.add(button, i % buildingsPerRow, i / buildingsPerRow, 1, 1);
            buildingButtons.add(button);

            int finalI = i;


            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (listener != null) {
                        listener.onBuildingClicked(finalI);
                        // System.out.println("A button has been clicked");
                    }
                    // System.out.println(buildings.get(finalI).getCode());

                    RectangularImageButton button = (RectangularImageButton) event.getSource();
                    RoutingScene routingScene = (RoutingScene) button.getScene();
                    routingScene.pushRoute(new RoomsListRoute(buildings.get(finalI).getCode()));
                }
            });
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

    public interface Listener {
        void onBuildingClicked(int buildingId);
    }
}
