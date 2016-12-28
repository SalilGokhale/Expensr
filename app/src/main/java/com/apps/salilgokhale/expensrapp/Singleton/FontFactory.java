package com.apps.salilgokhale.expensrapp.Singleton;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by salilgokhale on 27/12/2016.
 */

public class FontFactory {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }

}
