package nl.tudelft.oopp.demo.widgets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.entities.Room;

public class ReservationWidget extends VBox {
    private Room room;
    private Calendar from;
    private Calendar to;

    private Text roomName;
    private ImageView roomImage;

    private HBox dateContainer;
    private ImageView dateIcon;
    private Text dateText;

    private HBox timeContainer;
    private ImageView timeIcon;
    private Text timeText;

    private Button reserveButton;
    private Listener listener;
    private boolean reserveHidden = false;

    /**
     * Creates a new ReservationWidget.
     * @param room the room for which the reservation will be made
     * @param listener the listener who will listen to callbacks specified in the interface
     */
    public ReservationWidget(Room room, Listener listener) {
        this.room = room;

        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: -primary-color-light; -fx-background-radius: 8;");

        roomName = new Text(room.getName());
        roomName.getStyleClass().add("reservation-date-text");
        getChildren().add(roomName);
        Image image = new Image("https://cdn.mos.cms.futurecdn.net/K5nhgMGSRCzdppKW9bQcMd.jpg");
        roomImage = new ImageView(image);
        roomImage.setPreserveRatio(true);
        getChildren().add(roomImage);

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

        reserveButton = new Button("Reserve");
        reserveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listener.onReserveClicked();
            }
        });
        getChildren().add(reserveButton);

        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
        });



        updateDisplay();
    }

    public void setAvailable(boolean available) {
        reserveHidden = !available;
        reserveButton.setVisible(!reserveHidden);
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

    private void resizeDisplay(double newWidth) {
        setSpacing(newWidth * 0.08);
        setPadding(new Insets(newWidth * 0.08));

        roomName.setStyle("-fx-font-size:" + newWidth * 0.07);
        roomImage.setFitWidth(newWidth * 0.7);

        dateText.setStyle("-fx-font-size:" + newWidth * 0.05);
        timeText.setStyle("-fx-font-size:" + newWidth * 0.05);
    }

    private void updateDisplay() {
        if (from == null) {
            dateContainer.setVisible(false);
            timeContainer.setVisible(false);
            reserveButton.setVisible(false);
        } else {
            dateContainer.setVisible(true);
            timeContainer.setVisible(true);
            reserveButton.setVisible(!reserveHidden);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
            System.out.println(dateFormat.format(from.getTime()));
            dateText.setText(dateFormat.format(from.getTime()));

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String fromTime = timeFormat.format(from.getTime());
            String toTime = timeFormat.format(to.getTime());
            timeText.setText(String.format("%s - %s", fromTime, toTime));
        }
    }

    public interface Listener {
        void onReserveClicked();
    }
}
