package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFlightActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText departure;
    private EditText arrival;
    private EditText flightNumber;
    private EditText departureTime;
    private EditText flightCapacity;
    private EditText price;
    private int numFlightCapacity;
    private double numPrice;
    private SQLHelper db = SQLHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        departure = (EditText) findViewById(R.id.newDeparture);
        arrival = (EditText) findViewById(R.id.newArrival);
        flightCapacity = (EditText) findViewById(R.id.newCapacity);
        flightNumber = (EditText) findViewById(R.id.newFlightNumber);
        departureTime = (EditText) findViewById(R.id.newDepartureTime);
        price = (EditText) findViewById(R.id.newPrice);


        Button button = (Button) findViewById(R.id.submitFlightButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submitFlightButton){
            if(departure.getText().toString().equals("") ||
                    flightNumber.getText().toString().equals("") ||
                    arrival.getText().toString().equals("") ||
                    departureTime.getText().toString().equals("") ||
                    flightCapacity.getText().toString().equals("") ||
                    price.getText().toString().equals("")){
                invalidInformation(v, "All fields must be entered", "Invalid Information");
            }else{
                try{
                    Log.d("addFlight", flightCapacity.getText().toString());
                    numFlightCapacity = Integer.parseInt(flightCapacity.getText().toString());
                    numPrice = Double.parseDouble(price.getText().toString());
                    if(numFlightCapacity>0 && numPrice>0)
                        addFlight(v);
                    else{
                        invalidInformation(v, "Invalid: Flight Capacity or Price", "Invalid Information");
                    }
                }catch (Exception e){
                    invalidInformation(v, "All fields must be entered", "Invalid Information");
                }
            }
        }

    }

    public void addFlight(View v){
        if(db.isFlightNoAvailable(flightNumber.getText().toString())){
            Flight flight = new Flight(flightNumber.getText().toString(),
                    departure.getText().toString(),
                    arrival.getText().toString(),
                    departureTime.getText().toString(),
                    numFlightCapacity, numPrice);
            flight.setFlightCapacity(Integer.parseInt(flightCapacity.getText().toString()));

            String text = "FlightNo: " + flight.getFlightNo() +
                    "\nDeparture: " + flight.getDeparture() + ", " + flight.getDepartureTime() +
                    "\nArrival: " + flight.getArrival() +
                    "\nFlight Capacity : " + flight.getFlightCapacity() +
                    "\nPrice: $" + flight.getPrice() + "\n";

            confirmation(v, text,"Confirm Flight Information");
        }else{
            invalidInformation(v, "This entry already exists: \nFlightNo: " + flightNumber.getText().toString(), "Invalid Information");
        }
    }

    public void confirmation(final View v, String msg, String title){
        new AlertDialog.Builder(v.getContext())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            Flight flight = new Flight(flightNumber.getText().toString(),
                                    departure.getText().toString(),
                                    arrival.getText().toString(),
                                    departureTime.getText().toString(),
                                    numFlightCapacity, numPrice);
                            db.addFlight(flight);
                        startActivity(new Intent(v.getContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void invalidInformation(final View v, String msg, String title){
        new AlertDialog.Builder(v.getContext())
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(v.getContext(), MainActivity.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }



}
