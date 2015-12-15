package com.ascentbrezie.brezie.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.activities.SplashScreenActivity;
import com.ascentbrezie.brezie.async.FetchQuoteOfTheDayAsyncTask;
import com.ascentbrezie.brezie.utils.Constants;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by SAGAR on 12/9/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(final String message) {

        String url = Constants.quoteOfDayUrl;

        new FetchQuoteOfTheDayAsyncTask(getApplicationContext(), new FetchQuoteOfTheDayAsyncTask.FetchQuoteofTheDayCallback() {
            @Override
            public void onStart(boolean status) {

            }
            @Override
            public void onResult(boolean result) {

                if(result){

                    Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                            PendingIntent.FLAG_ONE_SHOT);

                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.icon_arrow_down)
                            .setContentTitle("Brezie")
                            .setContentText(Constants.quoteOfTheDay)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

                }

            }
        }).execute(url);


    }
}