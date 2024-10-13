package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private List<User> users = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();

    public Database() {
        // Voeg de gebruikers toe aan de "database" van het systeem
        this.users.add(new Manager("Mark", "de Haan", "mdhaan", "dEb38t3D0c3nt"));
        this.users.add(new SalesPerson("Wiley", "Finch", "wfinch", "dEb38t3D0c3nt"));
        this.users.add(new Manager("Anna", "Bakker", "abakker", "%7agIIAd8"));
        this.users.add(new SalesPerson("John", "Doe", "jdoe", "%7agIIAd8"));

        // Voeg de voorstellingen toe aan de "database" van het systeem
        this.shows.add(new Show(LocalDateTime.of(2024, 10, 4, 16, 30), LocalDateTime.of(2024, 10, 4, 19, 0), "Rebel Moon - Part two: The Scargiver"));
        this.shows.add(new Show(LocalDateTime.of(2024, 10, 5, 20, 0), LocalDateTime.of(2024, 10, 5, 22, 30), "Captain America: Brave New World"));
        this.shows.add(new Show(LocalDateTime.of(2024, 12, 3, 15, 0), LocalDateTime.of(2024, 12, 3, 17, 30), "Venom: The Last Dance"));
        this.shows.add(new Show(LocalDateTime.of(2024, 11, 1, 18, 0), LocalDateTime.of(2024, 11, 1, 20, 30), "Dune: Part Two"));
        this.shows.add(new Show(LocalDateTime.of(2024, 11, 10, 14, 0), LocalDateTime.of(2024, 11, 10, 16, 30), "Avatar: The Way of Water"));
        this.shows.add(new Show(LocalDateTime.of(2024, 11, 15, 19, 0), LocalDateTime.of(2024, 11, 15, 21, 30), "The Batman"));
        this.shows.add(new Show(LocalDateTime.of(2024, 12, 1, 17, 0), LocalDateTime.of(2024, 12, 1, 19, 30), "Spider-Man: No Way Home"));
        this.shows.add(new Show(LocalDateTime.of(2024, 12, 10, 20, 0), LocalDateTime.of(2024, 12, 10, 22, 30), "The Matrix Resurrections"));

        // Voeg klanten toe aan de voorstellingen
        this.shows.get(0).addCustomer(new Customer("Bart", "Sneek", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 1);
        this.shows.get(0).addCustomer(new Customer("Bob", "Meskers", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 2);
        this.shows.get(0).addCustomer(new Customer("Bob", "Meskers", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 3);
        this.shows.get(1).addCustomer(new Customer("Bob", "Meskers", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 4);
        this.shows.get(1).addCustomer(new Customer("David", "Davidson", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 5);
        this.shows.get(2).addCustomer(new Customer("Alice", "Wonderland", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 6);
        this.shows.get(3).addCustomer(new Customer("Bart", "Sneek", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 7);
        this.shows.get(4).addCustomer(new Customer("Bob", "Meskers", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 8);
        this.shows.get(5).addCustomer(new Customer("David", "Davidson", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 9);
        this.shows.get(6).addCustomer(new Customer("Alice", "Wonderland", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 10);
        this.shows.get(7).addCustomer(new Customer("Bob", "Meskers", LocalDateTime.of(2024, 10, 11, 16, 30)), 5, 11);
    }

    public List<User> getUsers() {
        // Geef alle gebruikers door
        return this.users;
    }

    public List<Show> getShows() {
        // Geef alle voorstellingen door
        return this.shows;
    }

    public List<Show> getCurrentShows() {
        // Geef alle voorstellinge door die nog moeten worden gehouden
        List<Show> currentShows = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (Show show : this.shows) {
            if (show.getStartDate().isAfter(LocalDate.now()) || show.getStartDate().isEqual(LocalDate.now()) && LocalTime.parse(show.getStartTime(), timeFormatter).isAfter(LocalTime.now()))
            {
                currentShows.add(show);
            }
        }
        return currentShows;
    }

    public void addShow(Show show) {
        // Voeg een voorstelling toe aan de lijst met voorstellingen
        this.shows.add(show);
    }

    public void editShow(Show show, Show editSchow) {
        // Wijzig de gegevens van een voorstelling in de lijst met voorstellingen
        for (int i = 0; i < this.shows.size(); i++) {
            if (this.shows.get(i).equals(show)) {
                this.shows.set(i, editSchow);
                break;
            }
        }
    }

    public void deleteShow(Show show) {
        // Verwijder een voorstelling uit de lijst met voorstellingen
        this.shows.remove(show);
    }


    public void saveDatabase(String filename) {
        // Sla de "database" op in een binaire fill
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(this.shows);
        } catch (IOException e) {}
    }

    public void loadDatabase(String filename) {
        // Probeer de "database" op te halen uit een binaire fill
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            this.shows = (List<Show>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {}
    }
}
