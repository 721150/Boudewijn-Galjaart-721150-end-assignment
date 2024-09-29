package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Manager;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.SalesPerson;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();

    public Database() {
        // Voeg de gebruikers toe aan de "database" van het systeem
        this.users.add(new Manager("Mark", "de Haan", "mdhaan", "dEb38t3D0c3nt"));
        this.users.add(new SalesPerson("Wiley", "Finch", "wfinch", "dEb38t3D0c3nt"));
    }

    public List<User> getUsers() {
        return users;
    }
}
