package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.custom.CustomEditText;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 11/28/2015.
 */
public class MobileVerificationActivity extends Activity {

    private CustomEditText otp;
    private CustomTextView number;
    private CustomButton verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        Log.d(Constants.LOG_TAG, Constants.MOBILE_VERIFICATION_ACTIVITY);

        getExtras();
        findViews();
        fetchData();
    }

    public void getExtras(){


    }

    public void findViews(){

        number = (CustomTextView) findViewById(R.id.number_edit_mobile_verification_activity);
        otp = (CustomEditText) findViewById(R.id.otp_edit_mobile_verification_activity);
        verify = (CustomButton) findViewById(R.id.verify_button_mobile_verification_activity);

    }

    public void fetchData(){

        // write the Async task here
        // if true then set the views

    }

    public void setViews(){

        verify.setOnClickListener(listener);

    }

    public void verify(){


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.verify_button_mobile_verification_activity : verify();
                    break;


            }

        }
    };

}
