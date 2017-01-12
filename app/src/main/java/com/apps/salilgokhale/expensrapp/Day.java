package com.apps.salilgokhale.expensrapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by salilgokhale on 12/01/2017.
 */

public class Day {
    String day;
    String dateString;
    Date actualDate;
    SimpleDateFormat sdf = new SimpleDateFormat("d/M", Locale.ENGLISH);
    SimpleDateFormat dayf = new SimpleDateFormat("EEEE", Locale.ENGLISH);

    public Day (Date date){
        actualDate = date;
        dateString = sdf.format(date);
        day = dayf.format(date);

    }

    public String getDay() {
        return day;
    }

    public String getDateString() {
        return dateString;
    }
}
