package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Manager;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
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

    @FXML
    private VBox mainScreenVBox;

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
        if (this.user instanceof Manager) {
            sellTicketsButton.setVisible(false);
        }
        else {
            manageShowingsButton.setVisible(false);
            viewSalesHistoryButton.setVisible(false);
        }
    }

    @FXML
    protected void loadManageShowingsVBox() throws IOException {
        // Toon het scherm voor het beheren van de voorstellingen in de VBox
        loadShowingsVBox("manage-showings-view.fxml");
    }

    @FXML
    protected void loadViewSalesHistoryVBox() throws IOException {
        // Toon het scherm voor het overzicht van de verkopen in de VBox
        loadShowingsVBox("view-sales-history-view.fxml");
    }

    @FXML
    protected void loadSellTicketsVBox() throws IOException {
        // Toon het scherm voor het betellen van de kaarten in de VBox
        loadShowingsVBox("sell-tickets-view.fxml");
    }

    private void loadShowingsVBox(String name) throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(name));
        VBox vBox = fxmlLoader.load();
        ManageShowingsController manageShowingsController = fxmlLoader.getController();
        manageShowingsController.giveData(this.user, this.database);
        mainScreenVBox.getChildren().setAll(vBox);
    }
}
