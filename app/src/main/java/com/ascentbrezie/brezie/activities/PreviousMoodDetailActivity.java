package com.ascentbrezie.brezie.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.MoodDetailRecyclerAdapter;
import com.ascentbrezie.brezie.async.FetchMoodDetailAsyncTask;
import com.ascentbrezie.brezie.async.SendTransactionAsyncTask;
import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.MoodDetailData;
import com.ascentbrezie.brezie.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ADMIN on 21-10-2015.
 */
public class PreviousMoodDetailActivity extends AppCompatActivity {

    private RecyclerView moodDetailRecyclerView;
    private RecyclerView.Adapter moodDetailRecyclerAdapter;
    private RecyclerView.LayoutManager moodDetailLayoutManager;

    private String moodId,userId,latitude,longitude;
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
        setContentView(R.layout.previous_activity_mood_detail);
        Log.d(Constants.LOG_TAG,Constants.MOOD_DETAIL_ACTIVITY);

        getExtras();
        initializeJsons();
        findViews();
        customActionBar();
        settingTheAdapter();
        fetchData();
    }

    public void getExtras(){

        sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        cycleComplete = sharedPreferences.getBoolean("isCommentCycleComplete", false);
        if(cycleComplete){

            moodId = sharedPreferences.getString("moodId","null");
        }
        else{

            moodId = getIntent().getStringExtra("mood");
        }

        boolean wasInMood = sharedPreferences.contains("mood_"+moodId);
        Log.d(Constants.LOG_TAG," was in mood "+ wasInMood);
        if(wasInMood){

            offset = sharedPreferences.getInt("mood_"+moodId,0);
            Log.d(Constants.LOG_TAG," offset is "+offset);
        }
        else{

            offset = 0;
            Log.d(Constants.LOG_TAG," offset is from else "+offset);
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

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        moodDetailRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_mood_detail_activity);
    }

    public void customActionBar(){

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    public void settingTheAdapter(){

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        moodDetailRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        moodDetailLayoutManager = new LinearLayoutManager(PreviousMoodDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        moodDetailLayoutManager.scrollToPosition(offset);
        moodDetailRecyclerView.setLayoutManager(moodDetailLayoutManager);


    }

    public void fetchData(){


        sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","null");
        latitude = sharedPreferences.getString("latitude","null");
        longitude = sharedPreferences.getString("longitude", "null");

        String url = Constants.moodDetailUrl;

        new FetchMoodDetailAsyncTask(getApplicationContext(),new FetchMoodDetailAsyncTask.FetchMoodDetailCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(PreviousMoodDetailActivity.this);
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

                            // specify an adapter (see also next example)
                            moodDetailRecyclerAdapter = new MoodDetailRecyclerAdapter(PreviousMoodDetailActivity.this, quoteId, comment, width, height, Constants.moodDetailData);
                            moodDetailRecyclerView.setAdapter(moodDetailRecyclerAdapter);

                        }
                        else{


                            // specify an adapter (see also next example)
                            moodDetailRecyclerAdapter = new MoodDetailRecyclerAdapter(PreviousMoodDetailActivity.this,moodId,width,height,Constants.moodDetailData);
                            moodDetailRecyclerView.setAdapter(moodDetailRecyclerAdapter);
                        }

                    }
                    else{

                        // specify an adapter (see also next example)
                        moodDetailRecyclerAdapter = new MoodDetailRecyclerAdapter(PreviousMoodDetailActivity.this,moodId,width,height,Constants.moodDetailData);
                        moodDetailRecyclerView.setAdapter(moodDetailRecyclerAdapter);
                    }

                }
            }
        }).execute(url, userId, moodId, latitude, longitude);

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

            Log.d(Constants.LOG_TAG, " The quote Id is " + Constants.moodDetailData.get(i).getQuoteId() + " the check Id is " + quoteId);
            int a = Integer.parseInt(Constants.moodDetailData.get(i).getQuoteId());
            if(a == b){

                return Constants.moodDetailData.get(i);

            }

        }

        return null;
    }

    public void saveQuoteState(){


        String tempKey = "mood_"+moodId;

        Log.d(Constants.LOG_TAG," temp key is "+tempKey);
        Log.d(Constants.LOG_TAG," focussed on is "+MoodDetailRecyclerAdapter.focussedOn);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(tempKey, MoodDetailRecyclerAdapter.focussedOn);
        editor.commit();

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        recyclerState = moodDetailLayoutManager.onSaveInstanceState();
        outState.putParcelable("myState", recyclerState);

    }

    @Override
    protected void onPause() {
        super.onPause();

        saveQuoteState();
        sendTransaction();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(recyclerState != null){

            moodDetailLayoutManager.onRestoreInstanceState(recyclerState);
        }

    }

}
