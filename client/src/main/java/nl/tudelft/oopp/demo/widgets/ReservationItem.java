package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;

public class ReservationItem extends Group {
    private Reservation reservation;

    private VBox mainContainer;

    /**
     * Creates a Reservation item that displays a RoomReservation.
     * @param reservation the reservation whose information will be displayed
     */
    public ReservationItem(RoomReservation reservation) {
        this.reservation = reservation;

        Label reservationType = new Label("Room reservation:");

        reservationType.setStyle("-fx-font-family: -primary-font-name;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 18;");
        reservationType.setPadding(new Insets(10, 0, 10,9));

        Label reservationData = new Label("Building: "
                + reservation.getRoom().getBuilding().getName() + " Room: "
                + reservation.getRoom().getName());

        reservationData.setStyle("-fx-text-fill: #9E9E9E;"
                + "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;");
        reservationData.setPadding(new Insets(5, 0, 0,9));

        Label dateAndTime = new Label(reservation.getTimeSlot());

        dateAndTime.setStyle("-fx-text-fill: #9E9E9E;"
                + "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;");
        dateAndTime.setPadding(new Insets(5, 0, 0,9));


        mainContainer = new VBox();
        mainContainer.getChildren().addAll(reservationType, reservationData, dateAndTime);
        mainContainer.setPrefWidth(500);

        getChildren().add(mainContainer);

    }

    /**
     * Creates a ReservationItem that displays a PersonalReservation.
     * @param reservation the reservation whose information will be displayed
     */
    public ReservationItem(PersonalReservation reservation) {
        this.reservation = reservation;

        Label reservationType = new Label("Personal Event:");

        reservationType.setStyle("-fx-font-family: -primary-font-name;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 18;");
        reservationType.setPadding(new Insets(10, 0, 10,9));

        Label reservationData = new Label(reservation.getActivity());

        reservationData.setStyle("-fx-text-fill: #9E9E9E;"
                + "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;");
        reservationData.setPadding(new Insets(5, 0, 0,9));

        Label dateAndTime = new Label(reservation.getTimeSlot());

        dateAndTime.setStyle("-fx-text-fill: #9E9E9E;"
                + "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;");
        dateAndTime.setPadding(new Insets(5, 0, 0,9));


        mainContainer = new VBox();
        mainContainer.getChildren().addAll(reservationType, reservationData, dateAndTime);
        mainContainer.setPrefWidth(500);

        getChildren().add(mainContainer);

    }

    /**
     * Creates a ReservationItem that displays a BikeReservation.
     * @param reservation the reservation whose information will be displayed
     */
    public ReservationItem(BikeReservation reservation) {
        this.reservation = reservation;

        Label reservationType = new Label("Bike reservation:");

        reservationType.setStyle("-fx-font-family: -primary-font-name;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 18;");
        reservationType.setPadding(new Insets(10, 0, 10,9));

        Label reservationData = new Label("Pick up Building: "
                + reservation.getPickUpBuilding().getName() + "Drop off Building: "
                + reservation.getDropOffBuilding().getName());

        reservationData.setStyle("-fx-text-fill: #9E9E9E;"
                + "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;");
        reservationData.setPadding(new Insets(5, 0, 0,9));

        Label dateAndTime = new Label(reservation.getTimeSlot());

        dateAndTime.setStyle("-fx-text-fill: #9E9E9E;"
                + "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;");
        dateAndTime.setPadding(new Insets(5, 0, 0,9));


        mainContainer = new VBox();
        mainContainer.getChildren().addAll(reservationType, reservationData, dateAndTime);
        mainContainer.setPrefWidth(500);

        getChildren().add(mainContainer);

    }
}
