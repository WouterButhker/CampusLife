package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.widgets.AppBar;

public class AdminSceneRightsController implements Initializable {

    @FXML
    private VBox mainBox;

    @FXML
    private TextField searchInput;

    @FXML
    private VBox usersBox;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAppBar();
        loadUsers(null);
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    @FXML
    private void loadUsers() {
        loadUsers(searchInput.getText());
    }

    @FXML
    private void resetUsers() {
        loadUsers(null);
    }

    private void loadUsers(String search) {
        usersBox.getChildren().clear();
        //List<User> users = UserCommunication.getAllUsers();
        int test = 15;
        if (search != null && !search.equals("")) {
            //users = searchUsers(search, users);
            test = 6;
        }

        for (int i = 0; i < test; i++) { //users.size()
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER_LEFT);
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;";
            box.setStyle(css);
            box.setPrefHeight(50);
            Label usernameText = new Label(" Username : " + i); //user.getUsername();
            Label role = new Label("Current role : " + "Student"); //user.getRole()
            usernameText.setStyle("-fx-font-size: 18");
            usernameText.setPrefWidth(250);
            role.setStyle("-fx-font-size: 18");
            role.setPrefWidth(175);
            ChoiceBox<String> options = new ChoiceBox<>();
            loadRights(options);
            options.setPrefWidth(100);
            //options.setValue(user.getRole());
            HBox.setMargin(options, new Insets(0, 10, 0,10));
            Button confirm = new Button("Confirm");
            confirm.setStyle("-fx-font-size: 14");
            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //update user rights
                    updateUserRights();
                    loadUsers(search);
                }
            });
            HBox.setMargin(confirm, new Insets(0, 0, 0,10));
            Button delete = new Button("Delete");
            delete.setStyle("-fx-font-size: 14");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //delete user
                    deleteUser();
                    loadUsers(search);
                }
            });

            HBox.setMargin(delete, new Insets(0, 0, 0,50));
            box.getChildren().addAll(usernameText, role, options, confirm, delete);
            usersBox.getChildren().add(box);
        }
    }

    private void loadRights(ChoiceBox choiceBox) {
        String[] rights = new String[3];
        rights[0] = "Student";
        rights[1] = "Employee";
        rights[2] = "Admin";
        choiceBox.getItems().addAll(rights);
    }

//    private List<User> searchUsers(String search, List<User> users) {
//        List<User> res = new ArrayList<User>;
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getUsername.contains(search)) {
//                res.add(users.get(i));
//            }
//        }
//        return res;
//    }
    private void updateUserRights() {

    }

    private void deleteUser() {

    }
}
