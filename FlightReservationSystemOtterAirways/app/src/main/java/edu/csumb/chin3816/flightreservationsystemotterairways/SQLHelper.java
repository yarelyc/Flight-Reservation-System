package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yarelychino on 5/10/16.
 */
public class SQLHelper extends SQLiteOpenHelper{

    //singleton
    private static SQLHelper uniqueInstance;
    // Log TAG
    private static final String TAG = "SQLiteHelper";

    //Database Name
    private static final String DATABASE_NAME = "FlightReservation";
    private static final int DATABASE_VERSION = 1;

    //Table Name -
    private static final String TABLE_USER = "user";
    private static final String TABLE_FLIGHT = "flight";
    private static final String TABLE_TRANSACTION = "transact";
    private static final String TABLE_RESERVATION = "reservation";


    // Columns Names for user table
    private static final String KEY_USERID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ISADMIN = "isAdmin";

    // Columns Names for flight table
    private static final String KEY_FLIGHTID = "flightId";
    private static final String KEY_FLIGHTNO = "flightNo";
    private static final String KEY_DEPARTURE = "departure";
    private static final String KEY_ARRIVAL = "arrival";
    private static final String KEY_DEPARTURETIME = "departureTime";
    private static final String KEY_FLIGHTCAPACITY = "flightCapacity";
    private static final String KEY_PRICE = "price";

    // Columns Names for transaction table
    private static final String KEY_TRANSACTIONID = "transactionId";
    private static final String KEY_TRANSACTIONTYPE = "transactionType";
    private static final String KEY_TRANSACTIONDATE = "transactionDate";
    private static final String KEY_TRANSACTIONTIME = "transactionTime";


    // Columns Names for reservation table
    private static final String KEY_RESERVATIONID = "reservationId";
    private static final String KEY_NUMBEROFTICKETS = "numberOfTickets";
    private  static final String KEY_ISCANCEL = "isCancel";


    //private constructor to develop a singleton behavior
    private SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLHelper getInstance(Context context){
        if(uniqueInstance == null){
            //will only instantiate once after I will return the pointer
            uniqueInstance = new SQLHelper(context);
            //add the admin and others
        }
        return uniqueInstance;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE user ( "+
                KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_ISADMIN + " INTEGER );";

        // execute an SQL statement to create the table
        db.execSQL(CREATE_USER_TABLE);
        Log.d(TAG, "after creating user table");

        String CREATE_FLIGHT_TABLE = "CREATE TABLE flight ( "+
                KEY_FLIGHTID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FLIGHTNO + " TEXT, " +
                KEY_DEPARTURE + " TEXT, " +
                KEY_ARRIVAL + " TEXT, " +
                KEY_DEPARTURETIME + " TEXT, " +
                KEY_FLIGHTCAPACITY + " INTEGER, " +
                KEY_PRICE + " DOUBLE );";

        //TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        //datetime default current_timestamp
        db.execSQL(CREATE_FLIGHT_TABLE);
        Log.d(TAG, "after creating flight table");

       /* String CREATE_TRANSACTION_TABLE = "CREATE TABLE transaction ( "+
                KEY_TRANSACTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TRANSACTIONTYPE + " TEXT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_TRANSACTIONDATE + " TEXT, " +
                KEY_TRANSACTIONTIME + " TEXT )";
        */
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE transact ( "+
                KEY_TRANSACTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TRANSACTIONTYPE + " TEXT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_TRANSACTIONDATE + " TEXT, " +
                KEY_TRANSACTIONTIME + " TEXT );";

        Log.d(TAG, CREATE_TRANSACTION_TABLE);

        db.execSQL(CREATE_TRANSACTION_TABLE);
        Log.d(TAG, "after creating transaction table");

        String CREATE_RESERVATION_TABLE = "CREATE TABLE reservation ( "+
                KEY_RESERVATIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FLIGHTID + " INTEGER, " +
                KEY_NUMBEROFTICKETS + " INTEGER, " +
                KEY_TRANSACTIONID + " INTEGER, " +
                KEY_ISCANCEL + " INTEGER );";
        db.execSQL(CREATE_RESERVATION_TABLE);

        Log.d(TAG, "after creating reservation table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS transaction");
        db.execSQL("DROP TABLE IF EXISTS flight");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        // create fresh books table
        this.onCreate(db);
    }

    public void addUser(User user){
        Log.d(TAG, "before adding a user");

        if(isUserNameAvailable(user.getUserName())){
            // 1. get reference to writable DB
            SQLiteDatabase db = this.getWritableDatabase();

            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, user.getUserName()); // get userName
            values.put(KEY_PASSWORD, user.getPassword()); // get password
            values.put(KEY_ISADMIN, user.getIsAdmin()); // get isAdmin

            //STEP 3.
            db.insert(TABLE_USER, null, values);

            //Step 4.
            db.close();
            Log.d(TAG, "user was added");
        }

    }

    public boolean addFlight(Flight flight){
        Log.d(TAG, "before adding a flight");
        if(isFlightNoAvailable(flight.getFlightNo())){
            Log.d(TAG, "After if Statement adding a flight");
            // 1. get reference to writable DB
            SQLiteDatabase db = this.getWritableDatabase();

            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(KEY_FLIGHTNO, flight.getFlightNo()); // get NUMBER
            values.put(KEY_DEPARTURE, flight.getDeparture()); // get DEPARTURE
            values.put(KEY_ARRIVAL, flight.getArrival()); // get ARRIVAL
            values.put(KEY_DEPARTURETIME, flight.getDepartureTime()); // get TIME
            values.put(KEY_FLIGHTCAPACITY, flight.getFlightCapacity()); // get CAPACITY
            values.put(KEY_PRICE, flight.getPrice()); // get PRICE

            //STEP 3.
            db.insert(TABLE_FLIGHT,null, values);

            //Step 4.
            db.close();
            Log.d(TAG, "FLIGHT was added");
            return true;
        }
        return false;

    }

    public void addTransaction(Transaction transaction){
        Log.d(TAG, "before adding a TRANSACTION");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "aftercrating the writable");
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TRANSACTIONTYPE, transaction.getTransactionType()); // get TYPE
        values.put(KEY_USERNAME, transaction.getUserName()); // get NAME
        values.put(KEY_TRANSACTIONDATE, transaction.getTransactionDate()); // get TIME
        values.put(KEY_TRANSACTIONTIME, transaction.getTransactionTime()); // get TIME

        Log.d(TAG, "putting all the values");
        //STEP 3.
        db.insert(TABLE_TRANSACTION,null, values);

        Log.d(TAG, "before closing");
        //Step 4.
        db.close();
        Log.d(TAG, "TRANSACTION was added");
    }


    public void addReservation (Reservation reservation ){
        Log.d(TAG, "before adding a RESERVATION");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FLIGHTID, reservation.getFlightId()); // get FLIGHTID
        values.put(KEY_NUMBEROFTICKETS, reservation.getNumberOfTickets()); // get NUMBEROFTICKETS
        values.put(KEY_TRANSACTIONID, reservation.getTransactionId()); // get TRANSACTIONID
        values.put(KEY_ISCANCEL, reservation.getIsCancel());

        //STEP 3.
        db.insert(TABLE_RESERVATION, null, values);

        //Step 4.
        db.close();
        Log.d(TAG, "RESERVATION was added");
    }

    // Get all users from the database
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USER;

        Log.d(TAG, "before getting all the users");
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setUserId(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setIsAdmin(Integer.parseInt(cursor.getString(3)));
                // Add user to users
                users.add(user);
                Log.d(TAG, "getAllUsers() - " + user.toString());
            } while (cursor.moveToNext());
        }
        // return users
        return users;
    }

    public ArrayList<Flight> getAllFlights(){
        ArrayList<Flight> flights = new ArrayList<>();

        //1. build query
        String query  = "SELECT * FROM " + TABLE_FLIGHT;

        //2. GET reference TO WRITABLE DB
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Flight flight = null;

        if(cursor.moveToFirst()){
            do{
                flight = new Flight();

                flight.setFlightId(Integer.parseInt(cursor.getString(0)));
                flight.setFlightNo(cursor.getString(1));
                flight.setDeparture(cursor.getString(2));
                flight.setArrival(cursor.getString(3));
                flight.setDepartureTime(cursor.getString(4));
                flight.setFlightCapacity(Integer.parseInt(cursor.getString(5)));
                flight.setPrice(Double.parseDouble(cursor.getString(6)));

                flights.add(flight);

                Log.d(TAG, "getAllFlights() - " + flight.toString());
            }while(cursor.moveToNext());//movetonext checks if i can move to the next row
        }
        return flights;
    }

    public ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();

        //1. build query
        String query  = "SELECT * FROM " + TABLE_TRANSACTION;

        //2. GET reference TO WRITABLE DB
        SQLiteDatabase database = this.getReadableDatabase();


        Cursor cursor = database.rawQuery(query,null);
        Log.d(TAG, "before going to movetofirstCC");
        Transaction transaction = null;

        Log.d(TAG, "before going to movetofirstDD");
        if(cursor.moveToFirst()){
            do{
                transaction = new Transaction();

                transaction.setTransactionId(Integer.parseInt(cursor.getString(0)));
                transaction.setTransactionType(cursor.getString(1));
                transaction.setUserName(cursor.getString(2));
                transaction.setTransactionDate(cursor.getString(3));
                transaction.setTransactionTime(cursor.getString(4));

                transactions.add(transaction);
                Log.d(TAG, "getAllTransactions() - " + transaction.toString());

            }while(cursor.moveToNext());//movetonext checks if i can move to the next row
        }
        return transactions;
    }

    public ArrayList<Reservation> getAllReservations(){
        ArrayList<Reservation> reservations = new ArrayList<>();

        //1. build query
        String query  = "SELECT * FROM " + TABLE_RESERVATION;
        Log.d(TAG, "after query selection");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.d(TAG, "aftr raw query");
        Reservation reservation = null;

        Log.d(TAG, "about to move to first");
        if(cursor.moveToFirst()){
            Log.d(TAG, "inside move to first do while");
            do{

                reservation = new Reservation();

                reservation.setReservationId(Integer.parseInt(cursor.getString(0)));
                reservation.setFlightId(Integer.parseInt(cursor.getString(1)));
                reservation.setNumberOfTickets(Integer.parseInt(cursor.getString(2)));
                reservation.setTransactionId(Integer.parseInt(cursor.getString(3)));
                reservation.setIsCancel(Integer.parseInt(cursor.getString(4)));

                reservations.add(reservation);

                Log.d(TAG, "getAllReservation() - " + reservation.toString());
            }while(cursor.moveToNext());//movetonext checks if i can move to the next row
        }

        return reservations;
    }

    // Deleting  Reservation
    public void deleteReservation(Reservation reservation) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_RESERVATION,
                KEY_RESERVATIONID + " = ?",
                new String[]{String.valueOf(reservation.getReservationId())});
        // 3. close
        db.close();

        Log.d(TAG, "Delete reservation() - " + reservation.toString());
    }

    // Deleting user
    public void deleteUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USER,
                KEY_USERID+" = ?",
                new String[] { String.valueOf(user.getUserId()) });
        // 3. close
        db.close();

        Log.d(TAG, "Delete user() - " + user.toString());
    }

    // Deleting users
    public void deleteFlight(Flight flight) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FLIGHT,
                KEY_FLIGHTID+" = ?",
                new String[] { String.valueOf(flight.getFlightId()) });
        // 3. close
        db.close();

        Log.d(TAG, "Delete flight() - " + flight.toString());
    }

    public Boolean isUserNameAvailable(String name){
        //1. build query
        String query  = "SELECT "+KEY_USERNAME +" FROM " + TABLE_USER + " WHERE " +
                KEY_USERNAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,new String[] { String.valueOf(name) } );

        if(cursor.moveToFirst()){
            return false;
        }
        return true;
    }

    public Boolean isAdmin(String name, String password){
        //1. build query
        String query  = "SELECT  * FROM " + TABLE_USER + " WHERE " +
                KEY_USERNAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,new String[] { String.valueOf(name) } );

        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "Name: "+cursor.getString(1));
                Log.d(TAG, "Password: "+cursor.getString(2));
                Log.d(TAG, "isAdmin: "+cursor.getString(3));
                if(cursor.getString(1).equals(name) && cursor.getString(2).equals(password) && Integer.parseInt(cursor.getString(3)) == 1){
                    return true;
                }

            }while(cursor.moveToNext());
        }
        return false;
    }


    public Boolean isUser(String name, String password){
        //1. build query
        String query  = "SELECT * FROM " + TABLE_USER + " WHERE " +
                KEY_USERNAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,new String[] { String.valueOf(name) } );

        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(name) && cursor.getString(2).equals(password)){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        return false;
    }

    public Boolean isFlightNoAvailable(String flightNo){
        //1. build query
        String query  = "SELECT "+ KEY_FLIGHTNO +" FROM " + TABLE_FLIGHT + " WHERE " +
                KEY_FLIGHTNO + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,new String[] { String.valueOf(flightNo) } );

        if(cursor.moveToFirst()){
            do{

                if(cursor.getString(0).equals(flightNo)){
                    Log.d(TAG, "isFlightNoAV after cursor statment");
                    return false;
                }
            }while(cursor.moveToNext());
        }
        return true;
    }

    public ArrayList<Flight> getFlights(String depart, String arrival, String flightCapacity){
        ArrayList<Flight> flights = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_FLIGHT + " WHERE " +
                KEY_FLIGHTCAPACITY + " >= ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(flightCapacity)});

        Flight flight;
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(2).equalsIgnoreCase(depart) && cursor.getString(3).equalsIgnoreCase(arrival)){
                    flight = new Flight(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));
                    flight.setFlightId(cursor.getShort(0));

                    flights.add(flight);
                    Log.d(TAG, "getFlights() - " + flight.toString());
                }

            }while(cursor.moveToNext());
            Log.d(TAG, " Flights were found");
        }
        Log.d(TAG, " Flights were not found");
        return flights;
    }

    public Boolean isThereReservationTransaction(String name){
        String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " +
                KEY_USERNAME + " = ? AND " + KEY_TRANSACTIONTYPE + " = 'Reservation' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if(cursor.moveToFirst()){//1. there is  transaction  of type reservation with that username
            //2. to verify that the transaction has not been cancel
            if(isReservationCancel(Integer.parseInt(cursor.getString(0)))){
                return true;
            }
        }
        return false;
    }

    public Boolean isReservationCancel(int transactionId){
        String query = "SELECT * FROM " + TABLE_RESERVATION + " WHERE " +
                 KEY_TRANSACTIONID + " = ? AND " +
                KEY_ISCANCEL + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(transactionId)});
        if(cursor.moveToFirst()){ //the transaction is there
            return true;
        }
        return false;
    }

    public String getTransactionId(String date, String time){

        String query = "SELECT "+ KEY_TRANSACTIONID +" FROM " + TABLE_TRANSACTION + " WHERE " +
                KEY_TRANSACTIONTIME + " = ? AND " +
                KEY_TRANSACTIONDATE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{time, date});
        if(cursor.moveToFirst()){ //the transaction is there
           return cursor.getString(0);
        }
        return null;
    }

    public void updateFlightCapacity(String flightNo, String minus, String capacity){
        SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            try{
                int i = Integer.parseInt(minus);
                int total = Integer.parseInt(capacity);
                values.put(KEY_FLIGHTCAPACITY, total-i);
            }catch (Exception e){
                return;
            }
            int i = db.update(TABLE_FLIGHT, //table
                    values, // column/value
                    KEY_FLIGHTNO + " = ?", // selections
                    new String[]{flightNo}); //selection arg

        db.close();

    }

    public ArrayList<Flight> getReservationToCancel(String name){
        ArrayList<Flight> flights = new ArrayList<>();

        Log.d("insqlhelper"," in get Reservation to Cancel");
        String query= "SELECT " +  KEY_RESERVATIONID + ", " + KEY_NUMBEROFTICKETS +
                 ", " + KEY_FLIGHTNO + ", " + KEY_DEPARTURE + ", " + KEY_ARRIVAL +
                ", " + KEY_DEPARTURETIME + ", " + KEY_FLIGHTCAPACITY + ", " + KEY_PRICE + " FROM " + TABLE_TRANSACTION +
                " NATURAL JOIN " + TABLE_RESERVATION + " NATURAL JOIN " + TABLE_FLIGHT + " WHERE " + KEY_USERNAME +
                " = ? AND " + KEY_ISCANCEL + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("insqlhelper"," after creating the database to Cancel");
        Flight flight = new Flight();
        Cursor cursor = db.rawQuery(query, new String[]{name});
        if(cursor.moveToFirst()){
            do{
                flight = new Flight();

                flight.setFlightId(Integer.parseInt(cursor.getString(0)));
                flight.setNumberOfTickets(Integer.parseInt(cursor.getString(1)));
                flight.setFlightNo(cursor.getString(2));
                flight.setDeparture(cursor.getString(3));
                flight.setArrival(cursor.getString(4));
                flight.setDepartureTime(cursor.getString(5));
                flight.setFlightCapacity(Integer.parseInt(cursor.getString(6)));
                flight.setPrice(Double.parseDouble(cursor.getString(7)));

                flights.add(flight);

                Log.d(TAG, "getReservationToCancel() - " + flight.toString());
            }while(cursor.moveToNext());//movetonext checks if i can move to the next row
        }
        return flights;
    }


    public boolean reservationSetToCancel (String transactionId){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_ISCANCEL, 1);

            int i = db.update(TABLE_RESERVATION,
                    values,
                    KEY_RESERVATIONID + " = ?",
                    new String[]{transactionId});
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Flight getReservation(String transactionId){
        Flight flight = new Flight();

        //1. build query
        String query= "SELECT " + KEY_NUMBEROFTICKETS +
                ", " + KEY_FLIGHTNO + ", " + KEY_DEPARTURE + ", " + KEY_ARRIVAL +
                ", " + KEY_DEPARTURETIME + ", " + KEY_PRICE +", " +KEY_RESERVATIONID +" FROM " +
                TABLE_RESERVATION + " NATURAL JOIN " + TABLE_FLIGHT + " WHERE " + KEY_TRANSACTIONID +
                " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,new String[] { String.valueOf(transactionId) } );

        if(cursor.moveToFirst()){
            do{
                flight.setNumberOfTickets(Integer.parseInt(cursor.getString(0)));
                flight.setFlightNo(cursor.getString(1));
                flight.setDeparture(cursor.getString(2));
                flight.setArrival(cursor.getString(3));
                flight.setDepartureTime(cursor.getString(4));
                flight.setPrice(Double.parseDouble(cursor.getString(5)));
                flight.setFlightId(Integer.parseInt(cursor.getString(6)));
            }while(cursor.moveToNext());
        }

        return flight;
    }
}