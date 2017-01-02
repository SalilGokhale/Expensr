package com.apps.salilgokhale.expensrapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by salilgokhale on 29/12/2016.
 */

public class BatchesAdapter extends RecyclerView.Adapter<BatchesAdapter.ViewHolder> {
    private List<Batch> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mBatchNameTV;
        public TextView mBatchDateTV;
        public TextView mBatchTotalTV;
        public ImageView mSAP;
        public ImageView mBag;

        public ViewHolder(View v) {
            super(v);

            mBatchNameTV = (TextView) v.findViewById(R.id.batch_name_cv);
            mBatchDateTV = (TextView) v.findViewById(R.id.batch_dates_cv);
            mBatchTotalTV = (TextView) v.findViewById(R.id.total_exp_cv);
            mSAP = (ImageView) v.findViewById(R.id.sap_cv);
            mBag = (ImageView) v.findViewById(R.id.bag_cv);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Batch onClick", "pressed");
                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BatchesAdapter(List<Batch> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BatchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batch_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Batch temp = mDataset.get(position);

        if (mDataset.size() != 0) {
            holder.mBatchNameTV.setText(temp.getName());
            if (!temp.getStartDate().isEmpty()) {
                String date2date = temp.getStartDate() + " - " + temp.getEndDate();
                holder.mBatchDateTV.setText(date2date);
            }
            String batchTotal = String.valueOf(temp.getTotal()) + " Total";
            holder.mBatchTotalTV.setText(batchTotal);
            if (temp.isSap()) {
                holder.mSAP.setImageResource(R.drawable.sap_green);
            } else {
                holder.mSAP.setImageResource(R.drawable.sap_grey);
            }
            if (temp.isBag()) {
                holder.mBag.setImageResource(R.drawable.bag_green);
            } else {
                holder.mBag.setImageResource(R.drawable.bag_grey);
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(Batch batch) {
        mDataset.add(batch);
        notifyDataSetChanged();
    }
}

