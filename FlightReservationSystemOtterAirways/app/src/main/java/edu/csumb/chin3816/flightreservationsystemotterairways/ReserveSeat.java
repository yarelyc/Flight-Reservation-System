package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yarelychino on 5/10/16.
 */
public class ReserveSeat extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText departure;
    private EditText arrival;
    private EditText numberOfTickets;
    private SQLHelper db = SQLHelper.getInstance(this);

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_seat_layout);

        View submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        departure = (EditText) findViewById(R.id.departureInput);
        arrival = (EditText) findViewById(R.id.arrivalInput);
        numberOfTickets = (EditText) findViewById(R.id.numberOfTicketsInput);

        /* Drop down Menu
        Spinner spinner = (Spinner) findViewById(R.id.departTime);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submitButton){
            int num;
            try{
                num = Integer.parseInt(numberOfTickets.getText().toString());
                if(num>0 && num<=7)
                    proceed();
                else
                    Toast.makeText(v.getContext(),"Invalid Number of Tickets ", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Toast.makeText(v.getContext(),"Invalid Number of Tickets ", Toast.LENGTH_SHORT).show();
            }
           //ArrayList<Flight> flights = db.getFlights(departure.getText().toString(), arrival.getText().toString(), numberOfTickets.getText().toString());

        }

    }

    public void proceed(){
        Log.d("ResetTAg", departure.getText().toString());
        Log.d("ResetTAg", arrival.getText().toString());
        Log.d("ResetTAg", numberOfTickets.getText().toString());
        Intent i = new Intent(this, FlightView.class);

        i.putExtra("departure",departure.getText().toString());
        i.putExtra("arrival",arrival.getText().toString());
        i.putExtra("numberOfTickets", numberOfTickets.getText().toString());
        startActivity(i);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
