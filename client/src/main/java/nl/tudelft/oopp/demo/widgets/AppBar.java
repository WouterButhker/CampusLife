package nl.tudelft.oopp.demo.widgets;

import java.io.IOException;
import java.net.URL;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.views.MainApplication;
import nl.tudelft.oopp.demo.views.MyProfileRoute;

public class AppBar extends StackPane {
    private HBox titleContainer;
    private HBox leftContainer;
    private HBox rightContainer;

    private Hyperlink backButton;
    private Hyperlink profileButton;
    private Text universityTitle;
    private Hyperlink adminScreenLink;
    private Rectangle separator;
    private Rectangle leftSeparator;
    private Hyperlink signOutLink;

    /**
     * Creates an AppBar without the Admin Mode link.
     */
    public AppBar() {
        addComponents();
        adminScreenLink.setVisible(false);
        separator.setVisible(false);
    }

    /**
     * Creates an AppBar with or without the Admin Mode link.
     * @param hasAdminMode true if the Admin Mode link should be displayed
     */
    public AppBar(boolean hasAdminMode, boolean hasBack, boolean hasProfile) {
        addComponents();
        adminScreenLink.setVisible(hasAdminMode);
        separator.setVisible(hasAdminMode);
        if (!hasBack) {
            leftContainer.getChildren().remove(backButton);
        }
        if (hasAdminMode && !hasBack) {
            leftContainer.getChildren().remove(leftSeparator);
        }

        if (!hasProfile) {
            profileButton.setVisible(false);
            leftSeparator.setVisible(false);
        }
    }

    private void addComponents() {
        setStyle("-fx-background-color: -primary-color");
        setAlignment(Pos.CENTER_LEFT);

        universityTitle = new Text(MainApplication.universityTitle);
        universityTitle.getStyleClass().add("app-bar-title");
        titleContainer = new HBox();
        titleContainer.getChildren().add(universityTitle);
        titleContainer.setAlignment(Pos.CENTER);
        getChildren().add(titleContainer);

        rightContainer = new HBox();
        rightContainer.setAlignment(Pos.CENTER_RIGHT);
        adminScreenLink = new Hyperlink("Admin Mode");
        adminScreenLink.getStyleClass().add("app-bar-link-style");
        adminScreenLink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RoutingScene routingScene = (RoutingScene) getScene();
                try {
                    URL xmlUrl = getClass().getResource("/AdminScene.fxml");
                    routingScene.pushRoute(new XmlRoute(xmlUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        rightContainer.getChildren().add(adminScreenLink);

        separator = new Rectangle();
        separator.setFill(Color.LIGHTGRAY);
        separator.setWidth(1);
        rightContainer.getChildren().add(separator);

        signOutLink = new Hyperlink("Sign Out");
        signOutLink.getStyleClass().add("app-bar-link-style");
        signOutLink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RoutingScene routingScene = (RoutingScene) getScene();
                routingScene.popAll();
                AuthenticationCommunication.logout();
            }
        });
        rightContainer.getChildren().add(signOutLink);
        rightContainer.setPadding(new Insets(0, 20, 0, 0));
        getChildren().add(rightContainer);

        backButton = new Hyperlink("Back");
        backButton.getStyleClass().add("app-bar-link-style");
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RoutingScene routingScene = (RoutingScene) getScene();
                try {
                    routingScene.popRoute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        leftSeparator = new Rectangle();
        leftSeparator.setFill(Color.LIGHTGRAY);
        leftSeparator.setWidth(1);
        leftSeparator.setVisible(true);

        profileButton = new Hyperlink("My Profile");
        profileButton.getStyleClass().add("app-bar-link-style");
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RoutingScene routingScene = (RoutingScene) getScene();
                try {
                    routingScene.pushRoute(new MyProfileRoute());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        leftContainer = new HBox();
        leftContainer.getChildren().add(backButton);
        leftContainer.getChildren().add(leftSeparator);
        leftContainer.getChildren().add(profileButton);
        leftContainer.setAlignment(Pos.CENTER_LEFT);
        getChildren().add(leftContainer);

        sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth(), newScene.getHeight());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth, newScene.getHeight());
                });
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    resizeDisplay(newScene.getWidth(), newHeight);
                });
            }
        });
    }

    private void resizeDisplay(Number newWidth, Number newHeight) {
        double appBarHeight = newHeight.doubleValue() * 0.1;

        titleContainer.setPrefWidth(newWidth.doubleValue());
        titleContainer.setPrefHeight(appBarHeight);

        leftContainer.setMaxWidth(newWidth.doubleValue() * 0.5);
        leftContainer.setPrefHeight(appBarHeight);

        rightContainer.setPrefWidth(newWidth.doubleValue());
        rightContainer.setPrefHeight(appBarHeight);
        
        separator.setHeight(appBarHeight * 0.7);
        leftSeparator.setHeight(appBarHeight * 0.7);

        double verticalPadding = appBarHeight * 0.1;
        double horizontalPadding = verticalPadding;
        setPrefWidth(newWidth.doubleValue());
        setPrefHeight(appBarHeight);
        setPadding(new Insets(
                verticalPadding,
                horizontalPadding,
                verticalPadding,
                horizontalPadding));

        universityTitle.setStyle("-fx-font-size:" + (appBarHeight * 0.5));

        double linksSize = Math.min(40, appBarHeight * 0.32);
        backButton.setStyle("-fx-font-size:" + linksSize);
        profileButton.setStyle("-fx-font-size:" + linksSize);
        adminScreenLink.setStyle("-fx-font-size:" + linksSize);
        signOutLink.setStyle("-fx-font-size:" + linksSize);
    }
}
