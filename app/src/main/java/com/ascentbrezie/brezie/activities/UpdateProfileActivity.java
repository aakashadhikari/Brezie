package com.ascentbrezie.brezie.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.async.UpdateProfileAsyncTask;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.custom.CustomEditText;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.utils.Constants;

import java.util.Calendar;

public class UpdateProfileActivity extends AppCompatActivity {
    private ImageView selectDOB;
    private Calendar calendar;
    private int year, month, day;
    private CustomButton submit;
    private ProgressDialog progressDialog;
    private CustomEditText firstName, lastName, phoneNumber, email;
    private CustomTextView dob;
    private RadioGroup sex;
    private String firstNameValue, lastNameValue, phoneNumberValue, emailValue, dobValue, genderValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Log.d(Constants.LOG_TAG, Constants.UPDATE_PROFILE_ACTIVITY);

        findViews();
        setViews();
        setDOB();
    }

    private void findViews() {

        firstName = (CustomEditText) findViewById(R.id.first_name_edit_update_profile_activity);
        lastName = (CustomEditText) findViewById(R.id.last_name_edit_update_profile_activity);
        phoneNumber = (CustomEditText) findViewById(R.id.phone_edit_edit_update_profile_activity);
        email = (CustomEditText) findViewById(R.id.email_edit_update_profile_activity);
        dob = (CustomTextView) findViewById(R.id.dob_text_update_profile_activity);
        sex = (RadioGroup) findViewById(R.id.sex_radio_group_update_profile_activity);
        submit = (CustomButton) findViewById(R.id.submit_button_update_profile_activity);

    }

    private void setViews() {
        dob.setOnClickListener(listener);
        submit.setOnClickListener(listener);
    }

    private void setDOB() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void submitEditedProfile() {

        String url = Constants.updateProfileUrl;

        firstNameValue = firstName.getText().toString();
        lastNameValue = lastName.getText().toString();
        phoneNumberValue = phoneNumber.getText().toString();
        emailValue = email.getText().toString();


        int id = sex.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(id);
        genderValue = radioButton.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        String nickname= sharedPreferences.getString("nickname","null");


        new UpdateProfileAsyncTask(this, new UpdateProfileAsyncTask.UpdateProfileCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(UpdateProfileActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }
            @Override
            public void onResult(boolean result) {
                progressDialog.dismiss();
                if (result) {
                    Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(url, nickname, firstNameValue, lastNameValue, phoneNumberValue, emailValue, dobValue, genderValue);
    }


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, dateChangedListener, year, month, day);
    }


    private DatePickerDialog.OnDateSetListener dateChangedListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dob.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
                    + selectedYear);

            dobValue = selectedYear+"/"+(selectedMonth + 1)+"/"+ selectedDay;
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dob_text_update_profile_activity:
                    showDialog(0);
                    break;
                case R.id.submit_button_update_profile_activity:
                    submitEditedProfile();
            }
        }
    };
}
