package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

public class TicketInformation {
    private String customerName;
    private String showTitle;
    private String purchaseDateTime;
    private int ticketsSold;

    public TicketInformation(String customerName, String showTitle, String purchaseDateTime, int ticketsSold) {
        this.customerName = customerName;
        this.showTitle = showTitle;
        this.purchaseDateTime = purchaseDateTime;
        this.ticketsSold = ticketsSold;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public String getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}
