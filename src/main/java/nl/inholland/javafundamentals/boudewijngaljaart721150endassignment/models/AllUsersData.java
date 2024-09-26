package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class AllUsersData {
    private List<User> users = new ArrayList<>();

    public AllUsersData() {
        this.users.add(new User("Mark", "de Haan", "mdhaan", "dEb38t3D0c3nt", Role.MANAGEMENT));
        this.users.add(new User("Wiley", "Finch", "wfinch", "dEb38t3D0c3nt", Role.SALES));
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
