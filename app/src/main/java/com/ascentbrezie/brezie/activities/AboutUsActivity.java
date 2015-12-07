package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 24-10-2015.
 */
public class  AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Log.d(Constants.LOG_TAG,Constants.ABOUT_US_ACTIVITY);

    }
}
