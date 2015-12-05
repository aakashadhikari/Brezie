package com.ascentbrezie.brezie.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.FetchQuotesForDayAsyncTask;
import com.ascentbrezie.brezie.utils.Constants;
import com.daimajia.slider.library.SliderLayout;


public class LandingActivity extends ActionBarActivity {

    private View a,b,c,d,e,f;
    private LinearLayout tabLayout;
    private Toolbar toolbar;
    private SliderLayout sliderLayout;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Log.d(Constants.LOG_TAG,Constants.LANDING_ACTIVITY);

        findViews();
        customActionBar();
        fetchData();

    }



    public void findViews(){

        tabLayout = (LinearLayout) findViewById(R.id.tabs_layout_landing_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar_landing_activity);
        sliderLayout = (SliderLayout)findViewById(R.id.slider_landing_activity);


    }

    public void customActionBar(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon_launcher);

    }

    public void fetchData(){


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","null");

        String url=Constants.landingUrl;

        new FetchQuotesForDayAsyncTask(getApplicationContext(),new FetchQuotesForDayAsyncTask.FetchQuotesForDayCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(LandingActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading...Please Wait");
                progressDialog.show();


            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    populateTabs();
                    setViews();
                    setTheSlider();

                }

            }
        }).execute(url,userId);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void populateTabs(){

        tabLayout.removeAllViews();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        int width = sharedPreferences.getInt("width",0);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, width/6);
        layoutParams.leftMargin = 5;
        layoutParams.weight = 1;

        for(int i=0;i<6;i++){

            Log.d(Constants.LOG_TAG," For loop is called ");
            View view = new View(this);
            view.setTag("mood_" + i);
            view.setOnClickListener(listener);
            view.setBackground(getResources().getDrawable(R.drawable.background_outline));
            tabLayout.addView(view,layoutParams);
        }




    }

    public void setViews(){


    }

    public void setTheSlider(){



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;
        switch (item.getItemId())
        {

//            case R.id.action_profile: i = new Intent(LandingActivity.this,ProfileActivity.class);
//                startActivity(i);
//                break;
//            case R.id.action_settings: i = new Intent(LandingActivity.this,SettingsActivity.class);
//                startActivity(i);
//                break;
            case R.id.action_about_us: i = new Intent(LandingActivity.this,AboutUsActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String tagDetails[] = v.getTag().toString().split("_");
            int position = Integer.parseInt(tagDetails[1])+1;

            Log.d(Constants.LOG_TAG," the mood id is "+position);

            Intent i = new Intent(getApplicationContext(),MoodDetailActivity.class);
            i.putExtra("mood",String.valueOf(position));
            startActivity(i);
        }
    };

//    View.OnClickListener listener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            Log.d(Constants.LOG_TAG," Calling the MoodDetailActivity");
//
////            Intent i = new Intent(LandingActivity.this, DeleteActivity.class);
//            Intent i = new Intent(LandingActivity.this, MoodDetailActivity.class);
//            startActivity(i);
//
////            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(LandingActivity.this, view, "abc");
////            startActivity(i, transitionActivityOptions.toBundle());
//
//
//
//        }
//    };

}
