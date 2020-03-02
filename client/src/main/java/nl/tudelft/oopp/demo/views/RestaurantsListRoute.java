package nl.tudelft.oopp.demo.views;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.RestaurantsGridView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListRoute extends Route {
    private ScrollPane scrollPane;
    private AppBar appBar;
    private VBox mainContainer;
    private RestaurantsGridView restaurantsGridView;

    public RestaurantsListRoute() {
        appBar = new AppBar();
        mainContainer = new VBox();

        List<String> restaurants = new ArrayList<>();
        for (int i = 0; i < 44; i++) {
            restaurants.add("Restaurant " + i);
        }

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
