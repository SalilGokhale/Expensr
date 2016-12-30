package com.apps.salilgokhale.expensrapp;

import java.util.Date;
import java.util.Map;

/**
 * Created by salilgokhale on 25/12/2016.
 */

public class Batch {

    private String name;
    private String startDate;
    private String endDate;
    private int expenseTotal;
    private boolean sap;
    private boolean bag;
    private String lastAddedTo;
    private Map<String, Object> matchingExpenses;

    public Batch () {}

    public Batch (String name, String startDate, String endDate, int expenseTotal, boolean sap, boolean bag){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenseTotal = expenseTotal;
        this.sap = sap;
        this.bag = bag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBag(boolean bag) {
        this.bag = bag;
    }

    public boolean isBag() {
        return bag;
    }

    public void setSap(boolean sap) {
        this.sap = sap;
    }

    public boolean isSap() {
        return sap;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setExpenseTotal(int expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public int getExpenseTotal() {
        return expenseTotal;
    }

    public Map<String, Object> getMatchingExpenses() {
        return matchingExpenses;
    }

    public void setMatchingExpenses(Map<String, Object> matchingExpenses) {
        this.matchingExpenses = matchingExpenses;
    }

    public String getLastAddedTo() {
        return lastAddedTo;
    }

    public void setLastAddedTo(String lastAddedTo) {
        this.lastAddedTo = lastAddedTo;
    }
}
