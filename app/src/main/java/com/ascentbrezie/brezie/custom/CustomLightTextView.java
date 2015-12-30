package com.ascentbrezie.brezie.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class CustomLightTextView extends TextView{

    public CustomLightTextView(Context context) {
        super(context);
        init();
    }

    public CustomLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomLightTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"din_pro_light.ttf");
        setTypeface(tf);

    }

}
