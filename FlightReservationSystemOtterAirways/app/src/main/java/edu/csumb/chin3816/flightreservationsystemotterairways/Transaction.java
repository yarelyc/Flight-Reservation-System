package edu.csumb.chin3816.flightreservationsystemotterairways;

/**
 * Created by yarelychino on 5/10/16.
 */
public class Transaction {

    private int transactionId;
    private String transactionType;
    private String userName;
    private String transactionDate;
    private String transactionTime;

    public Transaction(){

    }

    public Transaction(int transactionId, String transactionType, String userName, String transactionDate, String transactionTime){
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.userName = userName;
        this.transactionDate= transactionDate;
        this.transactionTime = transactionTime;
    }
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
    @Override
    public String toString() {
        return "trst [trId=" + transactionId + ", trsTyoe=" + transactionType + ", usr=" + userName +
                ", trD= " + transactionDate + ", trT= " + transactionTime +"]";
    }

}
