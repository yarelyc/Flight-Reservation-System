package edu.csumb.chin3816.flightreservationsystemotterairways;

/**
 * Created by yarelychino on 5/10/16.
 */
public class Flight {

    private int flightId;
    private String flightNo;
    private String departure;
    private String arrival;
    private String departureTime;
    private int flightCapacity;
    private double price;

    public Flight(){

    }

    public Flight(String flightNo, String departure, String arrival, String departureTime,
                  int flightCapacity, double price){
        this.flightNo = flightNo;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.flightCapacity = flightCapacity;
        this.price = price;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getFlightCapacity() {
        return flightCapacity;
    }

    public void setFlightCapacity(int flightCapacity) {
        this.flightCapacity = flightCapacity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice(){
        return price;
    }
    @Override
    public String toString() {
        return "Flgt [id=" + flightId + ", no=" + flightNo + ", dp=" + departure +
                ", ar= " + arrival + ", dpT= " + departureTime + ", flgtC= " + flightCapacity  + ", prc= " + price +  "]";
    }


}
