package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    public Database() {
        // Voeg de gebruikers toe aan de "database" van het systeem
        this.users.add(new Manager("Mark", "de Haan", "mdhaan", "dEb38t3D0c3nt"));
        this.users.add(new SalesPerson("Wiley", "Finch", "wfinch", "dEb38t3D0c3nt"));

        // Voeg de voorstellingen toe aan de "database" van het systeem
        this.shows.add(new Show(LocalDateTime.of(2024, 10, 4, 16, 30), LocalDateTime.of(2024, 10, 4, 17, 0), "Rebel Moon - Part two: The Scargiver"));
        this.shows.add(new Show(LocalDateTime.of(2024, 10, 5, 20, 0), LocalDateTime.of(2024, 10, 5, 22, 30), "Captain America: Brave New World"));

        // Voeg klanten toe aan de "database" van het systeem
        this.customers.add(new Customer("Bart", "Sneek"));
        this.customers.add(new Customer("Bob", "Meskers"));
        this.customers.add(new Customer("David", "Davidson"));

        // Voeg klanten toe aan de voorstellingen
        this.shows.get(0).addCustomer(customers.get(0), 1, 5);
        this.shows.get(0).addCustomer(customers.get(1), 2, 5);
        this.shows.get(0).addCustomer(customers.get(2), 3, 5);
        this.shows.get(1).addCustomer(customers.get(1), 4, 5);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Show> getShows() {
        return this.shows;
    }
}
