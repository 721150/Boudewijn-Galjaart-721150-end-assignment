package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.interfaces.Controller;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.CustomerSeat;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SeatsSellTicketsController implements Initializable, Controller {
    private Database database;

    private Show show;

    @FXML
    private GridPane allSeatsGridPane;

    @FXML
    private Label informationAboutSelectedShowLabel;

    @FXML
    private TextArea selectedSeatsTextArea;

    @FXML
    private TextField firstnameCustomerTextField;

    @FXML
    private TextField lastnameCustomerTextField;

    @FXML
    private Button sellSeatsTicketsButton;

    @FXML
    private Label sellFailMessage;

    @FXML
    private VBox mainScreenVBox;

    private int selectedSeatsCount;

    public void giveData(Database database, Show show) {
        this.database = database;
        this.show = show;
        this.selectedSeatsCount = 0;
        loadScreen();
    }

    private void loadScreen() {
        informationAboutSelectedShowLabel.setText(this.show.getStartDateTime() + " " + this.show.getTitle());
        loadSeatsInGridPane();
    }

    private void setupGridPane() {
        // Zorg dat er ruimte is tussen de verschillende zitplaatsen in het scherm
        allSeatsGridPane.setHgap(5);
        allSeatsGridPane.setVgap(5);
    }

    private void addRowsAndSeats() {
        // Laat alle zitplaatsen op het scherm zien
        for (int row = 0; row < this.show.getSeats().length; row++) {
            addRowLabel(row);
            for (int col = 0; col < this.show.getSeats()[row].length; col++) {
                addSeat(row, col);
            }
        }
    }

    private void addRowLabel(int row) {
        // Voeg een nieuwe rij toe aan het scherm met zitplaatsen
        Text rowLabel = new Text("Row " + (row + 1));
        allSeatsGridPane.add(rowLabel, 0, row);
    }

    private void addSeat(int row, int col) {
        // Voeg een nieuwe stoel toe aan een rij op het scherm
        Rectangle seat = new Rectangle(30, 30);
        Text text = new Text(String.valueOf((col + 1)));
        text.setFill(Color.WHITE);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(seat, text);
        if (this.show.getSeats()[row][col] != null) {
            seat.setFill(Color.RED);
        } else {
            seat.setFill(Color.GRAY);
        }
        stackPane.setOnMouseClicked(event -> handleSeatClick(seat, row + 1, col + 1));
        allSeatsGridPane.add(stackPane, col + 1, row);
    }

    private void handleSeatClick(Rectangle seat, int currentRow, int currentCol) {
        // Zet de kleur van de zitplek naar de status en voeg deze toe of verwijder deze van de lijst met geslecteerde plaatsen
        Color currentColor = (Color) seat.getFill();
        String selectedSeat = "Row " + currentRow + "/Seat " + currentCol + "\n";
        if (currentColor == Color.RED) {
            sellFailMessage.setVisible(true);
        }
        else if (currentColor == Color.GREEN) {
            seat.setFill(Color.GRAY);
            selectedSeatsTextArea.setText(selectedSeatsTextArea.getText().replace(selectedSeat, ""));
            sellFailMessage.setVisible(false);
            this.selectedSeatsCount--;
        }
        else {
            seat.setFill(Color.GREEN);
            selectedSeatsTextArea.appendText(selectedSeat);
            sellFailMessage.setVisible(false);
            this.selectedSeatsCount++;
        }
        updateSellButtonLabel();
    }

    private void loadSeatsInGridPane() {
        setupGridPane();
        addRowsAndSeats();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Maak de bestelknop klikbaar op het moment dat de voor- en achternaam van de klant zijn ingevuld en minstens één stoel is geselecteerd
        ChangeListener<String> textChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                updateSellButtonState();
            }
        };
        firstnameCustomerTextField.textProperty().addListener(textChangeListener);
        lastnameCustomerTextField.textProperty().addListener(textChangeListener);
        selectedSeatsTextArea.textProperty().addListener(textChangeListener);
    }

    private void updateSellButtonState() {
        boolean isCustomerInfoFilled = firstnameCustomerTextField.getText().trim().isEmpty() || lastnameCustomerTextField.getText().trim().isEmpty();
        boolean isSeatSelected = this.selectedSeatsTextArea.getText().trim().isEmpty();
        sellSeatsTicketsButton.setDisable(isCustomerInfoFilled || isSeatSelected);
    }

    private void updateSellButtonLabel() {
        // Pas de tekst van de knop bestellen aan naar het aantal geselecteerde kaartjes
        if (this.selectedSeatsCount == 0) {
            sellSeatsTicketsButton.setText("Sell tickets");
            return;
        }
        sellSeatsTicketsButton.setText("Sell " + this.selectedSeatsCount + " ticket(s)");
    }

    @FXML
    protected void sellButtonClick(ActionEvent event) throws IOException {
        // Haal de geselecteerde positie op en voeg deze toe aan de "database"
        CustomerSeat customer = new CustomerSeat(firstnameCustomerTextField.getText(), lastnameCustomerTextField.getText(), LocalDateTime.now());
        List<int[]> selectedSeatsPositions = getSelectedSeatsPositions();
        Show show = this.show;
        for (int[] position : selectedSeatsPositions) {
            show.addCustomer(customer, position[0] - 1, position[1] - 1);
            this.database.editShow(this.show, show);
        }
        showSuccessPopup("Selling " + this.selectedSeatsCount + " ticket(s) to " + customer.getFullName() + " was successful.");
        openManageShowingsScreen();
    }

    @FXML
    protected void cancelButtonClick(ActionEvent event) throws IOException {
        openManageShowingsScreen();
    }

    private void openManageShowingsScreen() throws IOException {
        // Open het scherm voor het beheren van de voorstellingen in de VBox
        FXMLLoader fxmlLoader = loadShowingsVBox(mainScreenVBox, "sell-tickets-view.fxml");
        SellTicketsController sellTicketsController = fxmlLoader.getController();
        sellTicketsController.giveData(this.database);
    }

    private List<int[]> getSelectedSeatsPositions() {
        // Haal de geslecteerde plaatsen op uit de TextArea
        String selectedSeatsText = selectedSeatsTextArea.getText();
        List<int[]> selectedSeatsPositions = new ArrayList<>();
        if (!selectedSeatsText.isEmpty()) {
            String[] seats = selectedSeatsText.split("\n");
            for (String seat : seats) {
                String[] parts = seat.split("/Seat ");
                int row = Integer.parseInt(parts[0].replace("Row ", "").trim());
                int col = Integer.parseInt(parts[1].trim());
                selectedSeatsPositions.add(new int[]{row, col});
            }
        }
        return selectedSeatsPositions;
    }
}
