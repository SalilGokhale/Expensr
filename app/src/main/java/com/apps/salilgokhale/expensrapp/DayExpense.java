package com.apps.salilgokhale.expensrapp;

import java.util.Date;

/**
 * Created by salilgokhale on 09/01/2017.
 */

public class DayExpense {
    boolean isDay;
    Date date;
    Expense expense;
    Day day;

    public DayExpense(Expense expense){
        this.isDay = false;
        this.date = expense.getActualDate();
        this.expense = expense;
    }

    public DayExpense(Date date){
        this.isDay = true;
        this.date = date;
        this.day = new Day(date);
    }

    public boolean isDay() {
        return isDay;
    }

    public Date getDate() {
        return date;
    }

    public Expense getExpense() {
        return expense;
    }

    public Day getDay() {
        return day;
    }
}
