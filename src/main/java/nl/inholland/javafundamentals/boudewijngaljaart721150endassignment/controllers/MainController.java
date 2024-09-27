package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    @FXML
    private Label welcomeMassageWhiteName;

    @FXML
    private Label welcomeMassageWhiteRole;

    @FXML
    private Label welkomeMessageWhiteDate;

    private User user;

    public void setUser(User user) {
        this.user = user;
        welcomeMassageWhiteName.setText(welcomeMassageWhiteName.getText() + " " + user.getUsername());
        welcomeMassageWhiteRole.setText(welcomeMassageWhiteRole.getText() + " " + user.getRole().toString().toLowerCase());
        welkomeMessageWhiteDate.setText(welkomeMessageWhiteDate.getText() + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }
}
