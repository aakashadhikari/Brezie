package com.ascentbrezie.brezie.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.MoodDetailRecyclerAdapter;
import com.ascentbrezie.brezie.async.FetchMoodDetailAsyncTask;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 21-10-2015.
 */
public class MoodDetailActivity extends AppCompatActivity {

    private RecyclerView moodDetailRecyclerView;
    private RecyclerView.Adapter moodDetailRecyclerAdapter;
    private RecyclerView.LayoutManager moodDetailLayoutManager;

    private Toolbar toolbar;
    private int width,height;
    private String position;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mood_detail);
        Log.d(Constants.LOG_TAG,Constants.MOOD_DETAIL_ACTIVITY);

        getExtras();
        findViews();
        customActionBar();
        settingTheAdapter();
        fetchData();
    }

    public void getExtras(){

        position = getIntent().getStringExtra("mood");
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
        moodDetailLayoutManager = new LinearLayoutManager(MoodDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        moodDetailRecyclerView.setLayoutManager(moodDetailLayoutManager);


    }

    public void fetchData(){

        String url = "";

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
                if(result){

                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                    int width = sharedPreferences.getInt("width",0);
                    int height = sharedPreferences.getInt("height",0);

                    // specify an adapter (see also next example)
                    moodDetailRecyclerAdapter = new MoodDetailRecyclerAdapter(MoodDetailActivity.this,width,height);
                    moodDetailRecyclerView.setAdapter(moodDetailRecyclerAdapter);

                }
            }
        }).execute(url);

    }

    public void sendTransaction(){





    }

    @Override
    protected void onPause() {
        super.onPause();
        sendTransaction();

    }
}
