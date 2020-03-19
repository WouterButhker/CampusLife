package nl.tudelft.oopp.demo.views;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.RestaurantsGridView;

public class RestaurantsListRoute extends Route {
    private ScrollPane scrollPane;
    private AppBar appBar;
    private VBox mainContainer;
    private RestaurantsGridView restaurantsGridView;

    /**
     * A route that displays all the restaurants.
     */
    public RestaurantsListRoute() {
        appBar = new AppBar();
        mainContainer = new VBox();

        List<Restaurant> restaurants = RestaurantCommunication.getRestaurants();

        restaurantsGridView = new RestaurantsGridView(restaurants);
        scrollPane = new ScrollPane(restaurantsGridView);

        mainContainer.getChildren().add(appBar);
        mainContainer.getChildren().add(scrollPane);
    }

    @Override
    public Parent getRootElement() {
        return mainContainer;
    }
}
