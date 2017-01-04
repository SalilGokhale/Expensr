package com.apps.salilgokhale.expensrapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddExpenseFragment extends Fragment implements ButtonView, AdapterView.OnItemSelectedListener {

    // Buttons

    private Button hotelButton, subsButton, dinnerButton, trainButton, taxiButton, phoneButton;

    // Bind textviews and imageviews

    @BindView(R.id.date_day) TextView dayDateTV;
    @BindView(R.id.date_month) TextView monthDateTV;
    @BindView(R.id.date_number) TextView numberDateTV;
    @BindView(R.id.batch_spinner) Spinner batchSpinner;
    @BindView(R.id.button_view_switcher) ViewSwitcher viewSwitcher;

    private Unbinder unbinder;

    // Date and formatters

    Date date = new Date();
    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE", Locale.ENGLISH);
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd", Locale.ENGLISH);
    SimpleDateFormat sdf3 = new SimpleDateFormat("MMM", Locale.ENGLISH);

    // Presenter

    private ButtonPresenter buttonPresenter;

    // Variables

    private boolean batchesAvailable = true;
    private boolean dataRecieved = false;
    int selectedPos = 0;
    private BatchSpinnerAdapter spinnerAdapter;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Presenter set up
        buttonPresenter = new ButtonPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // set Date TextViews and onClickListeners
        setDateTVs(date);
        dayDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        numberDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        monthDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // set active batch selector
        if (isOnline()){
            buttonPresenter.retrieveActiveBatches();
            dataRecieved = true;
        }
        else {
            final Snackbar snackbar = Snackbar.make(((MainActivity)getActivity()).getCoordinatorLayout(), "Connect to internet to retrieve batches", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Retry", new MyTryInternetListener());
            snackbar.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override public boolean onPreDraw() {
                    snackbar.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                    ((CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams()).setBehavior(null);
                    return true;
                }
            });
            snackbar.show();
        }

        batchSpinner.setOnItemSelectedListener(this);
        spinnerAdapter = new BatchSpinnerAdapter(getContext());
        batchSpinner.setAdapter(spinnerAdapter);

    }

    @OnClick(R.id.hotel_button)
    public void addHotel(Button button){
        Log.d("Hotel button: ", "pressed!");
        buttonPress(0);
    }

    @OnClick(R.id.subs_button)
    public void addSubs(Button button){
        Log.d("Subs button: ", "pressed!");
        buttonPress(5);
    }

    @OnClick(R.id.dinner_button)
    public void addDinner(Button button){
        Log.d("Dinner button: ", "pressed!");
        buttonPress(6);
    }

    @OnClick(R.id.phone_button)
    public void addPhone(Button button){
        Log.d("Phone button: ", "pressed!");
        buttonPress(1);
    }

    @OnClick(R.id.train_button)
    public void addTrain(Button button){
        Log.d("Train button: ", "pressed!");
        buttonPress(2);
    }

    @OnClick(R.id.taxi_button)
    public void addTaxi(Button button){
        Log.d("Taxi button: ", "pressed!");
        buttonPress(3);
    }

    public void buttonPress(int i){
        if (dataRecieved) {
            if (batchesAvailable) {
                buttonPresenter.onButtonClicked(i, spinnerAdapter.getItem(selectedPos).getKey());
            }
            else {
                buttonPresenter.onButtonAddBatch(i);
            }
        }
        else {
            Snackbar snackbar = Snackbar.make(((MainActivity)getActivity()).getCoordinatorLayout(), "Error", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    // Spinner callback methods and adapter getter

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Log.d("onItemSelected", "Entered");
        selectedPos = pos;

    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public BatchSpinnerAdapter getSpinnerAdapter() {
        return spinnerAdapter;
    }

    public ViewSwitcher getViewSwitcher() {
        return viewSwitcher;
    }

    // Data receive getter / setter

    public boolean isDataRecieved() {
        return dataRecieved;
    }

    public void setDataRecieved(boolean dataRecieved) {
        this.dataRecieved = dataRecieved;
    }

    public boolean isBatchesAvailable() {
        return batchesAvailable;
    }

    public void setBatchesAvailable(boolean batchesAvailable) {
        this.batchesAvailable = batchesAvailable;
        if (batchesAvailable){
            getViewSwitcher().setDisplayedChild(0);
        }
        else {
            getViewSwitcher().setDisplayedChild(1);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class MyTryInternetListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (isOnline()) {
                buttonPresenter.retrieveActiveBatches();
            }
            else {
                Log.d("Snackbar pressed: ", "did it go down");
                final Snackbar snackbar = Snackbar.make(((MainActivity)getActivity()).getCoordinatorLayout(), "Connect to internet to retrieve batches", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new MyTryInternetListener());
                snackbar.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override public boolean onPreDraw() {
                        snackbar.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                        ((CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams()).setBehavior(null);
                        return true;
                    }
                });
                snackbar.show();
            }
        }
    }

    // Datepicker and setter methods

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void updateDateTVs(String userinput){
        Log.d("Date String", userinput);
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        originalFormat.setLenient(true);
        try {
            date = originalFormat.parse(userinput);
            Log.d("Parsed Date: ", date.toString());
            setDateTVs(date);
        }
        catch (ParseException e2) {
            e2.printStackTrace();
            Log.d("Date:", "Error");
        }

    }

    public void setDateTVs(Date date1) {
        String dayOfTheWeek = sdf1.format(date1);
        String dateNumber = sdf2.format(date1);
        String monthNumber = sdf3.format(date1);

        dayDateTV.setText(dayOfTheWeek);
        numberDateTV.setText(dateNumber);
        monthDateTV.setText(monthNumber);
    }

    public Date getDate() {
        return date;
    }

    // presenter

    public ButtonPresenter getButtonPresenter() {
        return buttonPresenter;
    }

    // On Destroys

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        buttonPresenter.onDestroy();
    }

}
