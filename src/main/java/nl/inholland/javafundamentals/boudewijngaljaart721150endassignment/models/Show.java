package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import java.time.LocalDateTime;

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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
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
        for (Customer[] seat : seats) {
            if (seat == null) {
                numberOfSeatsLeft += 1;
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

    public void setSeats(Customer[][] seats) {
        this.seats = seats;
    }
}
