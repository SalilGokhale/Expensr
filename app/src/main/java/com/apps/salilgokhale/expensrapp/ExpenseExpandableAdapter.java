package com.apps.salilgokhale.expensrapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by salilgokhale on 06/01/2017.
 */

public class ExpenseExpandableAdapter extends ExpandableRecyclerAdapter<ExpenseExpandableAdapter.WeekParentViewHolder, ExpenseExpandableAdapter.ExpenseParentViewHolder> {

    private LayoutInflater inflater;

    public ExpenseExpandableAdapter(Context context, List<Week> itemList){
        super(itemList);
        inflater = LayoutInflater.from(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WeekParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_week, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new WeekParentViewHolder(v);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseParentViewHolder onCreateChildViewHolder(ViewGroup parent) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_expense, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ExpenseParentViewHolder(v);

    }

    @Override
    public void onBindParentViewHolder(WeekParentViewHolder weekParentViewHolder, int i, ParentListItem parentListItem){
        Week week = (Week) parentListItem;
        String weekName = "Week " + String.valueOf(week.getRank() + 1);
        weekParentViewHolder.mWeekTextView.setText(weekName);
        //weekParentViewHolder.mWeekDateTextView.setText(week.getWeekDates());
    }

    @Override
    public void onBindChildViewHolder(ExpenseParentViewHolder expenseParentViewHolder, int i, Object childListItem){
        expenseParentViewHolder.mDayDateTextView.setText("");
        expenseParentViewHolder.mExpenseTextView.setText("");
        expenseParentViewHolder.mDayTextView.setText("");
        DayExpense dayExpense = (DayExpense) childListItem;
        if (dayExpense.isDay) {
            expenseParentViewHolder.mDayTextView.setText(dayExpense.getDay().getDay());
            expenseParentViewHolder.mDayDateTextView.setText(dayExpense.getDay().getDateString());
        }
        else {
            expenseParentViewHolder.mExpenseTextView.setText(convertToType(dayExpense.getExpense().getExpenseType()));
        }
    }

    public class WeekParentViewHolder extends ParentViewHolder {
        public TextView mWeekTextView;
        public TextView mWeekDateTextView;

        public WeekParentViewHolder(View itemView) {
            super(itemView);

            mWeekTextView = (TextView) itemView.findViewById(R.id.week_text);
            mWeekDateTextView = (TextView) itemView.findViewById(R.id.week_dates_text);
        }
    }

    public class ExpenseParentViewHolder extends ChildViewHolder {
        private TextView mExpenseTextView;
        private TextView mDayTextView;
        public TextView mDayDateTextView;

        public ExpenseParentViewHolder(View itemView) {
            super(itemView);

            mExpenseTextView = (TextView) itemView.findViewById(R.id.expense_text);
            mDayTextView = (TextView) itemView.findViewById(R.id.day_text);
            mDayDateTextView = (TextView) itemView.findViewById(R.id.day_date_text);
        }

        /*
        public void bind(DayExpense expense){
            mExpenseTextView.setText(String.valueOf(expense.getExpenseType()));
            mDayTextView
        } */
    }

    private String convertToType(int i){
        switch (i){
            case 0:
                return "Hotel";
            case 1:
                return "Phone";
            case 2:
                return "Train";
            case 3:
                return "Taxi";
            case 4:
                return "Subsistence";
            case 5:
                return "Dinner";
            default:
                return "Error";
        }

    }

}
