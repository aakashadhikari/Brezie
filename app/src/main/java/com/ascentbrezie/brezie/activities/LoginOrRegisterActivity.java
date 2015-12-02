package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 11/28/2015.
 */
public class LoginOrRegisterActivity extends Activity {

    private CustomButton login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        Log.d(Constants.LOG_TAG,Constants.LOGIN_OR_REGISTER_ACTIVITY);

        getExtras();
        findViews();
        setViews();
    }

    /***
     * This function will be called when when
     * we pass extras with the intents
     * */

    public void getExtras(){



    }

    public void findViews(){

        login = (CustomButton) findViewById(R.id.login_button_login_or_register_activity);
        register = (CustomButton) findViewById(R.id.register_button_login_or_register_activity);

    }

    public void setViews(){

        login.setOnClickListener(listener);
        register.setOnClickListener(listener);

    }

    public void login(){

        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);

    }

    public void register(){

        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.login_button_login_or_register_activity: login();
                    break;
                case R.id.register_button_login_or_register_activity: register();
                    break;


            }

        }
    };
}
