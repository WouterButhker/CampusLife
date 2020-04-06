package nl.tudelft.oopp.demo.views;

import static nl.tudelft.oopp.demo.communication.ImageCommunication.updateUserImage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.reservation.BikeReservationCommunication;
import nl.tudelft.oopp.demo.communication.reservation.FoodOrderCommunication;
import nl.tudelft.oopp.demo.communication.reservation.PersonalReservationCommunication;
import nl.tudelft.oopp.demo.communication.reservation.RoomReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.widgets.*;

public class MyProfileRoute extends Route {
    private VBox rootElement;
    private VBox userDetails;
    private VBox eventContainer;
    private VBox newEventBox;

    private HBox horizontalContainer;

    ToggleButton upcoming;
    ToggleButton past;
    ToggleButton newEvent;
    ToggleButton foodToggle;
    private Rectangle rect;

    private List<Reservation> pastReservations;
    private List<Reservation> futureReservations;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd,HH:mm");
    private String currentDate;

    private ScrollPane scrollPane;

    /**
     * Instantiates a new MyProfileRoute.
     */
    public MyProfileRoute() {
        rootElement = new VBox();
        Boolean isAdmin = false;
        if (AuthenticationCommunication.myUserRole.equals("Admin")) {
            isAdmin = true;
        }
        AppBar appBar = new AppBar(isAdmin, true, false);
        rootElement.getChildren().add(appBar);
        addUserInformation();
        eventContainer = new VBox();
        eventContainer.setPadding(new Insets(0, 16, 16, 16));
        addToggleButton();
        rootElement.getChildren().addAll(eventContainer);
    }

    private void addToggleButton() {

        final ToggleGroup group = new ToggleGroup();
        upcoming = new ToggleButton("Upcoming events");
        upcoming.getStyleClass().add("profile-page-tab");
        upcoming.getStyleClass().add("profile-page-tab-hov");
        upcoming.setSelected(true);
        upcoming.setToggleGroup(group);
        past = new ToggleButton("Past events");
        past.getStyleClass().add("profile-page-tab");
        past.getStyleClass().add("profile-page-tab-hov");
        past.setToggleGroup(group);
        newEvent = new ToggleButton("New event");
        newEvent.getStyleClass().add("profile-page-tab");
        newEvent.getStyleClass().add("profile-page-tab-hov");
        newEvent.setToggleGroup(group);
        foodToggle = new ToggleButton("Food orders");
        foodToggle.getStyleClass().add("profile-page-tab");
        foodToggle.getStyleClass().add("profile-page-tab-hov");
        foodToggle.setToggleGroup(group);

        past.setUserData(Color.LIGHTBLUE);
        newEvent.setUserData(Color.SALMON);

        rect = new Rectangle(145, 50);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle newToggle) {

                if (!upcoming.isSelected() && !past.isSelected()
                        && !newEvent.isSelected() && !foodToggle.isSelected()) {
                    upcoming.setSelected(true);
                }

                if (past.isSelected()) {
                    displayPastEvents();
                } else if (newEvent.isSelected()) {
                    displayNewEvent();
                } else if (upcoming.isSelected()) {
                    displayUpcomingEvents();
                } else {
                    displayFoodOrders();
                }
            }
        });
        HBox toggleGroupBox = new HBox();
        toggleGroupBox.getChildren().addAll(upcoming, past, newEvent, foodToggle);
        eventContainer.getChildren().addAll(toggleGroupBox, rect);
        displayUpcomingEvents();
    }

    private void removeObject(Object obj) {
        if (eventContainer.getChildren().contains(obj)) {
            eventContainer.getChildren().remove(eventContainer.getChildren().indexOf(obj));
        }
    }

    private void cleanBeforeDisplaying() {
        removeObject(rect);
        removeObject(scrollPane);
        removeObject(newEventBox);
    }

    private void sortMyReservationsByDate() {
        pastReservations = new ArrayList<>();
        futureReservations = new ArrayList<>();
        Date dateObj = new Date();
        currentDate = dateFormat.format(dateObj);

        List<Reservation> allMyReservations = new ArrayList<>();

        List<RoomReservation> allMyRoomReservations =
                RoomReservationCommunication.getMyReservations();
        allMyReservations.addAll(allMyRoomReservations);

        List<BikeReservation> allMyBikeReservations =
                BikeReservationCommunication.getMyReservations();
        allMyReservations.addAll(allMyBikeReservations);

        List<PersonalReservation> allMyPersonalReservations =
                PersonalReservationCommunication.getMyReservations();
        allMyReservations.addAll(allMyPersonalReservations);

        for (Reservation reservation : allMyReservations) {
            String endTime = "";
            if (reservation instanceof BikeReservation) {
                BikeReservation bikeReservation = (BikeReservation) reservation;
                endTime = bikeReservation.getDate() + ","
                        + bikeReservation.getTimeSlot().substring(6);
            } else {
                endTime = reservation.getTimeSlot().substring(19);
            }

            String sortingEndTime = endTime.substring(6, 10) + "/"
                    + endTime.substring(3, 5) + "/"
                    + endTime.substring(0, 2) + ","
                    + endTime.substring(11);
            if (sortingEndTime.compareTo(currentDate) <= 0) {
                pastReservations.add(reservation);
            } else {
                futureReservations.add(reservation);
            }
        }
    }

    private Rectangle makeSeparator(double size) {
        Rectangle separator = new Rectangle();
        separator.setFill(Color.GRAY);
        separator.setWidth(1);
        separator.setVisible(true);
        separator.setHeight(size);
        return separator;
    }

    private void makeListOfReservations(List<Reservation> reservations) {
        scrollPane = new ScrollPane();
        VBox reservationsList = new VBox();
        reservationsList.setSpacing(2);

        for (int i = 0; i < reservations.size(); i++) {
            HBox currentReservation = new HBox();
            Reservation reservation = reservations.get(i);
            ReservationItem reservationInformation;

            if (reservation instanceof RoomReservation) {
                reservationInformation = new ReservationItem((RoomReservation) reservation);
            } else if (reservation instanceof BikeReservation) {
                reservationInformation = new ReservationItem((BikeReservation) reservation);
            } else {
                reservationInformation = new ReservationItem((PersonalReservation) reservation);
            }


            currentReservation.getChildren().add(reservationInformation);

            String styleHovered = "-fx-background-color:#cf3229;"
                    + "-fx-text-fill: white; -fx-font-size: 16;";
            String style = "-fx-background-color:#e4685d;"
                    + "-fx-text-fill: white; -fx-font-size: 16;";
            int finalI = i;
            Button delete = new Button("Delete");

            delete.setStyle(style);
            delete.setOnMouseEntered(event -> delete.setStyle(styleHovered));
            delete.setOnMouseExited(event -> delete.setStyle(style));

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (PopupWidget.displayBool("Are you sure you want to delete this reservation?"
                            + "\nThis will be irreversible", "Are you sure?")) {
                        int id = reservations.get(finalI).getId();
                        if (reservation instanceof RoomReservation) {
                            RoomReservationCommunication.deleteReservationFromDatabase(id);
                        } else if (reservation instanceof BikeReservation) {
                            BikeReservationCommunication.deleteBikeReservation(id);
                        } else if (reservation instanceof PersonalReservation) {
                            PersonalReservationCommunication.deleteReservationFromDatabase(id);
                        }

                        if (past.isSelected()) {
                            displayPastEvents();
                        } else if (upcoming.isSelected()) {
                            displayUpcomingEvents();
                        }
                    }
                }
            });
            StackPane deletePane = new StackPane(delete);
            deletePane.setAlignment(Pos.CENTER_RIGHT);

            currentReservation.getChildren().add(deletePane);
            reservationsList.getChildren().add(currentReservation);
            Separator separator = new Separator();
            separator.setPrefWidth(500);
            reservationsList.getChildren().add(separator);
        }
        scrollPane.setContent(reservationsList);
        scrollPane.fitToWidthProperty().set(true);
    }

    private void displayUpcomingEvents() {
        cleanBeforeDisplaying();
        sortMyReservationsByDate();
        makeListOfReservations(futureReservations);
        eventContainer.getChildren().addAll(scrollPane);
    }

    private void displayPastEvents() {
        cleanBeforeDisplaying();
        sortMyReservationsByDate();
        makeListOfReservations(pastReservations);
        eventContainer.getChildren().addAll(scrollPane);
    }

    private void displayNewEvent() {
        cleanBeforeDisplaying();
        newEventBox = new VBox();
        newEventBox.setMaxWidth(eventContainer.getWidth() / 2);
        newEventBox.setSpacing(3);
        newEventBox.setPadding(new Insets(0, 16, 16, 0));
        TextField t1 = new TextField();
        t1.setPromptText("Type");
        TextField t2 = new TextField();
        t2.setPromptText("DD/MM/YYYY");
        TextField t3 = new TextField();
        t3.setPromptText("HH:MM");
        TextField t4 = new TextField();
        t4.setPromptText("HH:MM");
        Button submit = new Button("Submit");
        Text txt1 = new Text("Type");
        Text txt2 = new Text("Date");
        Text txt3 = new Text("Start Time");
        Text txt4 = new Text("End Time");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String activity = t1.getText();
                String timeslot = t2.getText() + "," + t3.getText()
                        + " - " + t2.getText() + "," + t4.getText();
                if (t1.getText() != null
                        && t2.getText() != null
                        && t3.getText() != null
                        && t4.getText() != null) {
                    PersonalReservationCommunication.addReservationToDatabase(
                            AuthenticationCommunication.myUserId, timeslot, activity);
                }

            }
        });
        newEventBox.getChildren().addAll(txt1, t1, txt2, t2, txt3, t3, txt4, t4, submit);
        eventContainer.getChildren().addAll(newEventBox);
    }

    private void displayFoodOrders() {
        cleanBeforeDisplaying();

        List<FoodOrder> foodOrders = FoodOrderCommunication.getAll();

        VBox list = new VBox();
        for (int i = 0; i < foodOrders.size(); i++) {
            FoodOrder foodOrder = foodOrders.get(i);
            list.getChildren().add(new FoodOrderItem(foodOrder));

            Rectangle separator = new Rectangle();
            separator.setWidth(500);
            separator.setHeight(1);
            separator.setFill(Color.LIGHTGRAY);
            list.getChildren().add(separator);
        }

        scrollPane = new ScrollPane(list);
        eventContainer.getChildren().add(scrollPane);
    }

    private void addUserInformation() {
        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.TOP_LEFT);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(10);
        loadHorizontalContainer();

        //Integer rand = Math.abs(new Random().nextInt()) % AuthenticationCommunication.ids.size();
        //System.out.println(rand);
        //String imageId = AuthenticationCommunication.ids.get(rand);


        rootElement.getChildren().add(horizontalContainer);
    }

    private void loadHorizontalContainer() {
        Image profileImage = new Image(AuthenticationCommunication.myImageUrl);
        RectangularImageButton profilePicture = new RectangularImageButton(profileImage, "");
        profilePicture.setFitHeight(90);
        horizontalContainer.getChildren().add(profilePicture);

        userDetails = new VBox();
        userDetails.setAlignment(Pos.CENTER_LEFT);
        userDetails.setPadding(new Insets(16, 16, 16, 16));
        userDetails.setSpacing(10);

        userDetails.getChildren().add(oneBoldOneRegular(
                "Username: ", AuthenticationCommunication.myUsername));
        userDetails.getChildren().add(oneBoldOneRegular(
                "Role: ", AuthenticationCommunication.myUserRole));

        ImageSelectorWidget imageSelectorWidget = new ImageSelectorWidget();

        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateUserImage(imageSelectorWidget.getImage());
                horizontalContainer.getChildren().clear();
                imageSelectorWidget.removeChild(save);
                loadHorizontalContainer();
            }
        });
        imageSelectorWidget.addChild(save);
        Pane spacer = new Pane();
        spacer.setPrefWidth(63);
        Button changePassword = new Button("Change Password");
        changePassword.setPadding(new Insets(3, 10, 3, 10));
        changePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PopupWidget.displayPasswordChange(AuthenticationCommunication.myUsername);
            }
        });
        horizontalContainer.getChildren().addAll(
                userDetails, imageSelectorWidget, spacer, changePassword);
        //
    }

    private TextFlow oneBoldOneRegular(String boldString, String regularString) {
        TextFlow boldAndRegular = new TextFlow();
        Text boldText = new Text(boldString);
        boldText.setStyle("-fx-font-weight: bold");
        Text regularText = new Text(regularString);
        boldAndRegular.getChildren().addAll(boldText, regularText);
        return boldAndRegular;
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
