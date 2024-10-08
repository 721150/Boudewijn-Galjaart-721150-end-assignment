package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Customer;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SeatsSellTicketsController implements Initializable {
    private User user;

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

    public void giveData(User user, Database database, Show show) {
        this.user = user;
        this.database = database;
        this.show = show;
        informationAboutSelectedShowLabel.setText(this.show.getStartDateTime() + " " + this.show.getTitle());
        this.selectedSeatsCount = 0;
        loadSeatsInGridPane();
    }

    private void loadSeatsInGridPane() {
        // Maak de GridPane met daarin alle zitplaatsen voor de voorstelling
        allSeatsGridPane.setHgap(5);
        allSeatsGridPane.setVgap(5);
        for (int row = 0; row < this.show.getSeats().length; row++) {
            Text rowLabel = new Text("Row " + (row + 1));
            allSeatsGridPane.add(rowLabel, 0, row);
            for (int col = 0; col < this.show.getSeats()[row].length; col++) {
                Rectangle seat  = new Rectangle(30,30);

                Text text = new Text(String.valueOf((col + 1)));
                text.setFill(Color.WHITE);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(seat, text);

                if (this.show.getSeats()[row][col] != null){
                    seat.setFill(Color.RED);
                }
                else {
                    seat.setFill(Color.GRAY);
                }


                final int currentRow = row + 1;
                final int currentCol = col + 1;
                stackPane.setOnMouseClicked(event -> {
                    Color currentColor = (Color)seat.getFill();
                    if (currentColor == Color.RED) {
                        sellFailMessage.setVisible(true);
                    }
                    else if (currentColor == Color.GREEN) {
                        seat.setFill(Color.GRAY);
                        selectedSeatsTextArea.setText(selectedSeatsTextArea.getText().replace("Row " + currentRow + "/Seat " + currentCol + "\n", ""));
                        sellFailMessage.setVisible(false);
                        this.selectedSeatsCount--;
                    }
                    else {
                        seat.setFill(Color.GREEN);
                        selectedSeatsTextArea.appendText("Row " + currentRow + "/Seat " + currentCol + "\n");
                        sellFailMessage.setVisible(false);
                        this.selectedSeatsCount++;
                    }
                    updateSellButtonLabel();
                });

                allSeatsGridPane.add(stackPane, col + 1, row);
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Maak de bestelknop klikbaar op het moment dat de voor- en achternaam van de klant zijn ingevuld
        ChangeListener<String> textChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                sellSeatsTicketsButton.setDisable(firstnameCustomerTextField.getText().trim().isEmpty() || lastnameCustomerTextField.getText().trim().isEmpty());
            }
        };
        firstnameCustomerTextField.textProperty().addListener(textChangeListener);
        lastnameCustomerTextField.textProperty().addListener(textChangeListener);
    }

    private void updateSellButtonLabel() {
        if (this.selectedSeatsCount == 0) {
            sellSeatsTicketsButton.setText("Sell tickets");
            return;
        }
        sellSeatsTicketsButton.setText("Sell " + this.selectedSeatsCount + " ticket(s)");
    }

    @FXML
    protected void sellButtonClick(ActionEvent event) throws IOException {
        Customer customer = new Customer(firstnameCustomerTextField.getText(), lastnameCustomerTextField.getText());

        this.database.addCustomer(customer);

        List<int[]> selectedSeatsPositions = getSelectedSeatsPositions();
        Show show = this.show;

        for (int[] position : selectedSeatsPositions) {
            show.addCustomer(customer, position[0] - 1, position[1] - 1);
            this.database.editShow(this.show, show);
        }

        openManageShowingsScreen();
    }

    @FXML
    protected void cancelButtonClick(ActionEvent event) throws IOException {
        openManageShowingsScreen();
    }

    private void openManageShowingsScreen() throws IOException {
        // Open het scherm voor het beheren van de voorstellingen in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("sell-tickets-view.fxml"));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
        SellTicketsController sellTicketsController = fxmlLoader.getController();
        sellTicketsController.giveData(this.user, this.database);
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
