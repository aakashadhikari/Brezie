package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.SplashScreenTutorialAdapter;
import com.ascentbrezie.brezie.async.FetchUserIdAsyncTask;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.gcm.QuickstartPreferences;
import com.ascentbrezie.brezie.gcm.RegistrationIntentService;
import com.ascentbrezie.brezie.utils.Constants;
import com.ascentbrezie.brezie.utils.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.File;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class SplashScreenActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ImageView appName;
    private int width, height;
    protected LocationManager locationManager;
    private String deviceId;
    private double latitude, longitude;
    private String notificationId;
    private ViewPager viewPager;

    private CustomTextView skipOrContinue;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;

    private RelativeLayout.LayoutParams layoutParams,layoutParams1,layoutParams2,layoutParams3;

    private SplashScreenTutorialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(Constants.LOG_TAG, Constants.SPLASH_SCREEN_ACTIVITY);

        getLatitudeLongitude();
        findViews();
        getScreenResolution();
        getScreenCategory();
        settingTheLayouts();
        isFirstTimeUser();
        loadAnimation();
        getDeviceId();
        registerForGCMService();


    }

    public void getLatitudeLongitude(){

        GPSTracker tracker = new GPSTracker(this);
        if (!tracker.canGetLocation()) {
            Toast.makeText(getApplicationContext(), ("Unable to get your location"), Toast.LENGTH_LONG).show();
            Log.d(Constants.LOG_TAG, "Unable to get location");
            tracker.showSettingsAlert();
        } else {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            Log.d(Constants.LOG_TAG, "Lat " + tracker.getLatitude() + " Lon " + tracker.getLongitude());

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude", String.valueOf(tracker.getLatitude()));
            editor.putString("longitude", String.valueOf(tracker.getLongitude()));
            editor.commit();

        }


    }


    public void findViews() {

        viewPager = (ViewPager) findViewById(R.id.view_pager_splash_screen_activity);
        appName = (ImageView) findViewById(R.id.app_name_image_splash_screen_activity);
        skipOrContinue = (CustomTextView) findViewById(R.id.skip_or_continue_text_splash_screen_activity);


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

    public void getScreenCategory(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW: editor.putString("screenCategory", "1");
                editor.commit();
                break;
            case DisplayMetrics.DENSITY_MEDIUM: editor.putString("screenCategory", "1");
                editor.commit();
                break;
            case DisplayMetrics.DENSITY_HIGH: editor.putString("screenCategory", "1");
                editor.commit();
                break;
            case DisplayMetrics.DENSITY_XHIGH: editor.putString("screenCategory", "2");
                editor.commit();
                break;
            case DisplayMetrics.DENSITY_XXHIGH: editor.putString("screenCategory", "3");
                editor.commit();
                break;
            case DisplayMetrics.DENSITY_XXXHIGH: editor.putString("screenCategory", "4");
                editor.commit();
                break;
        }

    }

    public void settingTheLayouts(){

        SharedPreferences sharedPreference = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        int height = sharedPreference.getInt("height", 0);

        int viewPagerHeight = (height *60)/100;

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,viewPagerHeight);
        layoutParams.setMargins(5,5,5,5);
        viewPager.setLayoutParams(layoutParams);


        layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams1.addRule(RelativeLayout.BELOW, R.id.view_pager_splash_screen_activity);
        skipOrContinue.setLayoutParams(layoutParams1);

        layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams2.addRule(RelativeLayout.BELOW, R.id.skip_or_continue_text_splash_screen_activity);
        appName.setLayoutParams(layoutParams2);

        layoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);
        appName.setLayoutParams(layoutParams3);


    }

    public void isFirstTimeUser(){

        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        else
            cacheDir = getCacheDir();
        if(cacheDir.exists()){

            viewPager.setVisibility(View.GONE);
            skipOrContinue.setVisibility(View.GONE);
            appName.setLayoutParams(layoutParams3);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(getApplicationContext(), LandingActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
            },3000);

        }
        else{
            settingTheAdapter();
            setViews();
        }


    }



    public void settingTheAdapter(){

        adapter = new SplashScreenTutorialAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

    public void setViews(){

        skipOrContinue.setText("Skip");
        skipOrContinue.setOnClickListener(listener);
    }

    public void loadAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        appName.startAnimation(animation);

    }


    public void getDeviceId() {

        deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();

    }

    public void registerForGCMService(){


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(Constants.LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(getApplicationContext(), LandingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if(position == 3){

            new Handler().post(new Runnable() {
                @Override
                public void run() {

                    skipOrContinue.setText("Continue");
                }
            });

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
