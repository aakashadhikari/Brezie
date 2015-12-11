package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.FetchUserIdAsyncTask;
import com.ascentbrezie.brezie.gcm.QuickstartPreferences;
import com.ascentbrezie.brezie.gcm.RegistrationIntentService;
import com.ascentbrezie.brezie.utils.Constants;
import com.ascentbrezie.brezie.utils.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class SplashScreenActivity extends Activity{

    private ImageView appName;
    private int width, height;
    protected LocationManager locationManager;
    private String deviceId;
    private double latitude, longitude;
    private String notificationId;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(Constants.LOG_TAG, Constants.SPLASH_SCREEN_ACTIVITY);

        getLatitudeLongitude();
        findViews();
        loadAnimation();
        getScreenResolution();
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

}
