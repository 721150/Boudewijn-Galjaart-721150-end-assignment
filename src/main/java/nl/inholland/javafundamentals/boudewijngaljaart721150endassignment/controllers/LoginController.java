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
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
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
    protected void setLoginButton(ActionEvent event) throws IOException {
        // Haal alle gebruikers op uit de "database"
        Database database = new Database();
        User user = findUser(usernameField.getText(), passwordField.getText(), database);
        if (user != null) {
            // Openen van het nieuwe venster
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainController mainController = fxmlLoader.getController();
            mainController.giveData(user, database);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Fantastic Cinema");
            stage.setScene(scene);
            stage.show();
        }
        else {
            // Maak het bericht zichtbaar dat de verkeerde inloggegevens zijn ingevuld
            invalidUserMessage.setVisible(true);
        }
    }

    private User findUser(String username, String password, Database database) {
        // Zoel maar eem gebruiker met de ingevulde gebruikersnaam en wachtwoord
        List<User> users = database.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}