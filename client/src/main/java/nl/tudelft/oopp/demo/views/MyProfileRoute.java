package nl.tudelft.oopp.demo.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AgendaWidget;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.CalendarWidget;
import nl.tudelft.oopp.demo.widgets.ReservationWidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyProfileRoute extends Route {
    private VBox rootElement;

    private HBox horizontalContainer;

    /**
     * Instantiates a new RoomReservationRoute which displays the options
     * to reserve a room.
     */
    public MyProfileRoute() {
        rootElement = new VBox();
        AppBar appBar = new AppBar();
        rootElement.getChildren().add(appBar);

        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.CENTER);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(32);
        rootElement.getChildren().add(horizontalContainer);

    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
