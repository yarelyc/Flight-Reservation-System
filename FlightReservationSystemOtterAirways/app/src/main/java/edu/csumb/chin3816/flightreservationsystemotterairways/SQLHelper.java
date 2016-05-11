package edu.csumb.chin3816.flightreservationsystemotterairways;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String TABLE_TRANSACTION = "transaction";
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


    //private constructor to develop a singleton behavior
    private SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLHelper getInstance(Context context){
        if(uniqueInstance == null){
            //will only instantiate once after I will return the pointer
            uniqueInstance = new SQLHelper(context);
        }
        return uniqueInstance;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE user ( "+
                KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_ISADMIN + " INTEGER )";

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
                KEY_PRICE + " DOUBLE )";

        //TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        //datetime default current_timestamp
        db.execSQL(CREATE_FLIGHT_TABLE);
        Log.d(TAG, "after creating flight table");

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE transaction ( "+
                KEY_TRANSACTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TRANSACTIONTYPE + " TEXT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_TRANSACTIONDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                KEY_TRANSACTIONTIME + " TEXT )";

        db.execSQL(CREATE_TRANSACTION_TABLE);
        Log.d(TAG, "after creating transaction table");

        String CREATE_RESERVATION_TABLE = "CREATE TABLE reservation ( "+
                KEY_RESERVATIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FLIGHTID + " INTEGER, " +
                KEY_NUMBEROFTICKETS + " INTEGER, " +
                KEY_TRANSACTIONID + " INTEGER )";
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

    public void addFlight(Flight flight){
        Log.d(TAG, "before adding a flight");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FLIGHTNO, flight.getFlightNo()); // get userName
        values.put(KEY_DEPARTURE, flight.getDeparture()); // get password
        values.put(KEY_ARRIVAL, flight.getArrival()); // get isAdmin
        values.put(KEY_DEPARTURETIME, flight.getDepartureTime()); // get userName
        values.put(KEY_FLIGHTCAPACITY, flight.getFlightCapacity()); // get password
        values.put(KEY_PRICE, flight.getPrice()); // get isAdmin

        //STEP 3.
        db.insert(TABLE_FLIGHT,null, values);

        //Step 4.
        db.close();
        Log.d(TAG, "FLIGHT was added");
    }
}
