package com.apps.salilgokhale.expensrapp;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    public void onButtonClicked(int i){
        switch (i) {
            case 0:
                Log.d("ButtonPresenter click: ", "yes!");
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
    public void onDestroy(){
        buttonView = null;
    }

    @Override
    public void retrieveActiveBatches(){
        // TODO implement this method
    }
}
