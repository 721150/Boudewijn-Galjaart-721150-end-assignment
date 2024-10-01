package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Show {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String title;
    private Customer[][] seats;

    public Show(LocalDateTime startDateTime, LocalDateTime endDateTime, String title) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.title = title;
        this.seats = new Customer[12][6];
    }

    public String getStartDateTime() {
        // Format van de datum en tijd omzetten
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String startDateTime = this.startDateTime.format(formatter);
        return startDateTime;
    }

    public String getEndDateTime() {
        // Format van de datum en tijd omzetten
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String endDateTime = this.endDateTime.format(formatter);
        return endDateTime;
    }

    public String getTitle() {
        return title;
    }

    public Customer[][] getSeats() {
        return seats;
    }

    public int getNumberOfSeatsLeft() {
        // Bereken het aantal vrije stoelen en geef dit terug
        int numberOfSeatsLeft = 0;
        for (Customer[] row : seats) {
            for (Customer seat : row) {
                if (seat == null) {
                    numberOfSeatsLeft += 1;
                }
            }
        }
        return numberOfSeatsLeft;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCustomer(Customer customer, int row, int col) {
        if (seats[row][col] == null) {
            seats[row][col] = customer;
        }
    }

    public String getNumberOfSeatsLeftAndAvalableSeats() {
        int numberOfSeatsLeft = getNumberOfSeatsLeft();
        int availableSeats = this.seats.length * this.seats[0].length;
        return numberOfSeatsLeft + "/" + availableSeats;
    }
}
