package nl.tudelft.oopp.demo.views;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.RestaurantsGridView;

public class RestaurantsListRoute extends Route {
    private ScrollPane scrollPane;
    private AppBar appBar;
    private VBox mainContainer;
    private RestaurantsGridView restaurantsGrid;

    /**
     * A route that displays all the restaurants.
     */
    public RestaurantsListRoute() {
        //createRootElement(RestaurantCommunication.getRestaurants());

        appBar = new AppBar();
        mainContainer = new VBox();

        List<Restaurant> restaurantList = RestaurantCommunication.getRestaurants();
        restaurantsGrid = new RestaurantsGridView(restaurantList);
        scrollPane = new ScrollPane(restaurantsGrid);

        mainContainer.getChildren().add(appBar);
        mainContainer.getChildren().add(scrollPane);

        restaurantsGrid.setListener(new RestaurantsGridView.Listener() {
            @Override
            public void onRestaurantClicked(Restaurant restaurant) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RestaurantMenuRoute(restaurant));
            }
        });
    }

    /**
     * Creates the root element.
     *
     * @param restaurantList The list of restaurants to appear in the page
     */
    public void createRootElement(List<Restaurant> restaurantList) {
        appBar = new AppBar();
        mainContainer = new VBox();

        restaurantsGrid = new RestaurantsGridView(restaurantList);
        scrollPane = new ScrollPane(restaurantsGrid);

        mainContainer.getChildren().add(appBar);
        mainContainer.getChildren().add(scrollPane);

        restaurantsGrid.setListener(new RestaurantsGridView.Listener() {
            @Override
            public void onRestaurantClicked(Restaurant restaurant) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RestaurantMenuRoute(restaurant));
            }
        });


    }

    @Override
    public Parent getRootElement() {
        return mainContainer;
    }
}
