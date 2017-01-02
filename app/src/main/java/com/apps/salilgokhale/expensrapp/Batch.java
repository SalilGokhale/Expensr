package com.apps.salilgokhale.expensrapp;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.Map;

/**
 * Created by salilgokhale on 25/12/2016.
 */

public class Batch {

    @Exclude
    public String key;

    private String name;
    private String startDate;
    private String endDate;
    private int total;
    private boolean sap;
    private boolean bag;
    private String lastAddedTo;
    private Map<String, Object> matchingExpenses;

    public Batch () {}

    public Batch (String name, String startDate, String endDate, int expenseTotal, boolean sap, boolean bag){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = expenseTotal;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
