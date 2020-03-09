package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.entities.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsGridView extends GridPane {
    private List<Restaurant> restaurants;
    private List<RectangularImageButton> restaurantButtons;
    private Listener listener;

    /**
     * Creates a GridView that displays the restaurants as buttons
     * that can be pressed and throw an event.
     * @param restaurants the list of restaurants to be displayed
     */
    public RestaurantsGridView(List<Restaurant> restaurants) {
        this.restaurants = restaurants;

        restaurantButtons = new ArrayList<>();
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
        for (int i = 0; i < restaurants.size(); i++) {
            Image image = new Image("/images/main-screen-food.jpg");
            RectangularImageButton button = new RectangularImageButton(image, restaurants.get(i).getName());
            int restaurantsPerRow = 5;
            this.add(button, i % restaurantsPerRow, i / restaurantsPerRow, 1, 1);
            restaurantButtons.add(button);

            int finalI = i;


            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (listener != null) {
                        listener.onRestaurantClicked(finalI);
                    }
                }
            });
        }
    }

    private void resizeDisplay(Number newWidth) {
        double buttonWidth = newWidth.doubleValue() / 6;
        double spacing = (newWidth.doubleValue() - buttonWidth * 5) / 6;
        for (int i = 0; i < restaurantButtons.size(); i++) {
            RectangularImageButton button = restaurantButtons.get(i);
            button.setFitWidth(buttonWidth);
        }
        setPadding(new Insets(0, 0, 0, spacing));
        setHgap(spacing);
        setVgap(spacing);
    }

    public interface Listener {
        void onRestaurantClicked(int buildingId);
    }
}
