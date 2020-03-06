package nl.tudelft.oopp.demo.views;

import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.CalendarWidget;

public class RoomReservationRoute extends Route {
    private VBox rootElement;

    private HBox horizontalContainer;
    private DatePicker datePicker;
    private TextField timeBegin;
    private TextField timeEnd;
    private Button submitButton;

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
        rootElement.getChildren().add(horizontalContainer);
        datePicker = new DatePicker();
        horizontalContainer.getChildren().add(datePicker);

        timeBegin = new TextField();
        horizontalContainer.getChildren().add(timeBegin);

        timeEnd = new TextField();
        horizontalContainer.getChildren().add(timeEnd);

        submitButton = new Button("Reserve");
        submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button button = (Button) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();

                LocalDate date = datePicker.getValue();
                String beginTime = timeBegin.getText();
                String endTime = timeEnd.getText();

                // TODO make a call to make reservation in the back-end
                ReservationCommunication.addReservationToDatabase(4,
                        roomCode, beginTime + "-" + endTime);

            }
        });
        horizontalContainer.getChildren().add(submitButton);

        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(8);

        CalendarWidget calendarWidget = new CalendarWidget();
        calendarWidget.setMaxWidth(500);
        rootElement.getChildren().add(calendarWidget);
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
