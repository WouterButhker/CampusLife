package nl.tudelft.oopp.demo.views;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.FavoriteRestaurantCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ButtonsGridView;

public class RestaurantsListRoute extends Route {
    private AppBar appBar;
    private VBox rootContainer;
    private ScrollPane scrollPane;
    private VBox mainContainer;

    private Text favoritesTitle;
    private ButtonsGridView favoritesGrid;
    private List<FavoriteRestaurant> favorites;

    private Text restaurantsTitle;
    private ButtonsGridView restaurantsGrid;
    private List<Restaurant> restaurants;

    /**
     * A route that displays all the restaurants.
     */
    public RestaurantsListRoute() {
        rootContainer = new VBox();

        appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        mainContainer = new VBox();
        mainContainer.setPadding(new Insets(32, 0, 0, 0));
        mainContainer.setAlignment(Pos.CENTER);
        scrollPane = new ScrollPane(mainContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rootContainer.getChildren().add(scrollPane);

        favoritesTitle = new Text("Your Favorites");
        favoritesTitle.getStyleClass().add("main-screen-medium-text");
        mainContainer.getChildren().add(favoritesTitle);
        favoritesGrid = new ButtonsGridView(new ArrayList<>(), new ArrayList<>(), 5);
        mainContainer.getChildren().add(favoritesGrid);

        restaurantsTitle = new Text("Restaurants");
        restaurantsTitle.getStyleClass().add("main-screen-medium-text");
        mainContainer.getChildren().add(restaurantsTitle);
        restaurantsGrid = new ButtonsGridView(new ArrayList<>(), new ArrayList<>(), 5);
        restaurantsGrid.setPadding(new Insets(32, 0, 0, 0));
        mainContainer.getChildren().add(restaurantsGrid);
        refreshRestaurants();

        favoritesGrid.setListener(new ButtonsGridView.Listener() {
            @Override
            public void onButtonClicked(int buttonIndex) {
                Restaurant restaurant = favorites.get(buttonIndex).getRestaurant();
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RestaurantMenuRoute(restaurant));
            }
        });
        restaurantsGrid.setListener(new ButtonsGridView.Listener() {
            @Override
            public void onButtonClicked(int buttonIndex) {
                Restaurant restaurant = restaurants.get(buttonIndex);
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RestaurantMenuRoute(restaurant));
            }
        });

        // Resize layout on width change
        mainContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                refreshRestaurants();
                resizeDisplay(newScene.getWidth());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue());
                });
            }
        });
    }

    private void refreshRestaurants() {
        favorites = FavoriteRestaurantCommunication.getAll();

        favoritesTitle.setVisible(!favorites.isEmpty());
        favoritesTitle.setManaged(!favorites.isEmpty());
        favoritesGrid.setVisible(!favorites.isEmpty());
        favoritesGrid.setManaged(!favorites.isEmpty());

        List<Image> favoriteImages = new ArrayList<>();
        List<String> favoriteNames = new ArrayList<>();
        for (FavoriteRestaurant favoriteRestaurant : favorites) {
            String imageUrl = ImageCommunication.getRestaurantImageUrl(
                    favoriteRestaurant.getRestaurant().getId()).get(0);
            favoriteImages.add(new Image(imageUrl));
            favoriteNames.add(favoriteRestaurant.getRestaurant().getName());
        }
        favoritesGrid.setButtons(favoriteImages, favoriteNames);

        restaurants = RestaurantCommunication.getRestaurants();
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            for (FavoriteRestaurant favoriteRestaurant : favorites) {
                if (restaurant.getId().equals(favoriteRestaurant.getRestaurant().getId())) {
                    restaurants.remove(i);
                    i--;
                    break;
                }
            }
        }

        List<Image> restaurantImages = new ArrayList<>();
        List<String> restaurantNames = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            String imageUrl = ImageCommunication.getRestaurantImageUrl(
                    restaurant.getId()).get(0);
            restaurantImages.add(new Image(imageUrl));
            restaurantNames.add(restaurant.getName());
        }
        restaurantsGrid.setButtons(restaurantImages, restaurantNames);
    }

    private void resizeDisplay(double newWidth) {
        favoritesGrid.setPrefWidth(newWidth);
        restaurantsGrid.setPrefWidth(newWidth);

        double textSize = getRoutingScene().getHeight() * 0.045;
        favoritesTitle.setStyle("-fx-font-size: " + textSize);
        restaurantsTitle.setStyle("-fx-font-size: " + textSize);

        scrollPane.setPrefHeight(getRoutingScene().getHeight() * 0.9);
    }

    @Override
    public Parent getRootElement() {
        return rootContainer;
    }
}
