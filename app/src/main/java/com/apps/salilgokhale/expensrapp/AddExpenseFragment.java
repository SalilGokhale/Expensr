package com.apps.salilgokhale.expensrapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddExpenseFragment extends Fragment implements ButtonView {

    // Buttons

    private Button hotelButton, subsButton, dinnerButton, trainButton, taxiButton, phoneButton;

    // Top date textviews

    @BindView(R.id.date_day) TextView dayDateTV;
    @BindView(R.id.date_month) TextView monthDateTV;
    @BindView(R.id.date_number) TextView numberDateTV;
    private Unbinder unbinder;

    // Date and formatters

    Date date = new Date();
    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE", Locale.ENGLISH);
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd", Locale.ENGLISH);
    SimpleDateFormat sdf3 = new SimpleDateFormat("MMM", Locale.ENGLISH);

    // Presenter

    private ButtonPresenter buttonPresenter;


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

    }

    @OnClick(R.id.hotel_button)
    public void addHotel(Button button){
        Log.d("Hotel button: ", "pressed!");
        buttonPresenter.onButtonClicked(0);
    }

    @OnClick(R.id.subs_button)
    public void addSubs(Button button){
        Log.d("Subs button: ", "pressed!");
        buttonPresenter.onButtonClicked(1);
    }

    @OnClick(R.id.dinner_button)
    public void addDinner(Button button){
        Log.d("Dinner button: ", "pressed!");
        buttonPresenter.onButtonClicked(2);
    }

    @OnClick(R.id.phone_button)
    public void addPhone(Button button){
        Log.d("Phone button: ", "pressed!");
        buttonPresenter.onButtonClicked(3);
    }

    @OnClick(R.id.train_button)
    public void addTrain(Button button){
        Log.d("Train button: ", "pressed!");
        buttonPresenter.onButtonClicked(4);
    }

    @OnClick(R.id.taxi_button)
    public void addTaxi(Button button){
        Log.d("Taxi button: ", "pressed!");
        buttonPresenter.onButtonClicked(5);
    }

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
