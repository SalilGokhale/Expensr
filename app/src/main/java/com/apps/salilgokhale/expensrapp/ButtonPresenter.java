package com.apps.salilgokhale.expensrapp;

/**
 * Created by salilgokhale on 28/12/2016.
 */

public interface ButtonPresenter {

    void onDestroy();

    void onButtonClicked(int i);

    void retrieveActiveBatches();
}
