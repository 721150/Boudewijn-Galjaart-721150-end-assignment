package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

public class MainController {
    @FXML
    private Label test;

    private User user;

    public void setUser(User user) {
        this.user = user;
        test.setText(user.getUsername());
    }
}
