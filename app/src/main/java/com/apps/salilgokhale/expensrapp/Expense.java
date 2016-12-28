package com.apps.salilgokhale.expensrapp;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by salilgokhale on 25/12/2016.
 */

public class Expense {

    public enum eType {
        Hotel, Train, Taxi, Subsistence, Phone, Dinner
    }

    private String receiptDate;
    private eType expenseType;

    public Expense () {}

    public Expense (String receiptDate, eType expenseType) {
        this.receiptDate = receiptDate;
        this.expenseType = expenseType;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Exclude
    public eType getExpenseTypeAsEnum(){
        return expenseType;
    }

    // these methods are just a Firebase 9.0.0 hack to handle the enum
    public String getExpenseType(){
        if (expenseType == null){
            return null;
        } else {
            return expenseType.name();
        }
    }

    public void setLifecycle(String expenseTypeString){
        if (expenseTypeString == null){
            expenseType = null;
        } else {
            this.expenseType = eType.valueOf(expenseTypeString);
        }
    }
}
