package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.FetchUserIdAsyncTask;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class SplashScreenActivity extends Activity implements LocationListener{

    private ImageView appName;
    private  int width,height;
    protected LocationManager locationManager;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(Constants.LOG_TAG, Constants.SPLASH_SCREEN_ACTIVITY);

        getService();
        findViews();
        loadAnimation();
        getScreenResolution();
        getDeviceId();
        sendDeviceId();

    }

    public void getService(){


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


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

        deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d(Constants.LOG_TAG, " The device Id is " + deviceId);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();

    }

    public void sendDeviceId(){

        String url = Constants.splashUrl;
        new FetchUserIdAsyncTask(this, new FetchUserIdAsyncTask.FetchUserIdCallback() {
            @Override
            public void onStart(boolean status) {


            }
            @Override
            public void onResult(boolean result) {

                if(true){

                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId",Constants.userId);
                    editor.putString("referenceCode",Constants.referenceCode);
                    editor.commit();

                    Intent i = new Intent(SplashScreenActivity.this,LandingActivity.class);
                    startActivity(i);
                }

            }
            }).execute(url,deviceId);

    }

    @Override
    public void onLocationChanged(Location location) {
        String latitude, longitude;

        latitude = Double.valueOf(location.getLatitude()).toString();
        longitude = Double.valueOf(location.getLongitude()).toString();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latitude", latitude);
        editor.putString("longitude", longitude);
        editor.commit();

        Log.d(Constants.LOG_TAG, "Latitude" + latitude);
        Log.d(Constants.LOG_TAG, "Longitude" + longitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(Constants.LOG_TAG, "status");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(Constants.LOG_TAG, "disable");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(Constants.LOG_TAG, "disable");

    }
}
