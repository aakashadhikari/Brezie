package com.ascentbrezie.brezie.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.TutorialAdapter;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 12/28/2015.
 */
public class TutorialActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private TutorialAdapter adapter;
    private ImageView firstDot,secondDot,thirdDot,fourthDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        Log.d(Constants.LOG_TAG,Constants.TUTORIAL_ACTIVITY);

        findViews();
        setViews();
        settingTheAdapter();

    }

    public void findViews(){


        viewPager = (ViewPager) findViewById(R.id.view_pager_tutorial_activity);
//        firstDot = (ImageView) findViewById(R.id.first_dot_included);
//        secondDot = (ImageView) findViewById(R.id.second_dot_included);
//        thirdDot = (ImageView) findViewById(R.id.third_dot_included);
//        fourthDot = (ImageView) findViewById(R.id.fourth_dot_included);

    }

    public void setViews(){

//        firstDot.setImageResource(R.drawable.icon_selected_dot);
    }


    public void settingTheAdapter(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        int height = sharedPreferences.getInt("height",0);


        adapter = new TutorialAdapter(getSupportFragmentManager(),height);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){

//            case 0: firstDot.setImageResource(R.drawable.icon_selected_dot);
//             secondDot.setImageResource(R.drawable.icon_unselected_dot);
//             thirdDot.setImageResource(R.drawable.icon_unselected_dot);
//             fourthDot.setImageResource(R.drawable.icon_unselected_dot);
//                break;
//            case 1: firstDot.setImageResource(R.drawable.icon_unselected_dot);
//                secondDot.setImageResource(R.drawable.icon_selected_dot);
//                thirdDot.setImageResource(R.drawable.icon_unselected_dot);
//                fourthDot.setImageResource(R.drawable.icon_unselected_dot);
//                break;
//            case 2: firstDot.setImageResource(R.drawable.icon_unselected_dot);
//                secondDot.setImageResource(R.drawable.icon_unselected_dot);
//                thirdDot.setImageResource(R.drawable.icon_selected_dot);
//                fourthDot.setImageResource(R.drawable.icon_unselected_dot);
//                break;
//            case 3:
//                firstDot.setImageResource(R.drawable.icon_unselected_dot);
//                secondDot.setImageResource(R.drawable.icon_unselected_dot);
//                thirdDot.setImageResource(R.drawable.icon_unselected_dot);
//                fourthDot.setImageResource(R.drawable.icon_selected_dot);
//                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
