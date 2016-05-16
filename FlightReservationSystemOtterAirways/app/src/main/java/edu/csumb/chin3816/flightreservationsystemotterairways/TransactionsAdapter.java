package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yarelychino on 5/12/16.
 */
public class TransactionsAdapter extends ArrayAdapter<Transaction> {

    public SQLHelper db = SQLHelper.getInstance(this.getContext());
    public TransactionsAdapter(Context context, int resource, List<Transaction> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v == null){//1. check if the view has been inflated.
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.transaction_adapter, null);
        }
        Transaction transaction = getItem(position);
        if(transaction != null){
            TextView info = (TextView) v.findViewById(R.id.infoTransaction);

            String x = transaction.getTransactionType();
            String str;
            if(x.equals("Reservation") || x.equals("Cancellation")){
               Flight flight =  db.getReservation(String.valueOf(transaction.getTransactionId()));
                str = "Transaction Type: " + x +
                        "\nCustomer's username: " + transaction.getUserName() +
                        "\nFlightNo: " + flight.getFlightNo() +
                        "\nDeparture: " + flight.getDeparture() +
                        "\nArrival: " + flight.getArrival() +
                        "\nNumber of tickets " + flight.getNumberOfTickets() +
                        "\nReservation Number: " + flight.getFlightId() +
                        "\nTransaction Date: " + transaction.getTransactionDate() +
                        "\nTransaction Time: " + transaction.getTransactionTime();
            }else{
                str = "Transaction Type: " + x +
                        "\nCustomer's username: " + transaction.getUserName() +
                        "\nTransaction Date: " + transaction.getTransactionDate() +
                        "\nTransaction Time: " + transaction.getTransactionTime();
            }
            info.setText(str);
            info.setMaxWidth(50);


        }
        return v;
    }


}
