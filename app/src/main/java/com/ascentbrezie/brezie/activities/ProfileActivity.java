package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.custom.CircularImageView;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 24-10-2015.
 */
public class ProfileActivity extends Activity {

    private ImageView coverImage;
    private CircularImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(Constants.LOG_TAG,Constants.PROFILE_ACTIVITY);

        findViews();
        setViews();
        alignProfileImage();

    }

    public void findViews(){

        coverImage = (ImageView) findViewById(R.id.cover_image_profile_activity);
        profileImage = (CircularImageView) findViewById(R.id.profile_image_profile_activity);
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
