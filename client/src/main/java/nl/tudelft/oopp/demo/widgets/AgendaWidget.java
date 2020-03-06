package nl.tudelft.oopp.demo.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AgendaWidget extends VBox {
    private static final int NUM_BLOCKS = 5;

    private Listener listener;

    private int topBlock = 10;
    private int from = 0;
    private int to = 23;

    private int selectedBlock = -1;

    private HBox buttonsContainer;
    private Button upButton;
    private Button downButton;

    private List<AgendaBlock> agendaBlocks = new ArrayList<>();

    public AgendaWidget(Listener listener) {
        this.listener = listener;

        setStyle("-fx-background-color: -primary-color-light;");
        setPadding(new Insets(8));
        setSpacing(8);
        setAlignment(Pos.CENTER);
        for (int i = 0; i < NUM_BLOCKS; i++) {
            AgendaBlock agendaBlock = new AgendaBlock(String.format("%d:00", i), true);
            int finalI = i;
            agendaBlock.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    selectedBlock = topBlock + finalI;
                    redrawBlocks();

                    if (listener != null) {
                        listener.onBlockSelected(topBlock + finalI, topBlock + finalI + 1);
                    }
                }
            });
            agendaBlocks.add(agendaBlock);
            getChildren().add(agendaBlock);
        }

        buttonsContainer = new HBox();
        buttonsContainer.setAlignment(Pos.CENTER);

        upButton = new Button();
        upButton.getStyleClass().add("up-arrow");
        upButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scrollUp();
            }
        });

        downButton = new Button();
        downButton.getStyleClass().add("down-arrow");
        downButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scrollDown();
            }
        });

        buttonsContainer.getChildren().addAll(upButton, downButton);
        getChildren().add(buttonsContainer);

        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeWidth(newWidth.doubleValue());
        });
        prefHeightProperty().addListener((obs, oldHeight, newHeight) -> {
            resizeHeight(newHeight.doubleValue());
        });

        redrawBlocks();
    }

    public void removeSelection() {
        selectedBlock = -1;
    }

    private void scrollDown() {
        if (topBlock + NUM_BLOCKS <= to) {
            topBlock += 1;
            redrawBlocks();
        }
    }

    private void scrollUp() {
        if (topBlock > 0) {
            topBlock -= 1;
            redrawBlocks();
        }
    }

    private void redrawBlocks() {
        for (int i = 0; i < NUM_BLOCKS; i++) {
            agendaBlocks.get(i).setTime(String.format("%d:00", topBlock + i));
            if ((topBlock + i) == selectedBlock) {
                agendaBlocks.get(i).setSelected(true);
            } else {
                agendaBlocks.get(i).setSelected(false);
            }
        }
    }

    private void resizeWidth(double width) {
        for (AgendaBlock agendaBlock : agendaBlocks) {
            agendaBlock.setPrefWidth(width);
        }
    }

    private void resizeHeight(double height) {
        double buttonsScale = 0.1;
        double buttonsSize = height * buttonsScale;
        upButton.setPrefWidth(buttonsSize);
        upButton.setPrefHeight(buttonsSize);
        downButton.setPrefWidth(buttonsSize);
        downButton.setPrefHeight(buttonsSize);

        double restSize = height * (1 - buttonsScale) - 16;

        for (AgendaBlock agendaBlock : agendaBlocks) {
            agendaBlock.setPrefHeight(restSize / NUM_BLOCKS);
        }
    }

    private class AgendaBlock extends StackPane {
        private VBox background;
        private Rectangle backgroundRectangle;
        private Text time;
        private VBox timeContainer;
        private Rectangle hoverGlow;
        private VBox hoverGlowContainer;

        private boolean isAvailable;
        private boolean isSelected = false;

        public AgendaBlock(String time, boolean available) {
            this.isAvailable = available;

            backgroundRectangle = new Rectangle();
            backgroundRectangle.setFill(Color.TRANSPARENT);
            background = new VBox();
            background.getChildren().add(backgroundRectangle);
            getChildren().add(background);

            this.time = new Text(time);
            timeContainer = new VBox();
            timeContainer.setPadding(new Insets(16));
            timeContainer.getChildren().add(this.time);
            getChildren().add(timeContainer);

            hoverGlow = new Rectangle();
            hoverGlow.setFill(Color.TRANSPARENT);
            hoverGlowContainer = new VBox();
            hoverGlowContainer.getChildren().add(hoverGlow);
            hoverGlowContainer.getStyleClass().add("hover-glow");
            hoverGlowContainer.setVisible(false);
            getChildren().add(hoverGlowContainer);

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverGlowContainer.setVisible(true);
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverGlowContainer.setVisible(false);
                }
            });

            prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
                backgroundRectangle.setWidth(newWidth.doubleValue());

                hoverGlow.setWidth(newWidth.doubleValue());
            });
            prefHeightProperty().addListener((obs, oldHeight, newHeight) -> {
                backgroundRectangle.setHeight(newHeight.doubleValue());

                hoverGlow.setHeight(newHeight.doubleValue());
            });

            recolor();
        }

        public void setAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            recolor();
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
            recolor();
        }

        private void recolor() {
            timeContainer.getStyleClass().clear();
            if (isSelected) {
                timeContainer.getStyleClass().add("selected-agenda-block");
            } else if (isAvailable) {
                timeContainer.getStyleClass().add("available-agenda-block");
            } else {
                timeContainer.getStyleClass().add("unavailable-agenda-block");
            }
        }

        public void setTime(String time) {
            this.time.setText(time);
        }
    }

    public interface Listener {
        void onBlockSelected(int begin, int end);
    }
}
