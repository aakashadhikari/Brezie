package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class SplashScreenActivity extends Activity {

    private ImageView appName;
    private  int width,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(Constants.LOG_TAG,Constants.SPLASH_SCREEN_ACTIVITY);

        findViews();
        loadAnimation();
        getScreenResolution();
        getDeviceId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreenActivity.this,LandingActivity.class);
                startActivity(i);
            }
        },3000);
    }

    public void findViews(){

        appName = (ImageView) findViewById(R.id.app_name_image_splash_screen_activity);


    }

    public void loadAnimation(){

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        appName.startAnimation(animation);

    }

    public void getScreenResolution(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("width", width);
        editor.putInt("height", height);
        editor.commit();

    }

    public void getDeviceId(){

        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d(Constants.LOG_TAG," The device Id is "+deviceId);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();

    }

}