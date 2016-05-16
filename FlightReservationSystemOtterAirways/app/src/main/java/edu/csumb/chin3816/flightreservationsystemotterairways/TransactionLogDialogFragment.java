package edu.csumb.chin3816.flightreservationsystemotterairways;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionLogDialogFragment extends DialogFragment implements View.OnClickListener  {

    public SQLHelper db = SQLHelper.getInstance(this.getContext());
    public ListView listView;

    public TransactionLogDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction_log_dialog, null, false);
        listView = (ListView) view.findViewById(R.id.logListView);

        // Step One: set up a click listener using a view for the create account.
        View close = view.findViewById(R.id.closeButton);
        close.setOnClickListener(this);


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayList<Transaction> transactions = db.getAllTransactions();

        TransactionsAdapter adapter = new TransactionsAdapter(getActivity(),
                R.layout.flight_adapter, transactions);

        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        Log.d("OnClick" , "inside the click" + v.getId() + " " + R.id.closeButton);
        if(v.getId() == R.id.closeButton){
            Log.d("OnClick" , "the if ran the click");
            dismiss();
        }
    }
    /*

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater layoutInflater= LayoutInflater.from(this.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_transaction_log_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());


        ArrayList<Transaction> transactions = db.getAllTransactions();

        TransactionsAdapter adapter = new TransactionsAdapter(this.getContext(), R.layout.flight_adapter, transactions);

        ListView listView = (ListView) view.findViewById(R.id.logListView);
        listView.setAdapter(adapter);

       // alertDialogBuilder.setView(listView);

        return alertDialogBuilder.create();
    }*/

}
