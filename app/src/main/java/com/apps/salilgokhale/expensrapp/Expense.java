package com.apps.salilgokhale.expensrapp;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.Map;

/**
 * Created by salilgokhale on 25/12/2016.
 */

public class Expense {

    /*public enum eType {
        Hotel, Train, Taxi, Subsistence, Phone, Dinner
    } */

    @Exclude
    public String key;

    private String receiptDate;
    private int expenseType;
    //private eType expenseType;
    private Map<String, Object> matchingBatch;

    public Expense () {}

    public Expense (String receiptDate, int expenseType, Map<String, Object> matchingBatch) {
        this.receiptDate = receiptDate;
        this.expenseType = expenseType;
        this.matchingBatch = matchingBatch;

    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public int getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(int expenseType) {
        this.expenseType = expenseType;
    }

    public Map<String, Object> getMatchingBatch() {
        return matchingBatch;
    }

    public void setMatchingBatch(Map<String, Object> matchingBatch) {
        this.matchingBatch = matchingBatch;
    }

    /*
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
    */

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public String getKey() {
        return key;
    }
}
