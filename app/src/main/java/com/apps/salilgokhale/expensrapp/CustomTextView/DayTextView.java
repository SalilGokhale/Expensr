package com.apps.salilgokhale.expensrapp.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.apps.salilgokhale.expensrapp.R;
import com.apps.salilgokhale.expensrapp.Singleton.FontFactory;

/**
 * Created by salilgokhale on 12/01/2017.
 */

public class DayTextView extends TextView {

    public DayTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public DayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public DayTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontFactory.getTypeface("fonts/Helvetica-Bold.ttf", context);
        setTypeface(customFont);
        this.setTextSize(getResources().getDimension(R.dimen.day_text_height));
        this.setAllCaps(true);
        this.setTextColor(ContextCompat.getColor(context, R.color.black));
    }
}
