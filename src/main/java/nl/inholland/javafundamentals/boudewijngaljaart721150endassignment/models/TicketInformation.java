package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

public class TicketInformation {
    private String customerName;
    private String showTitle;
    private String dateTimeofBuyTicket;
    private int ticketsSold;

    public TicketInformation(String customerName, String showTitle, String dateTimeofBuyTicket, int ticketsSold) {
        this.customerName = customerName;
        this.showTitle = showTitle;
        this.dateTimeofBuyTicket = dateTimeofBuyTicket;
        this.ticketsSold = ticketsSold;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getShowTitle() {
        return this.showTitle;
    }

    public String getDateTimeofBuyTicket() {
        return this.dateTimeofBuyTicket;
    }

    public int getTicketsSold() {
        return this.ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}
