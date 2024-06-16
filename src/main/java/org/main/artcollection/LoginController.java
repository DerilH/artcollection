package org.main.artcollection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.main.artcollection.services.UserMananger;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button closeButton;
    @FXML
    private Button regButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;

    private void gotoMain(Stage stage) {
        stage.setTitle("ArtCollection");
        stage.setScene(MainApp.MAIN_SCENE);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loginAction(ActionEvent actionEvent) {
        String username = this.nameField.getText();
        String password = this.passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            this.errorLabel.setText("Name and password are required!");
            return;
        }

        try {
            UserMananger.authenticate(username, password);
            if (UserMananger.currentUser == null) {
                this.errorLabel.setText("Invalid username or password!");
            } else gotoMain((Stage) this.loginButton.getScene().getWindow());
        } catch (IOException exception) {
            this.errorLabel.setText("Database read error");
        }
    }

    @FXML
    private void registerAction(ActionEvent actionEvent) {
        String username = this.nameField.getText();
        String password = this.passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            this.errorLabel.setText("Name and password are required!");
            return;
        }

        try {
            if (UserMananger.userExists(username)) {
                this.errorLabel.setText("User with name " + username + " already exists");
                return;
            }
            if (UserMananger.registerUser(username, password)) {
            } else {
                this.errorLabel.setText("Registering error");
            }
        } catch (IOException exception) {
            this.errorLabel.setText("Database read error");
        }
    }
}