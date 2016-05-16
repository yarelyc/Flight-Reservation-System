package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yarelychino on 5/11/16.
 */
public class FlightView extends Activity {

    private ArrayList<Flight> flights;
    private SQLHelper db = SQLHelper.getInstance(this);
    private String usernameString;
    private String passwordString;
    private Flight reserve;
    private String num;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_view);

        Bundle extras = getIntent().getExtras();
        String ar = extras.getString("arrival");
        String dep = extras.getString("departure");
        num = extras.getString("numberOfTickets");

        flights = db.getFlights(dep, ar, num);

        if(flights.isEmpty()){
          //not found
        }else{
            FlightAdapter adapter = new FlightAdapter(this, R.layout.flight_adapter,flights);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    reserve = flights.get(position);
                    launchDialog("Reserve Seat");
                }

            });
        }


    }

    public void launchDialog(String titleName) {
        // show it // get dialog.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        int errors = 0;

        // set dialog.xml to alert dialog builder
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle(titleName);
        final EditText userName = (EditText) dialogView
                .findViewById(R.id.userName);
        final EditText userPassword = (EditText) dialogView
                .findViewById(R.id.password);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        //override the positive button
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main Activity", "button okay in dialog was click");
                usernameString = userName.getText().toString();
                passwordString = userPassword.getText().toString();
                Log.d("Main Activity", userName.getText().toString());
                Log.d("Main Activity", userPassword.getText().toString());
                // Pattern last = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#&!])");
                Pattern one = Pattern.compile("[a-z]");
                Pattern two = Pattern.compile("[A-Z]");
                Pattern three = Pattern.compile("@|#|$|!");
                Pattern four = Pattern.compile("[0-9]");
                Log.d("Main Activity", "after greating patterns");

                Matcher match1 = one.matcher(usernameString);
                Matcher match2 = two.matcher(usernameString);
                Matcher match3 = three.matcher(usernameString);
                Matcher match4 = four.matcher(usernameString);

                Matcher match5 = one.matcher(passwordString);
                Matcher match6 = two.matcher(passwordString);
                Matcher match7 = three.matcher(passwordString);
                Matcher match8 = four.matcher(usernameString);

                //match1.find() && match2.find() && match3.find() && match4.find() && match5.find() &&
                // match6.find() && match7.find() && match8.find()
                //Log.d("Main Activity", "AA "+usernameString+" " + match.find() + " " + match1.find() + " " + match2.find());
                if (match1.find() && match2.find() && match3.find() && match4.find() && match5.find() &&
                        match6.find() && match7.find() && match8.find()) {
                    if (db.isUser(userName.getText().toString(), userPassword.getText().toString())) {
                        try {
                            Log.d("FlightView", "in try caatch");
                            double i = Double.parseDouble(num);
                            String msg = "Username: " + userName.getText().toString() +
                                    "\nFlight Number: " + reserve.getFlightNo() +
                                    "\nDeparture: " + reserve.getDeparture() + ", " + reserve.getDepartureTime() +
                                    "\nArrival: " + reserve.getArrival() +
                                    "\nNumber of tickers: " + num +
                                    "\nTotal amount: $" + i * reserve.getPrice() + "\n";
                            Log.d("FlightView", "before confirmation dialog");
                            confirmationDialog(v, msg, "Comfirmation", userName.getText().toString(), String.valueOf(reserve.getFlightCapacity()));
                        } catch (Exception e) {

                        }
                        alertDialog.dismiss();
                    }
                } else {

                }

            }
        });
    }
    public void confirmationDialog(View v, String msg, String title, final String username, final String capacity){
        final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
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
                });


    }
    public void errorDialog(View v, String msg){
        new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

}
