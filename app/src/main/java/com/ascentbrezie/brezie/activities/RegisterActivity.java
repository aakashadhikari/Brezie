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
public class RegisterActivity extends Activity {

    private CustomEditText number,nickName,password,confirmPassword;
    private CustomButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(Constants.LOG_TAG,Constants.REGISTER_ACTIVITY);

        getExtras();
        findViews();
        setViews();


    }

    public void getExtras(){


    }

    public void findViews(){

        number = (CustomEditText) findViewById(R.id.number_edit_register_activity);
        nickName = (CustomEditText) findViewById(R.id.nick_name_edit_register_activity);
        password = (CustomEditText) findViewById(R.id.password_edit_register_activity);
        confirmPassword = (CustomEditText) findViewById(R.id.confirm_password_edit_register_activity);
        register = (CustomButton) findViewById(R.id.register_button_register_activity);


    }

    public void setViews(){

        register.setOnClickListener(listener);

    }

    public void register(){


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.register_button_register_activity: register();
                    break;
            }


        }
    };

}
