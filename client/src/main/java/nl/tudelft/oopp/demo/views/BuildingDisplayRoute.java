package nl.tudelft.oopp.demo.views;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.communication.FavoriteRoomCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ButtonsGridView;
import nl.tudelft.oopp.demo.widgets.GalleryWidget;

public class BuildingDisplayRoute extends Route {
    private Building building;

    private VBox rootContainer;
    private HBox contentContainer;
    private VBox rightContainer;
    private ScrollPane scrollPane;
    private GalleryWidget galleryWidget;

    private Text title;
    private Rectangle titlePadding;
    private TextWithIcon locationText;
    private TextWithIcon timetableText;

    private Rectangle topPadding;
    private Rectangle buttonPadding;
    private Button roomsButton;

    private Text restaurantsTitle;
    private ButtonsGridView restaurantsGrid;

    /**
     * Instantiates the building display route for the room passed as parameter.
     * @param building the building to be displayed
     */
    public BuildingDisplayRoute(Building building) {
        this.building = building;

        rootContainer = new VBox();

        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        contentContainer = new HBox();
        rootContainer.getChildren().add(contentContainer);

        galleryWidget = new GalleryWidget(getBuildingImages());
        contentContainer.getChildren().add(galleryWidget);

        rightContainer = new VBox();
        rightContainer.setSpacing(16);
        rightContainer.setAlignment(Pos.TOP_CENTER);
        rightContainer.setPadding(new Insets(32));
        scrollPane = new ScrollPane(rightContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentContainer.getChildren().add(scrollPane);

        topPadding = new Rectangle();
        topPadding.setFill(Color.TRANSPARENT);
        rightContainer.getChildren().add(topPadding);

        title = new Text(String.format("%s", building.getName()));
        title.getStyleClass().add("gallery-text");
        rightContainer.getChildren().add(title);

        titlePadding = new Rectangle();
        titlePadding.setHeight(24);
        rightContainer.getChildren().add(titlePadding);

        // Add info texts
        Image locationImage = new Image("/images/location_icon.png");
        locationText = new TextWithIcon(locationImage, building.getLocation());
        rightContainer.getChildren().add(locationText);

        Image timetableIcon = new Image("/images/time_icon.png");
        String timetableString  = "";
        Weekdays weekdays = new Weekdays(building.getOpeningHours());
        String[] days = new String[]{"Monday      ", "Tuesday     ", "Wednesday", "Thursday    ",
            "Friday         ", "Saturday     ", "Sunday       "};
        for (int i = 0; i < 7; i++) {
            timetableString += String.format("%s: %s", days[i], weekdays.getWeekdays().get(i));
            if (i + 1 != 7) {
                timetableString += "\n";
            }
        }
        timetableText = new TextWithIcon(timetableIcon, timetableString);
        rightContainer.getChildren().add(timetableText);

        // Create rooms button
        buttonPadding = new Rectangle();
        buttonPadding.setHeight(24);
        rightContainer.getChildren().add(buttonPadding);
        roomsButton = new Button("Browse rooms");
        roomsButton.getStyleClass().add("reserve-button");
        roomsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button button = (Button) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();
                routingScene.pushRoute(new RoomsListRoute(building.getBuildingCode()));
            }
        });
        rightContainer.getChildren().add(roomsButton);

        buttonPadding = new Rectangle();
        buttonPadding.setHeight(24);
        rightContainer.getChildren().add(buttonPadding);

        List<Restaurant> restaurants = RestaurantCommunication
                .getAllRestaurantsFromBuilding(building.getCode());
        List<Image> restaurantImages = new ArrayList<>();
        List<String> restaurantNames = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantImages.add(new Image(
                    ImageCommunication.getRestaurantImageUrl(restaurant.getId()).get(0)));
            restaurantNames.add(restaurant.getName());
        }

        restaurantsTitle = new Text("Restaurants");
        restaurantsTitle.getStyleClass().add("gallery-text");
        restaurantsTitle.setTextAlignment(TextAlignment.CENTER);

        restaurantsGrid = new ButtonsGridView(restaurantImages, restaurantNames, 2);
        restaurantsGrid.setListener(new ButtonsGridView.Listener() {
            @Override
            public void onButtonClicked(int i) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RestaurantMenuRoute(restaurants.get(i)));
            }
        });

        if (!restaurants.isEmpty()) {
            rightContainer.getChildren().add(restaurantsTitle);
            rightContainer.getChildren().add(restaurantsGrid);
        }

        rootContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth(), newScene.getHeight() * 0.9);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newScene.getWidth(), newScene.getHeight() * 0.9);
                });
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    resizeDisplay(newScene.getWidth(), newScene.getHeight() * 0.9);
                });
            }
        });
    }

    private void resizeDisplay(double newWidth, double newHeight) {
        topPadding.setHeight(newHeight * 0.050);

        rightContainer.setPrefWidth(newWidth * 0.5);
        title.setStyle("-fx-font-size: " + newHeight * 0.075);
        title.setWrappingWidth(newWidth * 0.4 - 64);
        double infoTextHeight = newHeight * 0.045;
        locationText.setPrefWidth(newWidth * 0.5 - 64);
        locationText.setPrefHeight(infoTextHeight);
        timetableText.setPrefWidth(newWidth * 0.5 - 64);
        timetableText.setPrefHeight(infoTextHeight);

        roomsButton.setStyle("-fx-font-size: " + newHeight * 0.025);

        restaurantsTitle.setWrappingWidth(newWidth * 0.5 - 64);
        restaurantsTitle.setStyle("-fx-font-size: " + newHeight * 0.040);
        restaurantsGrid.setPrefWidth(newWidth * 0.5 - 64);

        galleryWidget.setPrefWidth(newWidth * 0.5);
        galleryWidget.setPrefHeight(newHeight);
    }

    private List<Image> getBuildingImages() {
        String imageUrl = ImageCommunication.getBuildingImageUrl(building.getBuildingCode());

        List<Image> roomImages = new ArrayList<>();
        roomImages.add(new Image(imageUrl));
        return roomImages;
    }

    @Override
    public Parent getRootElement() {
        return rootContainer;
    }

    private static class TextWithIcon extends HBox {
        private ImageView imageView;
        private Text text;

        public TextWithIcon(Image icon, String text) {
            setAlignment(Pos.TOP_LEFT);
            setPadding(new Insets(0, 0, 0, 24));
            setSpacing(24);

            imageView = new ImageView(icon);
            getChildren().add(imageView);

            this.text = new Text(text);
            this.text.getStyleClass().add("gallery-text");
            getChildren().add(this.text);

            prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeWidget(getPrefWidth(), getPrefHeight());
            });
            prefHeightProperty().addListener((obs, oldHeight, newHeight) -> {
                resizeWidget(getPrefWidth(), getPrefHeight());
            });
        }

        private void resizeWidget(double newWidth, double newHeight) {
            text.setWrappingWidth(newWidth - newHeight * 0.8 - 24);
            text.setStyle("-fx-font-size: " + newHeight);
            text.setLineSpacing(newHeight * 0.25);

            imageView.setFitWidth(newHeight);
            imageView.setFitHeight(newHeight);
        }
    }
}
