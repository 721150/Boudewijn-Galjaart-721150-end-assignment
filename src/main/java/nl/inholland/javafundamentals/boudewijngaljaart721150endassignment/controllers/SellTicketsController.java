package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.interfaces.Controller;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellTicketsController implements Initializable, Controller {
    @FXML
    private TableView showingsTableInformationTebleView;

    @FXML
    private Button selectSeatsButton;

    @FXML
    private Label informationAboutSelectedShowLabel;

    @FXML
    private VBox mainScreenVBox;

    private Database database;

    private ObservableList<Show> shows = FXCollections.observableArrayList();

    public void giveData(Database database) {
        this.database = database;
        this.shows.setAll(this.database.getCurrentShows());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Toon de voorstellingen in de tabel
        showingsTableInformationTebleView.setItems(this.shows);

        // Zorg ervoor dat de knoppen weizigen en verwijderen alleen werken als er een item is geselecteerd en dat de informatie van een geselecteerde voorstelling wordt weergeven
        showingsTableInformationTebleView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectSeatsButton.setDisable(false);
                informationAboutSelectedShowLabel.setText(getSelectedShow().getStartDateTime() + " " + getSelectedShow().getTitle());
            } else {
                selectSeatsButton.setDisable(true);
            }
        });
    }

    @FXML
    protected void selectSeatsButtonAction(ActionEvent event) throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = loadShowingsVBox(mainScreenVBox, "seats-sell-tickets-view.fxml");
        SeatsSellTicketsController seatsSellTicketsController = fxmlLoader.getController();
        seatsSellTicketsController.giveData(this.database, getSelectedShow());
    }

    private Show getSelectedShow() {
        // Haal de geselecteerde voorstelling op uit de tabel
        return (Show)showingsTableInformationTebleView.getSelectionModel().getSelectedItem();
    }
}
