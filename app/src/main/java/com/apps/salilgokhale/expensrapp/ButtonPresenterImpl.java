package com.apps.salilgokhale.expensrapp;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by salilgokhale on 30/12/2016.
 */

public class ButtonPresenterImpl implements ButtonPresenter {

    private ButtonView buttonView;

    private FirebaseDatabase mFirebaseDatabase; // the database - main access point
    private DatabaseReference mDatabaseReference; // class that references specific part of the database

    private boolean connected = false;

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
        switch (i) {
            case 0:
                Log.d("ButtonPresenter click: ", "hotel");
                Map<String, Object> matchingBatch = new HashMap<>();
                matchingBatch.put(batchID, true);
                Expense expense = new Expense(returnParsedDate(), 0, matchingBatch);

                DatabaseReference db = mDatabaseReference.child("expenses").push();
                db.setValue(expense);

            /*
            case 1:
                null;
            case 2:
                null;
            case 3:
                null;
            case 4:
                null;
            case 5:
                null;
            case 6:
                null; */
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

    @Override
    public void retrieveActiveBatches(){

        Query batchQuery = mDatabaseReference.child("batches").orderByChild("sap");
        /*
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d("Timer complete: ", "3 seconds!");
                if (!connected){
                    Log.d("ButtonPresenter: ", "No Batches Received");
                    ((AddExpenseFragment) buttonView).setBatchesAvailable(false);
                }
            }
        }.start(); */

        batchQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                connected = true;
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

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDestroy(){
        buttonView = null;
    }


}
