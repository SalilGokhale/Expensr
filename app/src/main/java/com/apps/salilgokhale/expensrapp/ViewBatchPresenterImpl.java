package com.apps.salilgokhale.expensrapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by salilgokhale on 05/01/2017.
 */

public class ViewBatchPresenterImpl implements ViewBatchPresenter {

    private ViewBatchView viewBatchView;
    private Context context;

    private FirebaseDatabase mFirebaseDatabase; // the database - main access point
    private DatabaseReference mDatabaseReference; // class that references specific part of the database

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    List<Week> weekList = new ArrayList<>();

    public ViewBatchPresenterImpl(ViewBatchView viewBatchView, Context context) {

        this.viewBatchView = viewBatchView;
        this.context = context;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }

    @Override
    public void retrieveBatchInfo(String batchKey){
        mDatabaseReference.child("batches").child(batchKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Batch batch = dataSnapshot.getValue(Batch.class);
                if (batch != null) {
                    batch.setKey(dataSnapshot.getKey());
                    Log.d("Batch Retrieved", batch.getKey());
                    viewBatchView.setBatchItem(batch);
                }
                else {
                    Toast toast = Toast.makeText(context, "Error, No batch retrieved1", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void retrieveExpensesForBatch(String batchKey){

        Query batchRef = mDatabaseReference.child("batches").child(batchKey).child("matchingExpenses");
        final DatabaseReference expRef = mDatabaseReference.child("expenses");

        batchRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                expRef.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1 != null) {
                            Expense expense = dataSnapshot1.getValue(Expense.class);
                            expense.setKey(dataSnapshot1.getKey());
                            Log.d("Expense retrieved", expense.getKey());

                            try {
                                expense.setActualDate(sdf.parse(expense.getReceiptDate()));
                                addExpenseToWeek(expense);
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                                Log.d("Date:", "Error");
                            }
                        }
                        else {
                            Log.d("Error", "No expense retrieved");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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

    public void addExpenseToWeek(Expense expense){

        Date expenseDate = expense.getActualDate();

        Log.d("addExpenseToWeek", "entered");
        Log.d("Actual date", sdf.format(expenseDate));
        Calendar c = Calendar.getInstance();
        c.setTime(expense.getActualDate());
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date startDate = c.getTime();
        Log.d("Start date", sdf.format(startDate));

        if (weekList.size() == 0 || startDate.after(weekList.get(weekList.size()-1).getStartDate())) {
            Log.d("if function", "weeklist=0 || latestartdate");
            Log.d("weekList size", String.valueOf(weekList.size()));

            c.add(Calendar.DATE, 6);
            Date endDate = c.getTime();

            List<DayExpense> dayExpenseList = new ArrayList<>();
            dayExpenseList.add(new DayExpense(expenseDate));
            dayExpenseList.add(new DayExpense(expense));

            Week newWeek = new Week("", startDate, endDate, dayExpenseList);
            Log.d("Week added", newWeek.getWeekName());
            weekList.add(newWeek);
            viewBatchView.getExpenseExpandableAdapter().notifyParentItemInserted(weekList.size()-1);

        }
        else if (startDate.before(weekList.get(weekList.size()-1).getStartDate())){
            Log.d("if function", "earlystartdate");
            Log.d("weekList size", String.valueOf(weekList.size()));
            Log.d("weekList start date:", sdf.format(weekList.get(weekList.size()-1).getStartDate()));

            c.add(Calendar.DATE, 6);
            Date endDate = c.getTime();

            List<DayExpense> dayExpenseList = new ArrayList<>();
            dayExpenseList.add(new DayExpense(expenseDate));
            dayExpenseList.add(new DayExpense(expense));

            Week newWeek = new Week("", startDate, endDate, dayExpenseList);
            Log.d("Week added", newWeek.getWeekName());
            weekList.add(0, newWeek);
            viewBatchView.getExpenseExpandableAdapter().notifyParentItemInserted(0);
        }
        else {
            Log.d("if function", "inbetween");
            Log.d("weekList size", String.valueOf(weekList.size()));
            for (int j = 0; j < weekList.size(); j++){
                if (startDate.equals(weekList.get(j).getStartDate())){
                    Log.d("Expense has", "same week!");
                    Log.d("weeklist start date", sdf.format(weekList.get(j).getStartDate()));
                    List<DayExpense> dayExpenseChildren = weekList.get(j).getChildItemList();
                    int childListLastIndex = dayExpenseChildren.size() - 1;

                    if (expenseDate.before(dayExpenseChildren.get(0).getDate())){
                        Log.d("day", "before first");
                        weekList.get(j).getChildItemList().add(0, new DayExpense(expenseDate));
                        weekList.get(j).getChildItemList().add(1, new DayExpense(expense));
                        viewBatchView.getExpenseExpandableAdapter().notifyChildItemInserted(j, 0);
                    }
                    else if (expenseDate.after(dayExpenseChildren.get(childListLastIndex).getDate())){
                        Log.d("day", "after last");
                        weekList.get(j).getChildItemList().add(new DayExpense(expenseDate));
                        weekList.get(j).getChildItemList().add(new DayExpense(expense));
                        viewBatchView.getExpenseExpandableAdapter().notifyChildItemInserted(j, childListLastIndex);
                    }
                    else {

                        boolean dayPresent = false;
                        for (int k = 0; k < childListLastIndex + 1; k++) {
                            if (dayExpenseChildren.get(k).isDay){

                                if (expenseDate.equals(dayExpenseChildren.get(k).getDate())){
                                    Log.d("day", "is same!");
                                    weekList.get(j).getChildItemList().add(k+1, new DayExpense(expense));
                                    dayPresent = true;
                                    break;
                                }

                                /*

                                if (expense.getActualDate().before(dayExpenseChildren.get(k).getDate())) {

                                    weekList.get(j).getChildItemList().add(k, expense);
                                    viewBatchView.getExpenseExpandableAdapter().notifyChildItemInserted(j, k);
                                    break;
                                } else if (k == childListLastIndex) {
                                    weekList.get(j).getChildItemList().add(expense);
                                    viewBatchView.getExpenseExpandableAdapter().notifyChildItemInserted(j, k);
                                    break;
                                }

                                */
                            }
                        }
                        if (!dayPresent){
                            Log.d("day", "wasn't present");
                            for (int l = 1; l < childListLastIndex + 1; l++){
                                if (expenseDate.before(dayExpenseChildren.get(l).getDate())){
                                    Log.d("day", "added before" + sdf.format(dayExpenseChildren.get(l).getDate()));
                                    weekList.get(j).getChildItemList().add(l, new DayExpense(expenseDate));
                                    weekList.get(j).getChildItemList().add(l+1, new DayExpense(expense));
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                else if (j + 1 < weekList.size()){
                    if (startDate.before(weekList.get(j+1).getStartDate())){
                        Log.d("Expense is", "before week" + String.valueOf(j+1));
                        Log.d("weeklist start date +1", sdf.format(weekList.get(j).getStartDate()));
                        c.add(Calendar.DATE, 6);
                        Date endDate = c.getTime();

                        List<DayExpense> dayExpenseList = new ArrayList<>();
                        dayExpenseList.add(new DayExpense(expenseDate));
                        dayExpenseList.add(new DayExpense(expense));

                        Week newWeek = new Week("", startDate, endDate, dayExpenseList);

                        weekList.add(j+1 , newWeek);
                        viewBatchView.getExpenseExpandableAdapter().notifyParentItemInserted(j+1);
                        break;
                    }
                }
            }
        }
        updateWeekNumbers();
    }

    private void updateWeekNumbers(){
        for (int i = 0; i < weekList.size(); i++){
            weekList.get(i).setRank(i);
        }
        viewBatchView.getExpenseExpandableAdapter().notifyDataSetChanged();
    }

    @Override
    public List<Week> getWeekList() {
        return weekList;
    }

    @Override
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
        viewBatchView = null;
    }

}
