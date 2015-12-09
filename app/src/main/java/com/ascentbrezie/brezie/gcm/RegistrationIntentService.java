package com.ascentbrezie.brezie.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.ascentbrezie.brezie.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by SAGAR on 12/9/2015.
 */
public class RegistrationIntentService extends IntentService {

    @Override
    protected void onHandleIntent(Intent intent) {

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

    }
}
