package nl.tudelft.oopp.demo.widgets;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.entities.Room;

public class RoomsGridView extends GridPane {
    private List<Room> rooms;
    private List<RectangularImageButton> roomButtons;

    private Listener listener;

    private final double scalar = 1.1;

    /**
     * Creates the Grid View of the room list page.
     * @param rooms a list with all the rooms that need to be displayed
     */
    public RoomsGridView(List<Room> rooms) {
        this.rooms = rooms;

        roomButtons = new ArrayList<>();
        addButtons();

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth() * scalar);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue() * scalar);
                });
            }
        });
    }

    /**
     * Method to update the rooms list for the user.
     * @param inputRooms the new rooms list that will replace the old list of rooms
     */
    public void setRooms(List<Room> inputRooms) {
        this.rooms = inputRooms;
        this.getChildren().clear();

        addButtons();

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth() * scalar);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue() * scalar);
                });
            }
        });
    }

    private final int roomsPerRow = 4;

    private void addButtons() {
        for (int i = 0; i < rooms.size(); i++) {
            Image image = new Image(ImageCommunication
                    .getRoomImageUrl(rooms.get(i).getRoomCode()).get(0));
            RectangularImageButton button = new RectangularImageButton(image,
                    rooms.get(i).getName());
            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (listener != null) {
                        listener.onRoomClicked(rooms.get(finalI));
                    }
                }
            });
            add(button, i % roomsPerRow, i / roomsPerRow, 1, 1);
            roomButtons.add(button);
        }
    }

    private void resizeDisplay(Number newWidth) {
        double buttonWidth = newWidth.doubleValue() / 6;
        double spacing = (newWidth.doubleValue() - buttonWidth * 5) / 6;
        for (int i = 0; i < roomButtons.size(); i++) {
            RectangularImageButton button = roomButtons.get(i);
            button.setFitWidth(buttonWidth);
        }
        setPadding(new Insets(0, 0, 0, spacing));
        setHgap(spacing);
        setVgap(spacing);
    }

    /**
     * Sets the listener of this RoomsGridView.
     * @param listener the listener
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onRoomClicked(Room room);
    }
}
