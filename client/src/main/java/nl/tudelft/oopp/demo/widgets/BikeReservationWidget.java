package nl.tudelft.oopp.demo.widgets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Weekdays;


public class BikeReservationWidget extends VBox {

    private List<Building> buildingList;
    private Building selected;
    private int selectedInList;
    private List<BikeReservation> bikeReservations;

    private Label header;

    private ScrollPane scrollPane;
    private AnchorPane buildingDisplay;
    private VBox buildingDisplayList;
    private List<HBox> boxes;

    private AgendaWidget agendaWidget;

    private Calendar timeSelected;
    private Calendar selectedDate = Calendar.getInstance();

    private Listener listener;

    /**
     * Creates a new BikeReservationWidget that is used to reserve bikes.
     * @param text The header that is being displayed at the top of the widget
     */
    public BikeReservationWidget(String text) {
        setSelectedInList(-1);
        buildingList = BuildingCommunication.getAllBuildingsWithBikeStation();
        bikeReservations = BikeReservationCommunication.getAllRelevantReservations();
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
        scrollPane.setStyle("-fx-background-color: transparent");
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
                calculateNumBikes();
                listener.changed();
            }
        }, 4);

        this.getChildren().add(agendaWidget);


        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
        });

    }

    /**
     * Method that loads the buildings into the scroll pane of the widget.
     */
    public void loadBuildings() {
        int numBuildings = buildingList.size();
        buildingDisplayList.getChildren().clear();
        boxes.clear();
        for (int i = 0; i < numBuildings; i++) {
            HBox buildingBox = new HBox();
            buildingBox.setPrefWidth(scrollPane.getPrefWidth() + 12);
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
            //+22 for some weird correction??
            label.setPrefWidth(scrollPane.getPrefWidth() - 65 - 45 - 10 - 20 + 22);
            label.setPadding(new Insets(0, 0, 0, 10));

            StackPane buttonPane = new StackPane();
            buttonPane.setPrefSize(45, 40);
            buttonPane.setId(String.valueOf(i));
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
                    removeSelection();
                    setTimeSelected(null);
                    updateButtons();
                    calculateNumBikes();
                    listener.changed();
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
        setAvailabilities();
    }

    public void setAvailabilities() {
        agendaWidget.setAvailabilities(computeAvailabilities());
    }

    private boolean[] computeAvailabilities() {
        if (selected == null) {
            return new boolean[24];
        }
        Building building = selected;
        int day = selectedDate.get(Calendar.DAY_OF_WEEK);
        //some modulo shenanigans because weekDays[0] is monday but Calendar doesn't have the same
        day = day + 5;
        day = day % 7;
        Weekdays openingHoursWeek = new Weekdays(building.getOpeningHours());
        String openingHoursDay = openingHoursWeek.getWeekdays().get(day);
        //if closed
        if (openingHoursDay.equals(Weekdays.CLOSED)) {
            return new boolean[24];
        }
        String begin = openingHoursDay.split("-")[0];
        String end = openingHoursDay.split("-")[1];
        int beginTime = Integer.parseInt(begin.split(":")[0]);
        int endTime = Integer.parseInt(end.split(":")[0]);
        boolean[] res = new boolean[24];
        for (int i = 0; i < res.length; i++) {
            if (i >= beginTime && i < endTime) {
                res [i] = true;
            } else {
                res [i] = false;
            }
        }
        return res;
    }

    private void resizeDisplay(double newWidth) {
        setSpacing(newWidth * 0.08);
        setPadding(new Insets(newWidth * 0.08));

        buildingDisplay.setPrefWidth(newWidth * 0.8);
        scrollPane.setPrefWidth(newWidth * 0.8);

        for (HBox box : boxes) {
            box.setPrefWidth(scrollPane.getPrefWidth() + 12);
            Label label = (Label) box.getChildren().get(1);
            //-65 for the image width
            //-45 for the button width
            //-10 for the padding
            //-20 for the left and right padding of the total HBox
            //+22 for some weird correction??
            label.setPrefWidth(scrollPane.getPrefWidth() - 65 - 45 - 10 - 20 + 22);
        }

        header.setStyle("-fx-font-size:" + newWidth * 0.08);
    }

    public void removeSelection() {
        agendaWidget.removeSelection();
    }

    private void updateButtons() {
        for (int i = 0; i < boxes.size(); i++) {
            StackPane stackPane = (StackPane) boxes.get(i).getChildren().get(2);
            stackPane.getStyleClass().clear();
            if (i == selectedInList) {
                stackPane.getStyleClass().add("selected-date-box");
                stackPane.setDisable(false);
            } else {
                stackPane.getStyleClass().add("available-date-box");
                stackPane.setDisable(false);
            }
        }
    }

    /**
     * Method that calculates the current amount of bikes at all the buildings.
     */
    public void calculateNumBikes() {
        if (timeSelected == null) {
            for (int i = 0; i < boxes.size(); i++) {
                Label label = (Label) boxes.get(i).getChildren().get(1);
                String labelText = label.getText().split("bikes : ")[0];
                label.setText(labelText + "bikes : " + buildingList.get(i).getBikes());
            }
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        for (int i = 0; i < buildingList.size(); i++) {
            int bikes = buildingList.get(i).getBikes();
            Building building = buildingList.get(i);
            for (int j = 0; j < bikeReservations.size(); j++) {
                BikeReservation bikeReservation = bikeReservations.get(j);
                String[] times = bikeReservation.getTimeSlot().split("-");
                String pickUp = times[0];
                String dropOff = times[1];
                Date pickUpDate = null;
                Date dropOffDate = null;
                try {
                    pickUpDate = sdf.parse(bikeReservation.getDate() + "-" + pickUp);
                    dropOffDate = sdf.parse(bikeReservation.getDate() + "-" + dropOff);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //the pickup time is before the selected time
                if (timeSelected.getTime().before(pickUpDate)) {
                    //we don't do anything
                }
                //the bike isn't dropped off yet
                if (building.equals(bikeReservation.getDropOffBuilding())
                    && timeSelected.getTime().before(dropOffDate)) {
                    bikes--;
                }
                if (bikes < 0) {
                    bikes = 0;
                }
                Label label = (Label) boxes.get(i).getChildren().get(1);
                String labelText = label.getText().split("bikes : ")[0];
                label.setText(labelText + "bikes : " + bikes);
            }
        }
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public List<HBox> getBoxes() {
        return boxes;
    }

    public interface Listener {
        void changed();
    }
}
