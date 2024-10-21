package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Show implements Serializable {
    private final int NUMBER_OF_rows = 6;
    private final int NUMBER_OF_columns = 12;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String title;
    private CustomerSeat[][] seats;
    private boolean atLeastSixteenYearOld;

    public Show(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, boolean isAtLeastSixteenYearOld) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.title = title;
        this.seats = new CustomerSeat[NUMBER_OF_rows][NUMBER_OF_columns];
        this.atLeastSixteenYearOld = isAtLeastSixteenYearOld;
    }

    public Show(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, CustomerSeat[][] seats, boolean atLeastSixteenYearOld) {
        this(startDateTime, endDateTime, title, atLeastSixteenYearOld);
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

    public CustomerSeat[][] getSeats() {
        // Geef de verzameling van zitplaatsen terug
        return this.seats;
    }

    public int getNumberOfSeatsLeft() {
        // Bereken het aantal vrije stoelen en geef dit terug
        int numberOfSeatsLeft = 0;
        for (CustomerSeat[] row : this.seats) {
            for (CustomerSeat seat : row) {
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
        for (CustomerSeat[] row : this.seats) {
            for (CustomerSeat seat : row) {
                numberOfSeats += 1;
            }
        }
        return numberOfSeats;
    }

    public String getstartTimeDateAndTitle() {
        // Geef de starttijd en datum met titel terug
        return this.startDateTime.format(getDateTimeFormatter()) + " " + this.title;
    }

    public void addCustomer(CustomerSeat customer, int row, int col) {
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

    public boolean getAtLeastSixteenYearOld() {
        // Geef terug of er een minimale leeftijd van 16 jaar is
        return atLeastSixteenYearOld;
    }

    public void setAtLeastSixteenYearOld(boolean atLeastSixteenYearOld) {
        // Verander de minimale leeftijd van 16 jaar
        this.atLeastSixteenYearOld = atLeastSixteenYearOld;
    }
}
