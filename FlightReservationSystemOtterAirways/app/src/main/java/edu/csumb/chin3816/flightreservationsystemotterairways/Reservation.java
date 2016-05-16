package edu.csumb.chin3816.flightreservationsystemotterairways;

/**
 * Created by yarelychino on 5/10/16.
 */
public class Reservation {
    private int reservationId;
    private int flightId;
    private int numberOfTickets;
    private int transactionId;
    private int isCancel;

    public Reservation(){

    }
    public Reservation( int flightId, int numberOfTickets, int transactionId, int isCancel){
        this.flightId= flightId;
        this.numberOfTickets = numberOfTickets;
        this.transactionId = transactionId;
        this.isCancel = isCancel;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString(){
        return "rsvt [rsvtId=" + reservationId + ", flgtId=" + flightId + ", nOT=" + numberOfTickets +
                ", trsId= " + transactionId + ", isC"+ isCancel+ "]";

    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }
}
