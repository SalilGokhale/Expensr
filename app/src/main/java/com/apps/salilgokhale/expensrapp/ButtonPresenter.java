package com.apps.salilgokhale.expensrapp;

/**
 * Created by salilgokhale on 28/12/2016.
 */

public interface ButtonPresenter {

    void onDestroy();

    void onButtonClicked(int i, String batchID);

    public void onButtonAddBatch(int i);

    void retrieveActiveBatches();
}
