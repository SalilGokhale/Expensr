package com.apps.salilgokhale.expensrapp;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by salilgokhale on 09/01/2017.
 */

public class Week implements ParentListItem {

    /* Create an instance variable for your list of children */
    private String weekName;
    private String weekDates;
    private List<DayExpense> mChildItemList;
    private Date startDate;
    private Date endDate;
    private int rank;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    /**
     * Your constructor and any other accessor
     *  methods should go here.
     */

    public Week(String weekName, Date startDate, Date endDate, List<DayExpense> dayExpenses){
        this.weekName = weekName;
        this.weekDates = sdf.format(startDate) + " - " + sdf.format(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.mChildItemList = dayExpenses;
    }

    public String getWeekName() {
        return weekName;
    }

    @Override
    public List<DayExpense> getChildItemList() {
        return mChildItemList;
    }


    public void setChildItemList(List<DayExpense> list) {
        mChildItemList = list;
    }

    @Override
    public boolean isInitiallyExpanded(){
        return false;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getWeekDates() {
        return weekDates;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
