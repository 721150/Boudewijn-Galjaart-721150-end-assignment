package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    @FXML
    private Label welcomeMassageWhiteName;

    @FXML
    private Label welcomeMassageWhiteRole;

    @FXML
    private Label welkomeMessageWhiteDate;

    @FXML
    private Button sellTicketsButton;

    @FXML
    private Button manageShowingsButton;

    @FXML
    private Button viewSalesHistoryButton;

    private User user;

    private Database database;

    public void giveData(User user, Database database) {
        this.user = user;
        this.database = database;
        loadDataOfUser();
    }

    private void loadDataOfUser() {
        // Toon de gegevens van de gebruiker op het scherm en de datum/tijd
        welcomeMassageWhiteName.setText(welcomeMassageWhiteName.getText() + " " + this.user.getFullName());
        welcomeMassageWhiteRole.setText(welcomeMassageWhiteRole.getText() + " " + this.user.getRole().toString().toLowerCase());
        welkomeMessageWhiteDate.setText(welkomeMessageWhiteDate.getText() + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        // Bepaal de rol van de gebruiker en bepaal welke rechten deze heeft
        if (this.user.getRole() == Role.MANAGEMENT) {
            sellTicketsButton.setVisible(false);
        }
        else {
            manageShowingsButton.setVisible(false);
            viewSalesHistoryButton.setVisible(false);
        }
    }
}
