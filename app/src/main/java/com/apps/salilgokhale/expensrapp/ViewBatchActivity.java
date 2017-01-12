package com.apps.salilgokhale.expensrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.salilgokhale.expensrapp.CustomTextView.BatchTitleTextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewBatchActivity extends AppCompatActivity implements ViewBatchView, ExpenseExpandableAdapter.ExpandCollapseListener {

    private ViewBatchPresenter viewBatchPresenter;
    private ExpenseExpandableAdapter expenseExpandableAdapter;
    private Batch batch;
    private Boolean listExpanded;

    @BindView(R.id.toolbar_view_batch) Toolbar toolbar;
    @BindView(R.id.expense_recycler) RecyclerView recyclerView;
    @BindView(R.id.batch_title) BatchTitleTextView batchTitleTextView;
    @BindView(R.id.batch_dates_title) TextView batchDatesTextView;
    @BindView(R.id.total_exp_title) TextView expTotalTextView;
    @BindView(R.id.bag_title) ImageView bagView;
    @BindView(R.id.sap_title) ImageView sapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batch);

        // Butterknife
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        // Presenter set up

        viewBatchPresenter = new ViewBatchPresenterImpl(this);

        // Batch info set up

        Intent intent = getIntent();
        String batchKey = intent.getStringExtra("batchKey");

        viewBatchPresenter.retrieveBatchInfo(batchKey);

        sapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (batch != null){
                    viewBatchPresenter.updateSapBag(0, batch.isSap(), batch.getKey());
                }
            }
        });

        bagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (batch != null) {
                    viewBatchPresenter.updateSapBag(1, batch.isBag(), batch.getKey());
                }
            }
        });

        // RecyclerView set up

        expenseExpandableAdapter = new ExpenseExpandableAdapter(this, viewBatchPresenter.getWeekList());
        expenseExpandableAdapter.onRestoreInstanceState(savedInstanceState);
        expenseExpandableAdapter.setExpandCollapseListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(expenseExpandableAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this));

        viewBatchPresenter.retrieveExpensesForBatch(batchKey);

        // Fabs set up

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_expand_collapse);
        fab2.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
        listExpanded = false;
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (listExpanded){
                expenseExpandableAdapter.collapseAllParents();
                fab2.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                listExpanded = false;
            }
            else{
                expenseExpandableAdapter.expandAllParents();
                fab2.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                listExpanded = true;
            }
            }
        });
    }

    @Override
    public ExpenseExpandableAdapter getExpenseExpandableAdapter() {
        return expenseExpandableAdapter;
    }

    @Override
    public void onListItemExpanded(int parentPosition){

    }

    @Override
    public void onListItemCollapsed(int parentPosition){

    }

    @Override
    public void setBatchItem(Batch batchItem){
        this.batch = batchItem;

        batchTitleTextView.setText(batch.getName());
        if (!batch.getStartDate().isEmpty()) {
            String date2date = batch.getStartDate() + " - " + batch.getEndDate();
            batchDatesTextView.setText(date2date);
        }
        String batchTotal = String.valueOf(batch.getTotal()) + " Total";
        expTotalTextView.setText(batchTotal);
        if (batch.isSap()) {
            sapView.setImageResource(R.drawable.sap_green);
        } else {
            sapView.setImageResource(R.drawable.sap_grey);
        }
        if (batch.isBag()) {
            bagView.setImageResource(R.drawable.bag_green);
        } else {
            bagView.setImageResource(R.drawable.bag_grey);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        viewBatchPresenter.onDestroy();
        super.onDestroy();
    }

}
