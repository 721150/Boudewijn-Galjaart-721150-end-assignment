package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

public class TicketOverview {
    private String customerName;
    private String showTitle;
    private String purchaseDateTime;
    private int ticketsSold;

    public TicketOverview(String customerName, String showTitle, String purchaseDateTime, int ticketsSold) {
        this.customerName = customerName;
        this.showTitle = showTitle;
        this.purchaseDateTime = purchaseDateTime;
        this.ticketsSold = ticketsSold;
    }

    // Getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(String purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}
