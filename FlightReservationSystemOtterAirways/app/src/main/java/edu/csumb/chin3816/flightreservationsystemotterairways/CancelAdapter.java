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
public class CancelAdapter extends ArrayAdapter<Flight> {

    public CancelAdapter(Context context, int resource, List<Flight> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v == null){//1. check if the view has been inflated.
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.cancel_adapter, null);
        }
        Flight flight = getItem(position);
        if(flight != null){
            TextView title = (TextView) v.findViewById(R.id.flightNo);
            TextView info = (TextView) v.findViewById(R.id.info);

            title.setText(flight.getFlightNo());
            String text = "Departure: " + flight.getDeparture() + ", " + flight.getDepartureTime() +
                    "\nArrival: " + flight.getArrival() +
                    "\nNumber of tickers: " + flight.getNumberOfTickets() +
                    "\nTotal amount: $" + flight.getNumberOfTickets() * flight.getPrice() + "\n";
            info.setText(text);

            /*TextView arrival = (TextView) v.findViewById(R.id.arrName);
            TextView time = (TextView) v.findViewById(R.id.depTimeName);
            TextView price = (TextView) v.findViewById(R.id.priName);
            TextView flightNo = (TextView) v.findViewById(R.id.flightNo);

            departure.setText(flight.getDeparture());
            arrival.setText(flight.getArrival());
            time.setText(flight.getDepartureTime());
            price.setText(String.valueOf(flight.getPrice()));
            flightNo.setText(flight.getFlightNo());*/
        }
        return v;
    }


}
