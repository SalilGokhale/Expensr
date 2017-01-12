package com.apps.salilgokhale.expensrapp.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.apps.salilgokhale.expensrapp.R;
import com.apps.salilgokhale.expensrapp.Singleton.FontFactory;

/**
 * Created by salilgokhale on 10/01/2017.
 */

public class BatchTitleTextView extends TextView {

    public BatchTitleTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public BatchTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public BatchTitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontFactory.getTypeface("fonts/Helvetica-Bold.ttf", context);
        setTypeface(customFont);
        this.setTextSize(getResources().getDimension(R.dimen.batch_title_size));
        this.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

}
