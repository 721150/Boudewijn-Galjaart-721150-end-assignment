package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.AllUsersData;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label invalidUserMessage;

    @FXML
    protected void setLoginButton(ActionEvent event) throws IOException { // moet nog aan worden gewerkt
        if (validateUser(readUsername(), readPassword())) {
            // Openen van het nieuwe venster
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main-view.fxml"));
            MainController mainController = fxmlLoader.getController();
            mainController.setUser(new User("test", "test", "test", "test", Role.SALES));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            invalidUserMessage.setVisible(true);
        }
    }

    @FXML
    protected String readUsername() {
        // Haal de gebruikersnaam op uit het tekstveld
        return usernameField.getText();
    }

    @FXML
    protected String readPassword() {
        // Haal het wachtwoord op uit het wachtwoordveld
        return passwordField.getText();
    }

    private boolean validateUser(String username, String password) {
        // Bepaal of de combinatie van gebruikersnaam en wachtwoord voorkomt in de verzameling
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private List<User> loadUsers(){
        // Haal alle gebruikers op uit de "database"
        AllUsersData allUsersData = new AllUsersData();
        return allUsersData.getUsers();
    }
}