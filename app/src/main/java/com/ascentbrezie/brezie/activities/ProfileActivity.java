package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.FetchProfileDetailsAsyncTask;
import com.ascentbrezie.brezie.custom.CircularImageView;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 24-10-2015.
 */
public class ProfileActivity extends Activity {

    private ImageView coverImage;
    private CircularImageView profileImage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(Constants.LOG_TAG, Constants.PROFILE_ACTIVITY);

        findViews();
        fetchData();
        alignProfileImage();

    }

    public void findViews(){

        coverImage = (ImageView) findViewById(R.id.cover_image_profile_activity);
        profileImage = (CircularImageView) findViewById(R.id.profile_image_profile_activity);
    }

    public void fetchData(){


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","null");

        String url = Constants.profileUrl;
        new FetchProfileDetailsAsyncTask(this, new FetchProfileDetailsAsyncTask.FetchProfileDetailsAsyncTaskCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading...Please Wait");
                progressDialog.show();

            }

            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(true){

                    setViews();

                }
                else{

                    Toast.makeText(getApplicationContext(),"Couldn't fetch your profile",5000).show();
                }

            }
        }).execute(url,userId);


    }

    public void setViews(){


    }

    public void alignProfileImage(){

        Log.d(Constants.LOG_TAG," coverImage " +coverImage.getBottom());
        Log.d(Constants.LOG_TAG," coverImage height " +coverImage.getHeight());

        int marginTop = coverImage.getHeight()-75;

        Log.d(Constants.LOG_TAG," margin top is "+marginTop);

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150,150);
//        layoutParams.addRule(Gravity.CENTER);
//        layoutParams.topMargin = marginTop;
//        profileImage.setLayoutParams(layoutParams);


    }

}
