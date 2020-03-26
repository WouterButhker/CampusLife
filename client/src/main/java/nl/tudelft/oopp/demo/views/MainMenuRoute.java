package nl.tudelft.oopp.demo.views;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.FoodCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.BuildingsGridView;
import nl.tudelft.oopp.demo.widgets.RectangularImageButton;


public class MainMenuRoute extends Route {
    public static final String BIKES_STRING = "Reserve a bike";
    public static final String ROOMS_STRING = "Reserve a room";
    public static final String FOOD_STRING = "Order food";

    private ScrollPane scrollPane;
    private VBox rootContainer;
    private Text universityTitle;

    private HBox buildingsTitleContainer;
    private Text buildingsTitle;

    private HBox buttonsRow;
    private List<RectangularImageButton> mainButtons;

    /**
     * Creates a MainMenuRoute which is the Route that displays the option to order
     * food, reserve a room or a bike. Also displays all the buildings
     */
    public MainMenuRoute() {
        rootContainer = new VBox();
        rootContainer.setAlignment(Pos.TOP_CENTER);
        rootContainer.setSpacing(20);
        scrollPane = new ScrollPane(rootContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Boolean isAdmin = false;
        if (AuthenticationCommunication.myUserRole.equals("Admin")) {
            isAdmin = true;
        }


        rootContainer.getChildren().add(new AppBar(isAdmin));

        //addDummyRestaurantData();
        createButtonsRow();
        createBuildingsTitle();

        List<Building> buildingsList = BuildingCommunication.getAllBuildings();

        BuildingsGridView buildingsGrid = new BuildingsGridView(buildingsList);
        rootContainer.getChildren().add(buildingsGrid);
    }

    private void addDummyRestaurantData() {
        List<Restaurant> restaurants = RestaurantCommunication.getRestaurants();
        if (restaurants.isEmpty()) {
            Restaurant restaurant = RestaurantCommunication.createRestaurant(new Restaurant(
                    null,
                    1,
                    "Subway",
                    "Takeaway food from Subway in Delft! "
                            + "Choose your favourite meal from a wide variety "
                            + "& have it delivered to your door!"
            ));
            int id = restaurant.getId();
            FoodCommunication.createFood(new Food(null, "Broodje Doner", id, 1.55));
            FoodCommunication.createFood(new Food(null, "Italiano BMT", id, 4.30));
            FoodCommunication.createFood(new Food(null, "Kapsalon Medium", 1, 5.50));
            FoodCommunication.createFood(new Food(null, "Spa Rood", id, 3.20));
            FoodCommunication.createFood(new Food(null, "Pizza 1", id, 3.24));
            FoodCommunication.createFood(new Food(null, "Pizza 2", id, 3.25));
            FoodCommunication.createFood(new Food(null, "Pizza 3", id, 3.25));
            FoodCommunication.createFood(new Food(null, "Pizza 4", id, 3.29));
        }
    }

    @Override
    public Parent getRootElement() {
        return scrollPane;
    }

    private void createTitle() {
        universityTitle = new Text("TUDelft");
        universityTitle.getStyleClass().add("university-main-title");
        universityTitle.setTextAlignment(TextAlignment.CENTER);
        rootContainer.getChildren().add(universityTitle);
    }

    private void createButtonsRow() {
        buttonsRow = new HBox();
        rootContainer.getChildren().add(buttonsRow);

        mainButtons = new ArrayList<>();
        Image bikesImage = new Image("/images/main-screen-bike.jpg");
        RectangularImageButton bikesButton = new RectangularImageButton(bikesImage, BIKES_STRING);
        bikesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RectangularImageButton button = (RectangularImageButton) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();
                routingScene.pushRoute(new BikesReservationRoute());
            }
        });
        mainButtons.add(bikesButton);
        Image roomsImage = new Image("/images/main-screen-rooms.jpg");
        RectangularImageButton roomsButton = new RectangularImageButton(roomsImage, ROOMS_STRING);
        roomsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RectangularImageButton button = (RectangularImageButton) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();
                routingScene.pushRoute(new RoomsListRoute());
            }
        });
        mainButtons.add(roomsButton);
        Image foodImage = new Image("/images/main-screen-food.jpg");
        RectangularImageButton foodButton = new RectangularImageButton(foodImage, FOOD_STRING);
        foodButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RectangularImageButton button = (RectangularImageButton) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();

                Restaurant restaurant = RestaurantCommunication.getRestaurants().get(0);

                routingScene.pushRoute(new RestaurantMenuRoute(restaurant));
            }
        });
        mainButtons.add(foodButton);
        buttonsRow.getChildren().addAll(mainButtons);

        // Resize layout on width change
        rootContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth);
                });
            }
        });
    }

    private void createBuildingsTitle() {
        buildingsTitleContainer = new HBox();
        buildingsTitle = new Text("  Buildings");
        buildingsTitle.getStyleClass().add("main-screen-medium-text");
        buildingsTitle.setTextAlignment(TextAlignment.CENTER);
        buildingsTitleContainer.getChildren().add(buildingsTitle);
        rootContainer.getChildren().add(buildingsTitleContainer);
    }

    private void resizeDisplay(Number newWidth) {
        /*
         This route should display the title, main buttons,
         buildings title and then the buildings below
         the building buttons should be partially covered so
         as to not make them the main thing
         */

        rootContainer.setPadding(new Insets(0, 0, 0, 0));

        buildingsTitleContainer.setMinWidth(newWidth.doubleValue());

        final double buttonWidth = 0.245;
        for (RectangularImageButton imageButton : mainButtons) {
            imageButton.setFitWidth(buttonWidth * newWidth.doubleValue());
        }
        double horizontalSpacing = ((1 - buttonWidth * 3) / 4) * newWidth.doubleValue();
        buttonsRow.setPadding(new Insets(0, 0, 0, horizontalSpacing));
        buttonsRow.setSpacing(horizontalSpacing);
    }
}
