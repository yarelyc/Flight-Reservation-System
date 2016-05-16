package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CancelReservationActivity extends AppCompatActivity {

    private ArrayList<Flight> flights;
    private Flight flight;
    private SQLHelper db = SQLHelper.getInstance(this);
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        flights = db.getReservationToCancel(username);


        CancelAdapter adapter = new CancelAdapter(this, R.layout.cancel_adapter,flights);
        ListView listView = (ListView) findViewById(R.id.cancelListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flight = flights.get(position);
                String text = "Are you sure you want to cancel:" +
                        "\nFlightNo: " + flight.getFlightNo() +
                        "\nDeparture: " + flight.getDeparture() + ", " + flight.getDepartureTime() +
                        "\nArrival: " + flight.getArrival() +
                        "\nNumber of tickers: " + flight.getNumberOfTickets() +
                        "\nTotal amount: $" + flight.getNumberOfTickets() * flight.getPrice() + "\n";
                confirmation(view,text, "Cancel Reservation Confirmation");

               // launchDialog("Reserve Seat");
            }

        });


    }

    public void confirmationDialog(View v, String msg, String title, final String username, final String capacity){

       /* final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Comlete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");

                String d = date.format(new Date());
                String t = time.format(new Date());
                Log.d("In Reservation", "add flight confirmation Dialog: " + reserve);
                db.addTransaction(new Transaction("Reservation", username, d, t));
                String id = db.getTransactionId(d, t);
                if (id != null) {
                    Log.d("In Reservation", "transaction Id: " + id);
                    db.addReservation(new Reservation(reserve.getFlightId(), Integer.valueOf(num), Integer.valueOf(id), 0));
                    Log.d("In Reservation", "Reservation was added: ");
                    db.updateFlightCapacity(reserve.getFlightNo(), num, capacity);
                    Log.d("In Reservation", "Complete reservation: ");
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                    alertDialog.dismiss();
                }
            }
        });*/


    }
    public void confirmation(final View v, String msg, String title){
        new AlertDialog.Builder(v.getContext())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        db.updateFlightCapacity(flight.getFlightNo(),
                                String.valueOf(-1*flight.getNumberOfTickets()),
                                String.valueOf(flight.getFlightCapacity()));
                        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");

                        String d = date.format(new Date());
                        String t = time.format(new Date());
                        db.addTransaction(new Transaction("Cancellation",username,d,t));
                        String tr = db.getTransactionId(d,t);
                        Reservation reservation = new Reservation(flight.getFlightId(),flight.getNumberOfTickets(),Integer.parseInt(tr),1);
                        db.addReservation(reservation);
                        db.reservationSetToCancel(String.valueOf(flight.getFlightId()));
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
}
