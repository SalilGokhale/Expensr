package com.apps.salilgokhale.expensrapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salilgokhale on 30/12/2016.
 */

public class BatchSpinnerAdapter extends BaseAdapter {
    Context context;
    List<Batch> batchList;
    LayoutInflater inflter;
    Boolean newBatch;

    public BatchSpinnerAdapter(){}

    public BatchSpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        this.batchList = new ArrayList<>();
        inflter = (LayoutInflater.from(applicationContext));
        newBatch = false;
    }

    @Override
    public int getCount() {
        return batchList.size();
    }

    @Override
    public Batch getItem(int i) {
        return batchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addItem(Batch batch){
        batchList.add(batch);
        Log.d("batch item added", "true");
        notifyDataSetChanged();
    }

    public void updateItem(int i, Batch batch){
        batchList.set(i, batch);
        notifyDataSetChanged();
    }

    public void removeItem(int i){
        batchList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.batch_spinner_item, null);

        TextView batchName = (TextView) view.findViewById(R.id.batch_name_spinner);
        TextView batchDates = (TextView) view.findViewById(R.id.batch_dates_spinner);
        batchName.setText(batchList.get(i).getName());
        String dates = batchList.get(i).getStartDate() + " - " + batchList.get(i).getEndDate();
        batchDates.setText(dates);

        return view;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }
}