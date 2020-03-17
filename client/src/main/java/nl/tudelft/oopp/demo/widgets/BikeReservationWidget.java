package nl.tudelft.oopp.demo.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;


public class BikeReservationWidget extends VBox {

    private List<Building> buildingList;
    private List<BikeReservation> relevantBikeReservations;
    private Building selected;
    private int selectedInList;

    private Label header;

    private ScrollPane scrollPane;
    private AnchorPane buildingDisplay;
    private VBox buildingDisplayList;
    private List<HBox> boxes;

    private AgendaWidget agendaWidget;

    private Calendar timeSelected;
    private Calendar selectedDate = Calendar.getInstance();

    /**
     * Creates a new BikeReservationWidget that is used to reserve bikes.
     */
    public BikeReservationWidget(String text) {
        setSelectedInList(-1);
        buildingList = BuildingCommunication.getAllBuildingsWithBikeStation();
        loadRelevantBikeReservations();
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: -primary-color-light; -fx-background-radius: 8;");

        header = new Label(text);
        header.setTextAlignment(TextAlignment.CENTER);
        StackPane stackPane = new StackPane(header);
        this.getChildren().add(stackPane);

        buildingDisplay = new AnchorPane();
        scrollPane = new ScrollPane(buildingDisplay);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        buildingDisplayList = new VBox();
        boxes = new ArrayList<>();
        buildingDisplay.getChildren().add(buildingDisplayList);
        loadBuildings();
        this.getChildren().add(scrollPane);

        agendaWidget = new AgendaWidget(new AgendaWidget.Listener() {
            @Override
            public void onBlockSelected(int begin, int end, boolean available) {
                timeSelected = (Calendar) selectedDate.clone();
                timeSelected.set(Calendar.HOUR_OF_DAY, begin);
                timeSelected.set(Calendar.MINUTE, 0);
                //DateFormat dateFormat = new SimpleDateFormat("HH:mm");
               // time = dateFormat.format(timeSelected.getTime());
            }
        }, 3);

        this.getChildren().add(agendaWidget);


        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
            loadBuildings();
        });

    }

    private void loadBuildings() {
        int numBuildings = buildingList.size();
        buildingDisplayList.getChildren().clear();
        boxes.clear();
        for (int i = 0; i < numBuildings; i++) {
            HBox buildingBox = new HBox();
            buildingBox.setPrefWidth(scrollPane.getPrefWidth() + 10);
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
            //-65 for the image width
            //-45 for the button width
            //-10 for the padding
            //-20 for the left and right padding of the total HBox
            //+20 for some weird correction??
            label.setPrefWidth(scrollPane.getPrefWidth() - 65 - 45 - 10 - 20 + 20);
            label.setPadding(new Insets(0, 0, 0, 10));

            StackPane buttonPane = new StackPane();
            buttonPane.setPrefSize(45, 40);
            buttonPane.setId(String.valueOf(i));
            //buttonPane.setPadding(new Insets(0, 0, 0, 0));
            VBox hoverGlow = new VBox();
            hoverGlow.setPrefSize(45, 40);
            Rectangle hoverGlowRectangle = new Rectangle();
            hoverGlow.getChildren().add(hoverGlowRectangle);
            hoverGlowRectangle.setFill(Color.TRANSPARENT);
            hoverGlow.getStyleClass().add("hover-glow");
            hoverGlowRectangle.setWidth(45);
            hoverGlowRectangle.setHeight(40);
            hoverGlow.setVisible(false);
            Label text = new Label("Select");
            buttonPane.getChildren().addAll(hoverGlow, text);
            buttonPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    selectBuilding(event);
                    updateButtons();
                }
            });
            buttonPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverGlow.setVisible(true);
                }
            });
            buttonPane.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverGlow.setVisible(false);
                }
            });

            buildingBox.getChildren().addAll(imageView, label, buttonPane);
            boxes.add(buildingBox);
            buildingDisplayList.getChildren().add(buildingBox);
        }
        updateButtons();
    }

    private void selectBuilding(MouseEvent event) {
        StackPane stackPane = (StackPane) event.getSource();
        int ithBuilding = Integer.parseInt(stackPane.getId());
        setSelectedInList(ithBuilding);
        setSelected(buildingList.get(selectedInList));
    }

//    private void reserveBike(ActionEvent event) {
//        if (dateSelected && !bikeReserved()) {
//            Button button = (Button) event.getSource();
//            int buildingCode = Integer.parseInt(button.getId());
//            int userId = AuthenticationCommunication.myUserId;
//            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//            String date = format.format(from.getTime());
//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//            String fromTime = timeFormat.format(from.getTime());
//            String toTime = timeFormat.format(to.getTime());
//            String slot = fromTime + "-" + toTime;
//            //            BikeReservationCommunication.createBikeReservation(new
//            //            BikeReservation(null, userId,
//            //                    buildingCode, buildingCode, date, slot));
//            BikeReservationCommunication.addReservationToTheDatabase(userId,
//                    buildingCode, buildingCode, date, slot);
//            loadRelevantBikeReservations();
//        }
//    }

    private boolean bikeReserved() {
        for (int i = 0; i < relevantBikeReservations.size(); i++) {
            if (relevantBikeReservations.get(i).getUser().equals(AuthenticationCommunication.myUserId)) {
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

        header.setStyle("-fx-font-size:" + newWidth * 0.08);
    }


    private void updateButtons() {
        for (int i = 0; i < boxes.size(); i++) {
            StackPane stackPane = (StackPane) boxes.get(i).getChildren().get(2);
            stackPane.getStyleClass().clear();
            if (i == selectedInList) {
                stackPane.getStyleClass().add("selected-date-box");
            } else {
                stackPane.getStyleClass().add("available-date-box");
            }
        }
    }

    private void loadRelevantBikeReservations() {
        List<BikeReservation> allReservations = BikeReservationCommunication.getAllReservations();
        List<BikeReservation> res = new ArrayList<>();
        Date dateObj = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy,HH:mm");
        String currentDate = dateFormat.format(dateObj);
        for (int i = 0; i < allReservations.size(); i++) {
            String date = allReservations.get(i).getDate() + "," + allReservations.get(i).getTimeSlot();
            if (date.compareTo(currentDate) > 0) {
                res.add(allReservations.get(i));
            }
        }
        relevantBikeReservations = res;
    }

    public Building getSelected() {
        return selected;
    }

    public void setSelected(Building selected) {
        this.selected = selected;
    }

    public int getSelectedInList() {
        return selectedInList;
    }

    public void setSelectedInList(int selectedInList) {
        this.selectedInList = selectedInList;
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Calendar day) {
        selectedDate = day;
    }

    public Calendar getTimeSelected() {
        return timeSelected;
    }

    public void setTimeSelected(Calendar time) {
        timeSelected = time;
    }

}
