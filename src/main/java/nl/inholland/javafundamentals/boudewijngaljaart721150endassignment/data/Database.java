package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Manager;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.SalesPerson;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();

    public Database() {
        // Voeg de gebruikers toe aan de "database" van het systeem
        this.users.add(new Manager("Mark", "de Haan", "mdhaan", "dEb38t3D0c3nt"));
        this.users.add(new SalesPerson("Wiley", "Finch", "wfinch", "dEb38t3D0c3nt"));
        this.shows.add(new Show(LocalDateTime.of(2024, 10, 4, 16, 30), LocalDateTime.of(2024, 10, 4, 17, 0), "Rebel Moon - Part two: The Scargiver"));
        this.shows.add(new Show(LocalDateTime.of(2024, 10, 5, 20, 0), LocalDateTime.of(2024, 10, 5, 22, 30), "Captain America: Brave New World"));
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Show> getShows() {
        return this.shows;
    }
}
