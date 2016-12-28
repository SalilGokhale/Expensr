package com.apps.salilgokhale.expensrapp;

import java.util.Date;

/**
 * Created by salilgokhale on 25/12/2016.
 */

public class Batch {

    private String name;
    private Date startDate;
    private Date endDate;
    private int expenseTotal;
    private boolean sap;
    private boolean bag;

    public Batch () {}

    public Batch (String name, Date startDate, Date endDate, int expenseTotal, boolean sap, boolean bag){
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setExpenseTotal(int expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public int getExpenseTotal() {
        return expenseTotal;
    }

}
