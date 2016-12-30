package com.apps.salilgokhale.expensrapp;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by salilgokhale on 28/12/2016.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter{

    private MainView mainView;

    private FirebaseDatabase mFirebaseDatabase; // the database - main access point
    private DatabaseReference mDatabaseReference; // class that references specific part of the database

    private List<Batch> activeBatches;


    public MainActivityPresenterImpl(MainView mainView) {

        this.mainView = mainView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        // find all batches which have not been submitted in SAP and list according to most recently updated.
    }

    @Override
    public void createBatch(String batchName){
        Batch batch = new Batch(batchName, "", "", 0, false, false);
        mDatabaseReference.child("batches").push().setValue(batch);
    }

    @Override
    public void onDestroy(){
        mainView = null;
    }

    public MainView getMainView(){
        return mainView;
    }

}
