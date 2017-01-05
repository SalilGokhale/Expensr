package com.apps.salilgokhale.expensrapp;

import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by salilgokhale on 30/12/2016.
 */

public class ButtonPresenterImpl implements ButtonPresenter {

    private ButtonView buttonView;

    private FirebaseDatabase mFirebaseDatabase; // the database - main access point
    private DatabaseReference mDatabaseReference; // class that references specific part of the database

    public ButtonPresenterImpl(ButtonView buttonView) {

        this.buttonView = buttonView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }

    // TODO figure out logic
    // Button pressed when no new batch
    // Batch created
    // Retrieve ID of new batch and assign to expense when created

    @Override
    public void onButtonClicked(int i,  String batchID){
        Map<String, Object> matchingBatch = new HashMap<>();
        matchingBatch.put(batchID, true);
        Expense expense = new Expense(returnParsedDate(), i, matchingBatch);

        DatabaseReference db = mDatabaseReference.child("expenses").push();
        db.setValue(expense);

        updateBatchDatesTotalMatch(batchID, db.getKey());

        switch (i) {
            case 0:
                Log.d("ButtonPresenter click: ", "hotel");
                break;
            case 1:
                Log.d("ButtonPresenter click: ", "phone");
                break;
            case 2:
                Log.d("ButtonPresenter click: ", "train");
                break;
            case 3:
                Log.d("ButtonPresenter click: ", "taxi");
                break;
            case 4:
                Log.d("ButtonPresenter click: ", "subs");
                break;
            case 5:
                Log.d("ButtonPresenter click: ", "dinner");
                break;
            default: break;
        }
    }

    @Override
    public void onButtonAddBatch(int i){

        if (buttonView != null) {

            if (!(((AddExpenseFragment) buttonView).isBatchesAvailable())) {
                ((MainActivity) ((AddExpenseFragment) buttonView).getActivity()).showAddBatchDialog(true, i);
            }
        }

    }

    private String returnParsedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        if (buttonView != null) {
            String receiptDate = sdf.format(((AddExpenseFragment) buttonView).getDate());
            return receiptDate;
        }
        else {
            return "error in date formatting";
        }
    }

    private Date returnDate(){
        return ((AddExpenseFragment) buttonView).getDate();
    }

    @Override
    public void retrieveActiveBatches(){

        Query batchQuery = mDatabaseReference.child("batches").orderByChild("sap");

        batchQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (buttonView != null){
                    Log.d("onChildAdded", "function called");
                    Batch batch = dataSnapshot.getValue(Batch.class);
                    ((AddExpenseFragment) buttonView).setDataRecieved(true);
                    int spinnerSize = ((AddExpenseFragment) buttonView).getSpinnerAdapter().getCount();
                    if (batch != null){
                        Log.d("ButtonPresenter: ", "Batch Received");
                        if (!batch.isSap()) {
                            batch.setKey(dataSnapshot.getKey());
                            ((AddExpenseFragment) buttonView).getSpinnerAdapter().addItem(batch);
                            ((AddExpenseFragment) buttonView).setBatchesAvailable(true);
                        }
                        else if (batch.isSap() && spinnerSize == 0){
                            ((AddExpenseFragment) buttonView).setBatchesAvailable(false);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (buttonView != null) {
                    List<Batch> batches = ((AddExpenseFragment) buttonView).getSpinnerAdapter().getBatchList();
                    Batch batch = dataSnapshot.getValue(Batch.class);
                    batch.setKey(dataSnapshot.getKey());
                    for (int i = 0; i < batches.size(); i++){
                        if (batch.getKey().equals(batches.get(i).getKey())){
                            ((AddExpenseFragment) buttonView).getSpinnerAdapter().updateItem(i, batch);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (buttonView != null) {
                    List<Batch> batches = ((AddExpenseFragment) buttonView).getSpinnerAdapter().getBatchList();
                    for (int i = 0; i < batches.size(); i++){
                        if (dataSnapshot.getKey().equals(batches.get(i).getKey())){
                            ((AddExpenseFragment) buttonView).getSpinnerAdapter().removeItem(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateBatchDatesTotalMatch(final String batchID, final String expenseKey){
        Log.d("updateBatchDates: ", "Entered");
        ValueEventListener batchListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Listener has", "received Snapshot");
                Batch batch = dataSnapshot.getValue(Batch.class);
                if (batch != null){
                    Log.d("Batch:", "is not null");
                    Log.d("BatchName", batch.getName());
                    if (batch.getMatchingExpenses() != null){
                        Log.d("Batch", "has matching expense");
                        batch.getMatchingExpenses().put(expenseKey, true);
                    }
                    else {
                        Log.d("Batch", "has not matching expense");
                        Map<String, Object> matchingExpense = new HashMap<>();
                        matchingExpense.put(expenseKey, true);
                        batch.setMatchingExpenses(matchingExpense);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    sdf.setLenient(true);
                    batch.setTotal(batch.getTotal()+1);

                    Date date = new Date();
                    String lastUpdate = sdf.format(date);
                    batch.setLastAddedTo(lastUpdate);

                    if (batch.getStartDate().equals("")) {
                        batch.setStartDate(lastUpdate);
                        batch.setEndDate(lastUpdate);
                    }
                    else {

                        try {

                            Date currentStartDate = sdf.parse(batch.getStartDate());
                            Date currentEndDate = sdf.parse(batch.getEndDate());
                            Date eDate = returnDate();
                            if (currentStartDate.after(eDate)) {
                                // update the batch
                                batch.setStartDate(sdf.format(eDate));
                            } else if (currentEndDate.before(returnDate())) {
                                // update the batch
                                batch.setEndDate(sdf.format(eDate));
                            }

                        } catch (ParseException e2) {
                            e2.printStackTrace();
                            Log.d("Date:", "Error");
                        }
                    }

                    mDatabaseReference.child("batches").child(batchID).setValue(batch);

                }
                else {
                    Log.d("Batch:", "is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.child("batches").child(batchID).addListenerForSingleValueEvent(batchListener);
    }

    @Override
    public void onDestroy(){
        buttonView = null;
    }


}
