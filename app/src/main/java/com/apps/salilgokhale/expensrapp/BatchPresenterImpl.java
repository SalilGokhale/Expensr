package com.apps.salilgokhale.expensrapp;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salilgokhale on 30/12/2016.
 */

public class BatchPresenterImpl implements BatchPresenter {

    private BatchView batchView;

    private FirebaseDatabase mFirebaseDatabase; // the database - main access point
    private DatabaseReference mDatabaseReference; // class that references specific part of the database

    public BatchPresenterImpl(BatchView batchView) {

        this.batchView = batchView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }

    // TODO retrieve batch list data from Firebase to pass to adapter

    public void requestBatches (){

        Query batchQuery = mDatabaseReference.child("batches").orderByChild("sap");

        batchQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (batchView != null)
                    ((BatchesFragment) batchView).getmAdapter().addItem(dataSnapshot.getValue(Batch.class));
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

    // TODO pass changes from adapter back up to Firebase

    @Override
    public void onDestroy(){
        batchView = null;
    }

}
