package nl.tudelft.oopp.demo.views;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Building;
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

        createTitle();
        createButtonsRow();
        createBuildingsTitle();

        List<Building> buildingsList = BuildingCommunication.getAllBuildings();


        BuildingsGridView buildingsGrid = new BuildingsGridView(buildingsList);
        rootContainer.getChildren().add(buildingsGrid);
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
        mainButtons.add(bikesButton);
        Image roomsImage = new Image("/images/main-screen-rooms.jpg");
        RectangularImageButton roomsButton = new RectangularImageButton(roomsImage, ROOMS_STRING);
        mainButtons.add(roomsButton);
        Image foodImage = new Image("/images/main-screen-food.jpg");
        RectangularImageButton foodButton = new RectangularImageButton(foodImage, FOOD_STRING);
        mainButtons.add(foodButton);
        buttonsRow.getChildren().addAll(mainButtons);

        // Resize layout on width change
        rootContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            resizeDisplay(newScene.getWidth());
            newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeDisplay(newWidth);
            });
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

        rootContainer.setPadding(new Insets(20, 0, 0, 0));

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
