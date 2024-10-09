package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewSalesHistoryController implements Initializable {
    private User user;

    private Database database;

    private ObservableList<Show> shows = FXCollections.observableArrayList();

    @FXML
    private TableView showingsTableInformationTebleView;

    public void giveData(User user, Database database) {
        this.user = user;
        this.database = database;
        this.shows.setAll(this.database.getShows());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Laat de gegevens van de verkochte voorstellingen weergeven op het scherm
        showingsTableInformationTebleView.setItems(shows);
    }
}
