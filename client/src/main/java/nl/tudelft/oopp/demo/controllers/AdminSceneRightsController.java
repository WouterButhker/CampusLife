package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.PopupWidget;

public class AdminSceneRightsController implements Initializable {

    @FXML
    private VBox mainBox;

    @FXML
    private TextField searchInput;

    @FXML
    private VBox usersBox;

    @FXML
    private Button search;

    @FXML
    private Button reset;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAppBar();
        loadUsers(null);
        addStyle();
        setGlobalEventHandler(mainBox);
    }

    private void addStyle() {
        mainBox.getStylesheets().add("css/admin-scene.css");
        mainBox.setStyle("-fx-background-color: -primary-color-light");
        search.getStyleClass().add("adminButtonSmall");
        reset.getStyleClass().add("adminButtonSmall");
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    @FXML
    private void resetUsers() {
        loadUsers(null);
        searchInput.setText("");
    }

    @FXML
    private void loadUsers() {
        loadUsers(searchInput.getText());
    }

    private void loadUsers(String search) {
        usersBox.getChildren().clear();
        List<User> users = UserCommunication.getAllUsers();
        if (users == null) {
            return;
        }

        if (search != null && !search.equals("")) {
            users = searchUsers(search, users);
        }

        for (int i = 0; i < users.size(); i++) {
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER_LEFT);
            box.getStyleClass().add("boxContainer");
            box.setPrefHeight(50);
            User user = users.get(i);
            Label usernameText = new Label(" Username : " + user.getUsername());
            Label role = new Label("Current role : " + user.getRole());
            usernameText.setStyle("-fx-font-size: 18");
            usernameText.setPrefWidth(225);
            role.setStyle("-fx-font-size: 18");
            role.setPrefWidth(200);
            ChoiceBox<String> options = new ChoiceBox<>();
            loadRights(options);
            options.setPrefWidth(100);
            options.setValue(user.getRole());
            HBox.setMargin(options, new Insets(0, 10, 0,10));
            Button confirm = new Button("Confirm");
            confirm.getStyleClass().add("adminButtonSmall");
            confirm.setStyle("-fx-font-size: 14");
            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateUserRights(user, options.getValue());
                    loadUsers(search);
                }
            });
            HBox.setMargin(confirm, new Insets(0, 0, 0,10));
            Button delete = new Button("Delete");
            delete.getStyleClass().add("adminButtonSmall");
            delete.setStyle("-fx-font-size: 14");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    boolean confirmation = PopupWidget.displayBool("Are you sure about deleting "
                            + "this?\nThe change will be irreversible.", "Confirmation");
                    if (confirmation) {
                        deleteUser(user.getId());
                        loadUsers(search);
                    }
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

    private List<User> searchUsers(String search, List<User> users) {
        List<User> res = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().contains(search)) {
                res.add(users.get(i));
            }
        }
        return res;
    }

    private void updateUserRights(User user, String role) {
        user.setRole(role);
        UserCommunication.updateUserRole(user);
    }

    private void deleteUser(Integer id) {
        UserCommunication.deleteUser(id);
    }

    //if enterKey is pressed, then loadUsers with search is called
    private void setGlobalEventHandler(Node root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loadUsers(searchInput.getText());
                ev.consume();
            }
        });
    }
}
