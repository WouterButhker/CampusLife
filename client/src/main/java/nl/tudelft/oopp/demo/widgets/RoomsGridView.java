package nl.tudelft.oopp.demo.widgets;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.entities.Room;

public class RoomsGridView extends GridPane {
    private List<Room> rooms;
    private List<RectangularImageButton> roomButtons;

    private Listener listener;

    /**
     * Creates the Grid View of the room list page.
     * @param rooms a list with all the rooms that need to be displayed
     */
    public RoomsGridView(List<Room> rooms) {
        this.rooms = rooms;

        roomButtons = new ArrayList<>();
        addButtons();

        double scalar = 1.8;

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth() * scalar);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue() * scalar);
                });
            }
        });
    }

    private final int roomsPerRow = 2;

    private void addButtons() {
        for (int i = 0; i < rooms.size(); i++) {
            Image image = new Image(
                    "https://cdn.mos.cms.futurecdn.net/K5nhgMGSRCzdppKW9bQcMd.jpg");
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