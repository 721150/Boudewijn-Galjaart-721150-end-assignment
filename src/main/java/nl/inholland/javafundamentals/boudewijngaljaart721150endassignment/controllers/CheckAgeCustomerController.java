package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckAgeCustomerController implements Initializable {
    private Show show;

    private String customer;

    private int numberOfSeats;

    @FXML
    private Label titleOfMovieLabel;

    @FXML
    private Label startDateTimeOfMovieLabel;

    @FXML
    private Label numberOfTicketsLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private CheckBox checkedAgeCheckBox;

    public void giveData(Show show, String customerName, int numberOfTickets) {
        this.show = show;
        this.customer = customerName;
        this.numberOfSeats = numberOfTickets;
        displayShowInformation();
    }

    public boolean informationCheck() {
        if (checkedAgeCheckBox.isSelected()) {
            return true;
        }
        return false;
    }

    private void displayShowInformation() {
        // Weergeef de informatie van de film
        titleOfMovieLabel.setText(this.show.getTitle());
        startDateTimeOfMovieLabel.setText(this.show.getStartDateTime());
        customerNameLabel.setText(this.customer);
        numberOfTicketsLabel.setText(this.numberOfSeats + "");
    }

    @FXML
    protected void confirmButtonClicked() {
        closeWindow();
    }

    @FXML
    protected void cancelButtonClicked() {
        closeWindow();
    }

    private void closeWindow() {
        // Sluit het dialoogvenster
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Maak de bevestigingsknop klikbaar als het veld is aangevinkt
        checkedAgeCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(!newValue);
        });
        confirmButton.setDisable(true);
    }
}
