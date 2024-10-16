package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Show implements Serializable {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String title;
    private Customer[][] seats;

    public Show(LocalDateTime startDateTime, LocalDateTime endDateTime, String title) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.title = title;
        this.seats = new Customer[6][12];
    }

    public Show(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, Customer[][] seats) {
        this(startDateTime, endDateTime, title);
        this.seats = seats;
    }

    public String getStartDateTime() {
        // Format van de datum en tijd omzetten
        return this.startDateTime.format(getDateTimeFormatter());
    }

    public String getEndDateTime() {
        // Format van de datum en tijd omzetten
        return this.endDateTime.format(getDateTimeFormatter());
    }

    public String getStartTime() {
        // Format van de tijd omzetten en de datum weglaten
        return this.startDateTime.toLocalTime().format(getTimeFormatter());
    }

    public String getEndTime() {
        // Format van de tijd omzetten en de datum weglaten
        return this.endDateTime.toLocalTime().format(getTimeFormatter());
    }

    public LocalDate getStartDate() {
        // Format van de datum omzetten en de tijd weglaten
        return this.startDateTime.toLocalDate();
    }

    public LocalDate getEndDate() {
        // Format van de datum omzetten en de tijd weglaten
        return this.endDateTime.toLocalDate();
    }

    public String getTitle() {
        // Geef de titel van de voorstelling terug
        return this.title;
    }

    public Customer[][] getSeats() {
        // Geef de verzameling van zitplaatsen terug
        return this.seats;
    }

    public int getNumberOfSeatsLeft() {
        // Bereken het aantal vrije stoelen en geef dit terug
        int numberOfSeatsLeft = 0;
        for (Customer[] row : this.seats) {
            for (Customer seat : row) {
                if (seat == null) {
                    numberOfSeatsLeft += 1;
                }
            }
        }
        return numberOfSeatsLeft;
    }

    public String getNumberOfSeatsLeftAndAvalableSeats() {
        // Bepaal het aantal beschikbare en totaal aantal stoelen en geef dit terug
        return getNumberOfSeatsLeft() + "/" + getTotalNumberOfSeats();
    }

    public int getTotalNumberOfSeats() {
        // Bereken het aantal stoelen en geef dit terug
        int numberOfSeats = 0;
        for (Customer[] row : this.seats) {
            for (Customer seat : row) {
                numberOfSeats += 1;
            }
        }
        return numberOfSeats;
    }

    public String getstartTimeDateAndTitle() {
        // Geef de starttijd en datum met titel terug
        return this.startDateTime.format(getDateTimeFormatter()) + " " + this.title;
    }

    public void addCustomer(Customer customer, int row, int col) {
        // Voeg een klant toe aan de voorstelling op de opgegeven positie
        if (this.seats[row][col] == null) {
            this.seats[row][col] = customer;
        }
    }

    private DateTimeFormatter getTimeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }

    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }
}
