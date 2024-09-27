package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();

    public Database() {
        // Voeg de gebruikers toe aan de "database" van het systeem
        this.users.add(new User("Mark", "de Haan", "mdhaan", "dEb38t3D0c3nt", Role.MANAGEMENT));
        this.users.add(new User("Wiley", "Finch", "wfinch", "dEb38t3D0c3nt", Role.SALES));
    }

    public List<User> getUsers() {
        return users;
    }
}
