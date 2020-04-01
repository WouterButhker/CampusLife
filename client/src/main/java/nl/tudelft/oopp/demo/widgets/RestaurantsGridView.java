package nl.tudelft.oopp.demo.widgets;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;

public class RestaurantsGridView extends GridPane {
    private List<Restaurant> restaurants;
    private List<RectangularImageButton> restaurantButtons;
    private Listener listener;

    /**
     * Creates a GridView that displays the restaurants as buttons
     * that can be pressed and throw an event.
     *
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
     *
     * @param listener the listener
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private final int restaurantsPerRow = 3;

    private void addButtons() {
        for (int i = 0; i < restaurants.size(); i++) {
            Image image = new Image(ImageCommunication
                            .getRestaurantImageUrl(restaurants.get(i).getId()).get(0));
            RectangularImageButton button =
                    new RectangularImageButton(image, restaurants.get(i).getName());
            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (listener != null) {
                        listener.onRestaurantClicked(restaurants.get(finalI));
                    }
                }
            });
            add(button, i % restaurantsPerRow, i / restaurantsPerRow, 1, 1);
            restaurantButtons.add(button);
        }
    }

    private void resizeDisplay(Number newWidth) {
        double buttonWidth = newWidth.doubleValue() / 6;
        double spacing = (newWidth.doubleValue() - buttonWidth * 5) / 6;
        for (RectangularImageButton button : restaurantButtons) {
            button.setFitWidth(buttonWidth);
        }
        setPadding(new Insets(0, 0, 0, spacing));
        setHgap(spacing);
        setVgap(spacing);
    }

    public interface Listener {
        void onRestaurantClicked(Restaurant restaurant);
    }
}
