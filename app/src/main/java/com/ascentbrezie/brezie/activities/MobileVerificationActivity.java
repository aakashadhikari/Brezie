package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.MobileVerificationAsyncTask;
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

    private ProgressDialog progressDialog;
    private String numberValue,passwordValue,nickNameValue,otpValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        Log.d(Constants.LOG_TAG, Constants.MOBILE_VERIFICATION_ACTIVITY);

        getExtras();
        findViews();
        setViews();
    }

    public void getExtras(){

        Intent i = getIntent();
        numberValue = i.getStringExtra("number");
        passwordValue = i.getStringExtra("password");
        nickNameValue = i.getStringExtra("nickname");
        otpValue = i.getStringExtra("otp");


    }

    public void findViews(){

        number = (CustomTextView) findViewById(R.id.number_edit_mobile_verification_activity);
        otp = (CustomEditText) findViewById(R.id.otp_edit_mobile_verification_activity);
        verify = (CustomButton) findViewById(R.id.verify_button_mobile_verification_activity);

    }

    public void setViews(){

        number.setText(numberValue);
        otp.setText(otpValue);

        verify.setOnClickListener(listener);

    }

    public void verify(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "null");
        String otpValue = otp.getText().toString();

        String url = Constants.verifyUrl;
        new MobileVerificationAsyncTask(this, new MobileVerificationAsyncTask.MobileVerificationCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(MobileVerificationActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading...Please Wait");
                progressDialog.show();

            }

            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nickname",Constants.nickname);
                    editor.commit();

                    String route = sharedPreferences.getString("route","null");

                    if(route.equalsIgnoreCase("profile")){

                        Intent i = new Intent(MobileVerificationActivity.this,ProfileActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                    else if (route.equalsIgnoreCase("comment")){

                        Intent i = new Intent(MobileVerificationActivity.this,MoodDetailActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }

                }
                else{

                    Toast.makeText(MobileVerificationActivity.this," Mobile number not verified", Toast.LENGTH_SHORT).show();
                }

            }
        }).execute(url,userId,numberValue,passwordValue,nickNameValue,otpValue);


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
