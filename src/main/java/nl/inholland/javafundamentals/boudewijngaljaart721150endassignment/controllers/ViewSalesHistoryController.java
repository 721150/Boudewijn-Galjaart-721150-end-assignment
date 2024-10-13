package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Customer;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.TicketInformation;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewSalesHistoryController implements Initializable {

    private Database database;

    private ObservableList<TicketInformation> customerShow = FXCollections.observableArrayList();

    @FXML
    private TableView showingsTableInformationTebleView;

    public void giveData(Database database) {
        this.database = database;
        makeCustomerShowing();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Laat de gegevens van de verkochte voorstellingen weergeven op het scherm
        showingsTableInformationTebleView.setItems(customerShow);
    }

    private void makeCustomerShowing() {
        // Maak een overzicht van de verkochte kaartjes en toon deze
        for (Show show : database.getShows()) {
            for (Customer[] row : show.getSeats()) {
                for (Customer customer : row) {
                    if (customer != null) {
                        Optional<TicketInformation> existingOverview = customerShow.stream().filter(ticketOverview -> ticketOverview.getCustomerName().equals(customer.getFullName()) && ticketOverview.getShowTitle().equals(show.getstartTimeDateAndTitle())).findFirst();
                        if (existingOverview.isPresent()) {
                            existingOverview.get().setTicketsSold(existingOverview.get().getTicketsSold() + 1);
                        } else {
                            customerShow.add(new TicketInformation(customer.getFullName(), show.getstartTimeDateAndTitle(), customer.getDateTimeofBuyTicket().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), 1));
                        }
                    }
                }
            }
        }
    }
}
