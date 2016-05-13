package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String usernameString;
    private String passwordString;
    private Boolean isContinue = new Boolean(true);
    private SQLHelper db = SQLHelper.getInstance(this);
    private int type, errors;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.addUser(new User("A@lice5", "@cSit100", 0));
        db.addUser(new User("$BriAn7", "123aBc##", 0));
        db.addUser(new User("!chriS12!", "CHrIS12!!", 0));
        db.addUser(new User("!admiM2", "!admiM2", 1));
        db.addFlight(new Flight("Otter101", "Monterey", "Los Angeles", "10:30 AM", 10, 150.0));
        db.addFlight(new Flight("Otter102", "Los Angeles", "Monterey", "1:00 PM", 10, 150.0));
        db.addFlight(new Flight("Otter201", "Monterey", "Seattle", "11:00 AM", 5, 200.5));
        db.addFlight(new Flight("Otter205", "Monterey", "Seattle", "3:45 PM", 15, 150.0));
        db.addFlight(new Flight("Otter202", "Seattle", "Monterey", "2:10 PM", 5, 200.5));
        // Step One: set up a click listener using a view for the create account.
        View createAccount = findViewById(R.id.createAccountButton);
        createAccount.setOnClickListener(this);

        // Step One: set up a click listener using a view for the create account.
        View reservation = findViewById(R.id.reservationSeatButton);
        reservation.setOnClickListener(this);

        // Step One: set up a click listener using a view for the create account.
        View cancelReservation = findViewById(R.id.cancelReservationButton);
        cancelReservation.setOnClickListener(this);

        // Step One: set up a click listener using a view for the create account.
        View manageSystem = findViewById(R.id.manageSystemButton);
        manageSystem.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick(View v) {
        EditText cinput;
        Log.d("Main Activity", "buttonClick");
        if (v.getId() == R.id.createAccountButton) {
            launchDialog("Create Account");
            type = 1;
            //I want to launch a new activity
            //Toast.makeText(this,"create account",Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.reservationSeatButton) {
            startActivity(new Intent(this,ReserveSeat.class));
            //Toast.makeText(this,"create account",Toast.LENGTH_LONG).show();

        } else if (v.getId() == R.id.manageSystemButton) {
            launchDialog("Manage System Login");
            //Toast.makeText(this,"manage",Toast.LENGTH_LONG).show();
            type =2;
            isContinue = true;
        }else if (v.getId() == R.id.cancelReservationButton) {
            type = 3;
            launchDialog("Cancel Reservation");

            //Toast.makeText(this,"create account",Toast.LENGTH_LONG).show();
        }

    }

    public void launchDialog(String titleName) {
        // show it // get dialog.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        errors = 0;

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


                errors++;
                //match1.find() && match2.find() && match3.find() && match4.find() && match5.find() &&
               // match6.find() && match7.find() && match8.find()
                //Log.d("Main Activity", "AA "+usernameString+" " + match.find() + " " + match1.find() + " " + match2.find());
                if(match1.find() && match2.find() && match3.find() && match4.find() && match5.find() &&
                   match6.find() && match7.find() && match8.find()){

                    if(type == 1){
                        if(db.isUserNameAvailable(userName.getText().toString())){
                            db.addUser(new User(userName.getText().toString(), userPassword.getText().toString(), 0));

                            SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                            SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");

                            String d = date.format(new Date());
                            String t = time.format(new Date());
                            db.addTransaction(new Transaction("New Account ", userName.getText().toString(), d, t));
                           // db.addReservation(new Reservation(123, 2,123));
                            Toast.makeText(v.getContext(),"Account was successfully created",Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }else if(errors == 2){
                            errorDialog(v, "Sorry, Please try again");
                            alertDialog.dismiss();
                        }else{
                            Toast.makeText(v.getContext(),"Invalid Username/Password",Toast.LENGTH_SHORT).show();
                        }
                        //account was created
                    }else if(type == 2){
                        //the system manager button was selected
                        if(db.isAdmin(userName.getText().toString(), userPassword.getText().toString())){
                            ArrayList<User> list = db.getAllUsers();
                            Log.d("main", "afterretriving all users");
                            ArrayList<Reservation> list1 = db.getAllReservations();
                            Log.d("main", "before");
                            ArrayList<Transaction> list2 = db.getAllTransactions();
                            ArrayList<Flight> list3 = db.getAllFlights();
                            ArrayList<Reservation> list4= db.getAllReservations();
                            alertDialog.dismiss();
                        }else if(errors == 2){
                            errorDialog(v, "Sorry, Please try again");
                            alertDialog.dismiss();
                        }

                    }else if(type == 3){
                        //cancel reservation button was selected
                        if(db.isThereReservationTransaction(userName.getText().toString())){
                            Log.d("Main Activity", "there is a reservation available to cancel");
                        }else{
                            errorDialog(v, "No Reservation Available for: " + userName.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                    Log.d("Main Activity", "there is a lowerCase and uppercase");
                }else{
                    if(type == 1 && errors == 2){
                        errorDialog(v, "Sorry, Please try again");
                        alertDialog.dismiss();
                    }
                    Toast.makeText(v.getContext(),"Incorrect Username",Toast.LENGTH_SHORT).show();
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
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        errors = 0;
    }


}
