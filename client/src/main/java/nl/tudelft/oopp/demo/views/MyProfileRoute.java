package nl.tudelft.oopp.demo.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyProfileRoute extends Route {
    private VBox rootElement;

    private HBox horizontalContainer;
    private VBox userDetails;
    private Text userRole;
    /**
     * Instantiates a new RoomReservationRoute which displays the options
     * to reserve a room.
     */
    public MyProfileRoute() {
        rootElement = new VBox();
        Boolean isAdmin = false;
        if (AuthenticationCommunication.myUserRole.equals("Admin")) {
            isAdmin = true;
        }
        AppBar appBar = new AppBar(isAdmin);
        rootElement.getChildren().add(appBar);
        addUserInformation();
    }

    public void addUserInformation() {
        horizontalContainer = new HBox();
        //Background background = new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY));
        //horizontalContainer.setBackground(background);
        horizontalContainer.setAlignment(Pos.TOP_LEFT);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(10);

        Image profileImage = new Image("/images/myProfile.png");
        RectangularImageButton profilePicture = new RectangularImageButton(profileImage, "");
        profilePicture.setFitHeight(100);
        horizontalContainer.getChildren().add(profilePicture);

        userDetails = new VBox();
        userDetails.setAlignment(Pos.CENTER_LEFT);
        userDetails.setPadding(new Insets(16, 16, 16, 16));
        userDetails.setSpacing(10);

        userDetails.getChildren().add(oneBoldOneRegular
                ("Username: ", AuthenticationCommunication.myUsername));
        userDetails.getChildren().add(oneBoldOneRegular
                ("Role: ", AuthenticationCommunication.myUserRole));

        horizontalContainer.getChildren().add(userDetails);
        rootElement.getChildren().add(horizontalContainer);
    }

    public TextFlow oneBoldOneRegular(String boldString, String regularString) {
        TextFlow boldAndRegular = new TextFlow();
        Text boldText = new Text(boldString);
        boldText.setStyle("-fx-font-weight: bold");
        Text regularText = new Text(regularString);
        boldAndRegular.getChildren().addAll(boldText, regularText);
        return boldAndRegular;
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
