package nl.tudelft.oopp.demo.views;

import java.time.LocalDate;
import java.util.Calendar;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.ReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AgendaWidget;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.CalendarWidget;
import nl.tudelft.oopp.demo.widgets.ReservationWidget;

public class RoomReservationRoute extends Route {
    private VBox rootElement;

    private HBox horizontalContainer;

    private CalendarWidget calendarWidget;
    private AgendaWidget agendaWidget;
    private ReservationWidget reservationWidget;

    private Calendar selectedDate = Calendar.getInstance();
    private Calendar fromTime;
    private Calendar toTime;

    /**
     * Instantiates a new RoomReservationRoute which displays the options
     * to reserve a room.
     * @param roomCode the code of the room to be reserved
     */
    public RoomReservationRoute(String roomCode) {
        rootElement = new VBox();

        AppBar appBar = new AppBar();
        rootElement.getChildren().add(appBar);

        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.CENTER);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(32);
        rootElement.getChildren().add(horizontalContainer);

        calendarWidget = new CalendarWidget();
        calendarWidget.setListener(new CalendarWidget.Listener() {
            @Override
            public void onDayClicked(Calendar day) {
                agendaWidget.removeSelection();
                selectedDate = day;
                reservationWidget.setPeriod(null, null);
            }
        });
        agendaWidget = new AgendaWidget(new AgendaWidget.Listener() {
            @Override
            public void onBlockSelected(int begin, int end) {
                fromTime = (Calendar) selectedDate.clone();
                fromTime.set(Calendar.HOUR_OF_DAY, begin);
                fromTime.set(Calendar.MINUTE, 0);
                toTime = (Calendar) selectedDate.clone();
                toTime.set(Calendar.HOUR_OF_DAY, end);
                toTime.set(Calendar.MINUTE, 0);

                reservationWidget.setPeriod(fromTime, toTime);
            }
        });
        reservationWidget = new ReservationWidget(new Room("PC1", "PC-Hall 1", 10, true, true, 1, 1));
        horizontalContainer.getChildren().add(calendarWidget);
        horizontalContainer.getChildren().add(agendaWidget);
        horizontalContainer.getChildren().add(reservationWidget);

        horizontalContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                agendaWidget.setPrefHeight(newScene.getHeight() * 0.65);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue());
                });
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    agendaWidget.setPrefHeight(newHeight.doubleValue() * 0.65);
                });
            }
        });
    }

    private void resizeDisplay(double newWidth) {
        calendarWidget.setPrefWidth(newWidth * 0.3);
        agendaWidget.setPrefWidth(newWidth * 0.3);
        reservationWidget.setPrefWidth(newWidth * 0.3);
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
