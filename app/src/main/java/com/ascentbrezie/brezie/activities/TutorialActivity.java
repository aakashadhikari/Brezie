package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.SplashScreenTutorialAdapter;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 12/28/2015.
 */
public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SplashScreenTutorialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        Log.d(Constants.LOG_TAG,Constants.TUTORIAL_ACTIVITY);

        findViews();
        settingTheAdapter();

    }

    public void findViews(){


        viewPager = (ViewPager) findViewById(R.id.view_pager_tutorial_activity);

    }

    public void settingTheAdapter(){

        adapter = new SplashScreenTutorialAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

}
