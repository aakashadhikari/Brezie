package com.ascentbrezie.brezie.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.MoodDetailFragmentAdapter;
import com.ascentbrezie.brezie.adapters.MoodDetailRecyclerAdapter;
import com.ascentbrezie.brezie.async.FetchMoodDetailAsyncTask;
import com.ascentbrezie.brezie.async.SendTransactionAsyncTask;
import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.MoodDetailData;
import com.ascentbrezie.brezie.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by SAGAR on 12/28/2015.
 */
public class MoodDetailActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private MoodDetailFragmentAdapter adapter;


    private String moodId,userId,latitude,longitude,screenCategory;
    private ProgressDialog progressDialog;

    private String quoteId,comment;

    private Parcelable recyclerState;
    private SharedPreferences sharedPreferences;
    private String route;
    private boolean cycleComplete;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mood_detail);

        Log.d(Constants.LOG_TAG, Constants.MOOD_DETAIL_ACTIVITY);

        getExtras();
        initializeJsons();
        findViews();
        customActionBar();
        fetchData();


    }

    public void getExtras(){

        /**
         * Whenever a person comments on any of the quotes we first check if he is valid user or not
         * by asking him for login credentials or asking him to register
         * Once the registration is done we re direct him to this page again
         *
         * The if condition checks if the we are coming to this screen
         * via the comment cycle or we are coming to this screen via
         * the landing screen
         * **/
        sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        cycleComplete = sharedPreferences.getBoolean("isCommentCycleComplete", false);
        if(cycleComplete){

            moodId = sharedPreferences.getString("moodId","null");
        }
        else{

            moodId = getIntent().getStringExtra("moodId");
        }

    }

    public void initializeJsons(){

        Constants.transactionGrandParentJsonArray = new JSONArray();
        Constants.transactionGrandParentJsonObject = new JSONObject();
        Constants.transactionParentJsonArray = new JSONArray();
        Constants.transactionParentJsonObject = new JSONObject();
        Constants.transactionChildJsonArray = new JSONArray();
        Constants.transactionChildJsonObject = new JSONObject();

    }


    public void findViews(){

        viewPager = (ViewPager) findViewById(R.id.view_pager_temp_mood_detail_activity);


    }

    public void customActionBar(){

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public void fetchData(){


        sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "null");
        latitude = sharedPreferences.getString("latitude","null");
        longitude = sharedPreferences.getString("longitude", "null");
        screenCategory = sharedPreferences.getString("screenCategory", "null");


        String url = Constants.moodDetailUrl;

        new FetchMoodDetailAsyncTask(getApplicationContext(),new FetchMoodDetailAsyncTask.FetchMoodDetailCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(MoodDetailActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading...Please Wait");
                progressDialog.show();

            }

            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result) {
                    sharedPreferences = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
                    int width = sharedPreferences.getInt("width", 0);
                    int height = sharedPreferences.getInt("height", 0);

                    if(cycleComplete){

                        boolean isViaCommentRoute = checkRoute();
                        if(isViaCommentRoute){

//                        Constants.moodDetailData.get
                            sharedPreferences = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
                            String quoteId = sharedPreferences.getString("quoteId", "null");
                            String nickname = sharedPreferences.getString("nickname", null);
                            String comment = sharedPreferences.getString("comment", null);

                            getQuoteObject(quoteId).getCommentsData().add(new CommentsData(comment,nickname));

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("route");
                            editor.remove("comment");
                            editor.remove("isCommentCycleComplete");
                            editor.remove("moodId");
                            editor.commit();

                            getLastSeenQuote();
                            settingTheAdapter();

                        }
                        else{

                            getLastSeenQuote();
                            settingTheAdapter();

                        }

                    }
                    else{

                        getLastSeenQuote();
                        settingTheAdapter();
                    }

                }
            }
        }).execute(url, userId, moodId, latitude, longitude,screenCategory);



    }

    public boolean checkRoute(){

        sharedPreferences = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        route = sharedPreferences.getString("route","null");
        if(route.equalsIgnoreCase("comment")){
            return true;
        }
        return false;

    }


    public MoodDetailData getQuoteObject(String quoteId){

        int b = Integer.parseInt(quoteId);

        for (int i =0; i< Constants.moodDetailData.size();i++){

            int a = Integer.parseInt(Constants.moodDetailData.get(i).getQuoteId());
            if(a == b){

                return Constants.moodDetailData.get(i);

            }

        }

        return null;
    }


    public void getLastSeenQuote(){

        /**
         * wasInMood variable is used to store the value of the
         * quote that was last seen by the user
         * **/

        boolean wasInMood = sharedPreferences.contains("mood_" + moodId);
        if(wasInMood){

            String quoteId = String.valueOf(sharedPreferences.getInt("mood_"+moodId,0));
            for(int i =0;i<Constants.moodDetailData.size();i++){

                if(Constants.moodDetailData.get(i).getQuoteId().equalsIgnoreCase(quoteId)){

                    offset = i;

                }

            }

            Log.d(Constants.LOG_TAG," the offset is "+offset);
        }
        else{

            offset = 0;
        }



    }

    public void settingTheAdapter(){


        adapter = new MoodDetailFragmentAdapter(getSupportFragmentManager(),moodId);
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(offset);

    }

    public void sendTransaction(){


        new SendTransactionAsyncTask(this, new SendTransactionAsyncTask.SendTransactionCallback() {
            @Override
            public void onStart(boolean status) {



            }

            @Override
            public void onResult(boolean result) {

            }
        }).execute(Constants.transactionGrandParentJsonObject);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sendTransaction();
    }

    public void saveLastSeen(int position){

        int quoteId = Integer.parseInt(Constants.moodDetailData.get(position).getQuoteId());

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("mood_" + moodId, quoteId);
        editor.commit();



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        saveLastSeen(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
