package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yarelychino on 5/11/16.
 */
public class FlightView extends Activity {

    private ArrayList<Flight> flights;
    private SQLHelper db = SQLHelper.getInstance(this);
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_view);

        Bundle extras = getIntent().getExtras();
        String ar = extras.getString("arrival");
        String dep = extras.getString("departure");
        String num = extras.getString("numberOfTickets");
        Log.d("FlightView", extras.getString("arrival"));
        Log.d("FlightView", extras.getString("departure"));
        Log.d("FlightView", extras.getString("numberOfTickets"));

        flights = db.getFlights(dep, ar, num);


        FlightAdapter adapter = new FlightAdapter(this, R.layout.flight_adapter,flights);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
      //  db.addReservation();

    }


}
