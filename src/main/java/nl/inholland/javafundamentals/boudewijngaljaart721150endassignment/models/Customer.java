package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Customer implements Serializable {
    private String firstName;
    private String lastName;
    private LocalDateTime dateTimeofBuyTicket;

    public Customer(String firstName, String lastName, LocalDateTime dateTimeofBuyTicket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateTimeofBuyTicket = dateTimeofBuyTicket;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public LocalDateTime getDateTimeofBuyTicket() {
        return this.dateTimeofBuyTicket;
    }
}
