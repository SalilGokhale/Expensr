package com.apps.salilgokhale.expensrapp;

import android.util.Log;

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

    public void requestBatches (){

        Query batchQuery = mDatabaseReference.child("batches").orderByChild("sap");

        batchQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (batchView != null) {
                    Batch batch = dataSnapshot.getValue(Batch.class);
                    batch.setKey(dataSnapshot.getKey());
                    ((BatchesFragment) batchView).getmAdapter().addItem(batch);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildChanged", "activated");
                if (batchView != null) {
                    List<Batch> batches = ((BatchesFragment) batchView).getmAdapter().getmDataset();
                    Log.d("onChildChanged", "batches retrieved");
                    Batch batch = dataSnapshot.getValue(Batch.class);
                    batch.setKey(dataSnapshot.getKey());
                    for (int i = 0; i < batches.size(); i++){
                        Log.d("Batch number", String.valueOf(i));
                        if (batch.getKey().equals(batches.get(i).getKey())){
                            ((BatchesFragment) batchView).getmAdapter().updateItem(i, batch);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (batchView != null) {
                    List<Batch> batches = ((BatchesFragment) batchView).getmAdapter().getmDataset();
                    for (int i = 0; i < batches.size(); i++){
                        if (dataSnapshot.getKey().equals(batches.get(i).getKey())){
                            ((BatchesFragment) batchView).getmAdapter().removeItem(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("OnChild Moved:", "called!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void updateSapBag(int SapBag, boolean isSet, String batchKey){
        Log.d("batchKey:", batchKey);
        switch (SapBag){
            case 0:
                mDatabaseReference.child("batches").child(batchKey).child("sap").setValue(!isSet);
                break;
            case 1:
                mDatabaseReference.child("batches").child(batchKey).child("bag").setValue(!isSet);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy(){
        batchView = null;
    }

}
