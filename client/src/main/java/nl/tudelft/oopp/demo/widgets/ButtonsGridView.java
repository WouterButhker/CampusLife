package nl.tudelft.oopp.demo.widgets;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.entities.Room;

public class ButtonsGridView extends GridPane {
    private List<RectangularImageButton> buttons;
    private Listener listener;
    private int buttonsPerRow;

    /**
     * Creates a GridView that displays buttons
     * that can be pressed and throw an event.
     * @param images the list of images to be displayed
     * @param titles the list of titles to be displayed
     */
    public ButtonsGridView(List<Image> images, List<String> titles, int buttonsPerRow) {
        this.buttonsPerRow = buttonsPerRow;
        buttons = new ArrayList<>();
        setButtons(images, titles);

        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth);
        });
    }

    /**
     * Method to update the buttons.
     * @param images the list of images to be displayed
     * @param titles the list of titles to be displayed
     */
    public void setButtons(List<Image> images, List<String> titles) {
        this.getChildren().clear();
        buttons.clear();

        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            RectangularImageButton button = new RectangularImageButton(image, titles.get(i));
            this.add(button, i % buttonsPerRow, i / buttonsPerRow, 1, 1);
            buttons.add(button);

            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (listener != null) {
                        listener.onButtonClicked(finalI);
                    }
                }
            });
        }

        resizeDisplay(getPrefWidth());
    }

    /**
     * Sets the listener for click events.
     * @param listener the listener
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void resizeDisplay(Number newWidth) {
        double buttonWidth = newWidth.doubleValue() / (buttonsPerRow + 1);
        double spacing = (newWidth.doubleValue() - buttonWidth * buttonsPerRow)
                / (buttonsPerRow + 1);
        for (RectangularImageButton button : buttons) {
            button.setFitWidth(buttonWidth);
        }
        setPadding(new Insets(0, 0, 0, spacing));
        setHgap(spacing);
        setVgap(spacing);
    }

    public interface Listener {
        void onButtonClicked(int buttonIndex);
    }
}
