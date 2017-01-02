package com.apps.salilgokhale.expensrapp;

/**
 * Created by salilgokhale on 30/12/2016.
 */

public interface MainActivityPresenter {

    void onDestroy();

    void createBatch(String batchName, boolean newB, int i);
}
