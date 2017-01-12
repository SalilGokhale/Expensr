package com.apps.salilgokhale.expensrapp;

import android.util.Log;

import java.util.List;

/**
 * Created by salilgokhale on 05/01/2017.
 */

public interface ViewBatchPresenter {

    public List<Week> getWeekList();

    public void retrieveBatchInfo(String batchKey);

    public void retrieveExpensesForBatch(String batchKey);

    public void updateSapBag(int SapBag, boolean isSet, String batchKey);

    public void onDestroy();
}
