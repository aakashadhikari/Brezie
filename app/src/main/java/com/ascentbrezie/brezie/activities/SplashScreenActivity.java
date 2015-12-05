package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.FetchUserIdAsyncTask;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class SplashScreenActivity extends Activity{

    private ImageView appName;
    private int width, height;
    protected LocationManager locationManager;
    private String deviceId;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(Constants.LOG_TAG, Constants.SPLASH_SCREEN_ACTIVITY);

        GPSTrackerActivity tracker = new GPSTrackerActivity(this);
    if (!tracker.canGetLocation()) {
        Toast.makeText(getApplicationContext(), ("Unable to find"), Toast.LENGTH_LONG).show();
        Log.d(Constants.LOG_TAG, "Unable to find");
        tracker.showSettingsAlert();
    } else {
        latitude = tracker.getLatitude();
        longitude = tracker.getLongitude();
        Toast.makeText(getApplicationContext(), ("Lat" +tracker.getLatitude() + "Lon" +tracker.getLongitude()), Toast.LENGTH_LONG).show();
        Log.d(Constants.LOG_TAG, "Lat" + tracker.getLatitude() + "Lon" + tracker.getLongitude());
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("Lat", (long) + tracker.getLatitude());
        editor.putLong("Lon", (long) +tracker.getLongitude());
        editor.commit();

    }


        findViews();
        loadAnimation();
        getScreenResolution();
        getDeviceId();
        sendDeviceId();

    }


    public void findViews() {

        appName = (ImageView) findViewById(R.id.app_name_image_splash_screen_activity);


    }

    public void loadAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        appName.startAnimation(animation);

    }

    public void getScreenResolution() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("width", width);
        editor.putInt("height", height);
        editor.commit();

    }

    public void getDeviceId() {

        deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d(Constants.LOG_TAG, " The device Id is " + deviceId);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();

    }

    public void sendDeviceId() {

        String url = Constants.splashUrl;
        new FetchUserIdAsyncTask(this, new FetchUserIdAsyncTask.FetchUserIdCallback() {
            @Override
            public void onStart(boolean status) {


            }

            @Override
            public void onResult(boolean result) {

                if (true) {

                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", Constants.userId);
                    editor.putString("referenceCode", Constants.referenceCode);
                    editor.commit();

                    Log.d(Constants.LOG_TAG, " the user id is " + Constants.userId + " and reference code from splash screen " + Constants.referenceCode);


                    Intent i = new Intent(SplashScreenActivity.this, LandingActivity.class);
                    startActivity(i);
                }

            }
        }).execute(url, deviceId);

    }
}
