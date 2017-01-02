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

    public MainActivityPresenterImpl(MainView mainView) {

        this.mainView = mainView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("batches");

    }

    @Override
    public void createBatch(String batchName, boolean newB, int i){
        Batch batch = new Batch(batchName, "", "", 0, false, false);
        DatabaseReference db = mDatabaseReference.push();
        db.setValue(batch);

        if (newB){
            String batchID = db.getKey();

            if (mainView != null){

                ((AddExpenseFragment)((MainActivity.ViewPagerAdapter)((MainActivity) mainView).
                        getViewPager().getAdapter()).getItem(0)).getButtonPresenter().onButtonClicked(i, batchID);

            }
        }
    }

    @Override
    public void onDestroy(){
        mainView = null;
    }

    public MainView getMainView(){
        return mainView;
    }

}
