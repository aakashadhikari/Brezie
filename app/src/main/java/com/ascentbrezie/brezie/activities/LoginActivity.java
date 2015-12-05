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
import com.ascentbrezie.brezie.async.LoginAsyncTask;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.custom.CustomEditText;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 11/28/2015.
 */
public class LoginActivity extends Activity {

    CustomEditText number,password;
    CustomButton login;

    ProgressDialog progressDialog;

    private String numberValue,passwordValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(Constants.LOG_TAG, Constants.LOGIN_ACTIVITY);

        getExtras();
        findViews();
        setViews();

    }

    public void getExtras(){



    }

    public void findViews(){

        number = (CustomEditText) findViewById(R.id.number_edit_login_activity);
        password = (CustomEditText) findViewById(R.id.password_edit_login_activity);
        login = (CustomButton) findViewById(R.id.login_button_login_activity);


    }

    public void setViews(){

        login.setOnClickListener(listener);

    }

    public void login(){


        numberValue = number.getText().toString();
        passwordValue = password.getText().toString();

        String url= Constants.loginUrl;

        new LoginAsyncTask(this, new LoginAsyncTask.LoginCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(LoginActivity.this);
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
                    editor.putString("nickName",Constants.nickName);

                    Intent i = new Intent(LoginActivity.this,MoodDetailActivity.class);
                    startActivity(i);


                }
                else{

                    Toast.makeText(getApplicationContext(),"Username and Password incorrect",5000).show();
                }

            }
        }).execute(url,numberValue,passwordValue);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.login_button_login_activity: login();
                break;

            }
        }
    };
}
