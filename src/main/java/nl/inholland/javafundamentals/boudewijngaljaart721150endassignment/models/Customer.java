package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Customer extends Person implements Serializable {
    private LocalDateTime dateTimeofBuyTicket;

    public Customer(String firstName, String lastName, LocalDateTime dateTimeofBuyTicket) {
        super(firstName, lastName);
        this.dateTimeofBuyTicket = dateTimeofBuyTicket;
    }

    public LocalDateTime getDateTimeofBuyTicket() {
        return this.dateTimeofBuyTicket;
    }
}
