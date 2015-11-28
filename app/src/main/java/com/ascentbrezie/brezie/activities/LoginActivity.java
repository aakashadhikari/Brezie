package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.custom.CustomEditText;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 11/28/2015.
 */
public class LoginActivity extends Activity {

    CustomEditText number,password;
    CustomButton login;

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
