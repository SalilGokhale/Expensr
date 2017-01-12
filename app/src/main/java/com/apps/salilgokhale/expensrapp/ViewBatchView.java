package com.apps.salilgokhale.expensrapp;

/**
 * Created by salilgokhale on 05/01/2017.
 */

public interface ViewBatchView {

    public ExpenseExpandableAdapter getExpenseExpandableAdapter();

    public void setBatchItem(Batch batchItem);
}
