package nl.tudelft.oopp.demo.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;


public class BikeReservationWidget extends VBox {

    private List<Building> buildingList;
    private List<BikeReservation> bikeReservations;

    private Calendar from;
    private Calendar to;

    private VBox buildingDisplayList;
    private List<HBox> boxes;

    private ScrollPane scrollPane;
    private AnchorPane buildingDisplay;

    private HBox dateContainer;
    private ImageView dateIcon;
    private Text dateText;

    private HBox timeContainer;
    private ImageView timeIcon;
    private Text timeText;

    private boolean dateSelected = false;

    /**
     * Creates a new BikeReservationWidget that is used to reserve bikes.
     */
    public BikeReservationWidget() {
        buildingList = BuildingCommunication.getAllBuildingsWithBikeStation();
        bikeReservations = BikeReservationCommunication.getAllReservations();
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: -primary-color-light; -fx-background-radius: 8;");

        dateContainer = new HBox();
        dateContainer.setAlignment(Pos.CENTER);
        dateContainer.setSpacing(16);
        Image dateIconImage = new Image("/images/date_icon.png");
        dateIcon = new ImageView(dateIconImage);
        dateContainer.getChildren().add(dateIcon);
        dateText = new Text("");
        dateText.getStyleClass().add("reservation-date-text");
        dateContainer.getChildren().add(dateText);
        getChildren().add(dateContainer);

        timeContainer = new HBox();
        timeContainer.setAlignment(Pos.CENTER);
        timeContainer.setSpacing(16);
        Image timeIconImage = new Image("/images/time_icon.png");
        timeIcon = new ImageView(timeIconImage);
        timeContainer.getChildren().add(timeIcon);
        timeText = new Text("");
        dateText.getStyleClass().add("reservation-time-text");
        timeContainer.getChildren().add(timeText);
        getChildren().add(timeContainer);

        buildingDisplay = new AnchorPane();
        scrollPane = new ScrollPane(buildingDisplay);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        buildingDisplayList = new VBox();
        boxes = new ArrayList<HBox>();
        buildingDisplay.getChildren().add(buildingDisplayList);
        loadBuildings();
        getChildren().add(0, scrollPane);

        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
            loadBuildings();
        });

        updateDisplay();

    }

    private void loadBuildings() {
        int numBuildings = buildingList.size();
        buildingDisplayList.getChildren().clear();
        boxes.clear();
        for (int i = 0; i < numBuildings; i++) {
            HBox buildingBox = new HBox();
            buildingBox.setPrefWidth(scrollPane.getPrefWidth() - 10);
            buildingBox.setPadding(new Insets(5));
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;"
                    + "-fx-background-color: -primary-color-light";
            buildingBox.setStyle(css);
            Image image = new Image("images/TuDelftTempIMG.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(60);
            imageView.setPreserveRatio(true);
            Building building = buildingList.get(i);
            Label label = new Label("Building Code : " + building.getCode()
                    + " | " + building.getName()
                    + "\nAmount of bikes : " + building.getBikes());
            //65 for the image width
            //45 for the button width
            //10 for the padding
            //20 for the left and right padding of the total HBox
            label.setPrefWidth(scrollPane.getPrefWidth() - 65 - 45 - 10 - 20);
            label.setPadding(new Insets(0, 0, 0, 10));

            Button button = new Button("reserve");
            button.setPrefSize(45, 40);
            button.setPadding(new Insets(0));
            button.setId(building.getCode().toString());
            StackPane reserveButton = new StackPane(button);
            reserveButton.setPadding(new Insets(10, 0, 10, 0));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    reserveBike(event);
                    updateButtons();
                }
            });
            buildingBox.getChildren().addAll(imageView, label, reserveButton);
            boxes.add(buildingBox);
            buildingDisplayList.getChildren().add(buildingBox);
        }
        updateButtons();
    }

    private void reserveBike(ActionEvent event) {
        if (dateSelected && !bikeReserved()) {
            Button button = (Button) event.getSource();
            int buildingCode = Integer.parseInt(button.getId());
            int userId = AuthenticationCommunication.myUserId;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(from.getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String fromTime = timeFormat.format(from.getTime());
            String toTime = timeFormat.format(to.getTime());
            String slot = fromTime + "-" + toTime;
            //            BikeReservationCommunication.createBikeReservation(new
            //            BikeReservation(null, userId,
            //                    buildingCode, buildingCode, date, slot));
            BikeReservationCommunication.addReservationToTheDatabase(userId,
                    buildingCode, buildingCode, date, slot);
            bikeReservations = BikeReservationCommunication.getAllReservations();
        }
    }

    private boolean bikeReserved() {
        for (BikeReservation bikeReservation : bikeReservations) {
            System.out.println(bikeReservation);
            if (bikeReservation.getUser() == AuthenticationCommunication.myUserId) {
                return true;
            }
        }
        return false;
    }

    private void resizeDisplay(double newWidth) {
        setSpacing(newWidth * 0.08);
        setPadding(new Insets(newWidth * 0.08));

        buildingDisplay.setPrefWidth(newWidth * 0.8);
        scrollPane.setPrefWidth(newWidth * 0.8);

        dateText.setStyle("-fx-font-size:" + newWidth * 0.05);
        timeText.setStyle("-fx-font-size:" + newWidth * 0.05);
    }

    /**
     * Sets the from and to time to be displayed.
     * @param from the beginning time of the reservation
     * @param to the ending time of the reservation
     */
    public void setPeriod(Calendar from, Calendar to) {
        this.from = from;
        this.to = to;

        updateDisplay();
    }

    private void updateDisplay() {
        if (from == null) {
            dateContainer.setVisible(false);
            timeContainer.setVisible(false);
        } else {
            dateContainer.setVisible(true);
            timeContainer.setVisible(true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
            dateText.setText(dateFormat.format(from.getTime()));

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String fromTime = timeFormat.format(from.getTime());
            String toTime = timeFormat.format(to.getTime());
            timeText.setText(String.format("%s - %s", fromTime, toTime));
        }
    }

    public void dateSelected(boolean selected) {
        dateSelected = selected;
        updateButtons();
    }

    private void updateButtons() {
        for (int i = 0; i < boxes.size(); i++) {
            StackPane stackPane = (StackPane) boxes.get(i).getChildren().get(2);
            Button button = (Button) stackPane.getChildren().get(0);
            if (bikeReserved() || !dateSelected) {
                button.setDisable(true);
                String disabledButton = "-fx-background-color: gray";
                button.setStyle(disabledButton);
            } else {
                button.setDisable(false);
                button.setStyle(null);
            }
        }
    }

}
