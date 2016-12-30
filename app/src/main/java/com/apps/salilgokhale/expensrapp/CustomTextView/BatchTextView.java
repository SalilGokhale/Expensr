package com.apps.salilgokhale.expensrapp.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.apps.salilgokhale.expensrapp.R;
import com.apps.salilgokhale.expensrapp.Singleton.FontFactory;

/**
 * Created by salilgokhale on 30/12/2016.
 */

public class BatchTextView extends TextView {

    public BatchTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public BatchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public BatchTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontFactory.getTypeface("fonts/Helvetica-Light.ttf", context);
        setTypeface(customFont);
        this.setTextSize(getResources().getDimension(R.dimen.drawer_text_height));
    }

}

