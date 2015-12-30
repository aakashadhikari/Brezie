package com.ascentbrezie.brezie.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.adapters.IntroQuoteSliderAdapter;
import com.ascentbrezie.brezie.async.FetchQuotesForDayAsyncTask;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.utils.Constants;


public class LandingActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private View a, b, c, d, e, f;
    private LinearLayout tabLayout;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    TextView textView;
    static int current = 0;
    private IntroQuoteSliderAdapter introQuoteSliderAdapter;
    private ViewPager viewPager;
    private ImageView pointer;
    private Handler handler;
    private Runnable runnable;
    private Animation animation;
    private CustomTextView moodText,promptText;
    private int moods[] = {R.drawable.mood_happy,R.drawable.mood_motivated,R.drawable.mood_loved,R.drawable.mood_spiritual,R.drawable.mood_romantic,R.drawable.mood_naughty};


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Log.d(Constants.LOG_TAG, Constants.LANDING_ACTIVITY);

        findViews();
        customActionBar();
        fetchData();


    }


    public void findViews() {

        Log.d(Constants.LOG_TAG, " find Views called");
        tabLayout = (LinearLayout) findViewById(R.id.tabs_layout_landing_activity);
        pointer = (ImageView) findViewById(R.id.arrow_image_landing_activity);
        moodText = (CustomTextView) findViewById(R.id.mood_text_landing_activity);
        promptText = (CustomTextView) findViewById(R.id.prompt_text_landing_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_landing_activity);
    }

    public void customActionBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon_launcher);

    }

    public void fetchData() {

        Log.d(Constants.LOG_TAG, " fetch Data called ");

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "null");

        String url = Constants.landingUrl;
        new FetchQuotesForDayAsyncTask(getApplicationContext(), new FetchQuotesForDayAsyncTask.FetchQuotesForDayCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(LandingActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading...Please Wait");
                progressDialog.show();

            }

            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if (result) {

                    populateTabs();
                    setViews();
                    setTheSlider();

                }

            }
        }).execute(url, userId);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void populateTabs() {

        Log.d(Constants.LOG_TAG," populate tabs ");

        tabLayout.removeAllViews();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        int width = sharedPreferences.getInt("width", 0);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, width / 6);
        layoutParams.leftMargin = 5;
        layoutParams.weight = 1;

        for (int i = 0; i < 6; i++) {
            Log.d(Constants.LOG_TAG, " For loop is called ");
            View view = new View(this);
            view.setTag("mood_" + i);
            view.setOnClickListener(listener);
            view.setBackground(getResources().getDrawable(moods[i]));
            tabLayout.addView(view, layoutParams);
        }

        setLayoutForOtherViews();

    }

    public void setLayoutForOtherViews(){

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(50,50);
        layoutParams1.addRule(RelativeLayout.ABOVE, R.id.tabs_layout_landing_activity);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams1.setMargins(0, 5, 0, 5);
        pointer.setLayoutParams(layoutParams1);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.addRule(RelativeLayout.ABOVE, R.id.arrow_image_landing_activity);
        promptText.setLayoutParams(layoutParams2);

        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams3.addRule(RelativeLayout.ABOVE,R.id.prompt_text_landing_activity);
        moodText.setLayoutParams(layoutParams3);


    }

    public void setViews() {

        moodText.setText("Make me feel...");
        promptText.setText("(Click on an Emozie to proceed)");

    }


    public void setTheSlider() {

        Log.d(Constants.LOG_TAG," set The slider ");

        introQuoteSliderAdapter = new IntroQuoteSliderAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager_landing_activity);
        viewPager.setAdapter(introQuoteSliderAdapter);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (current < Constants.quotesData.size()) {
                    viewPager.setCurrentItem(current++, true);
                }
                handler.postDelayed(this, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);
        viewPager.setOnPageChangeListener(this);

    }

    public void fetchProfile(){


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname","null");


        if(nickname.equalsIgnoreCase("null")){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("route","profile");
            editor.commit();

            Intent i = new Intent(LandingActivity.this,LoginOrRegisterActivity.class);
            startActivity(i);
        }
        else{

            Intent i = new Intent(LandingActivity.this,ProfileActivity.class);
            startActivity(i);
        }

    }

    public void shareTheApp(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "null");
        String referenceCode = sharedPreferences.getString("referenceCode","null");


        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "Get many more amazing quotes like this only on Brezie. Free download now by clicking on the following link \n"
                + Constants.shareUrl + "user_id=" + userId + "&ref_code=" + referenceCode);
        Intent mailer = Intent.createChooser(share , null);
        mailer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mailer);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;
        switch (item.getItemId()) {

//            case R.id.action_profile: fetchProfile();
//                break;
//            case R.id.action_settings: i = new Intent(LandingActivity.this,SettingsActivity.class);
//                startActivity(i);
//                break;
            case R.id.action_user_guide:
                i = new Intent(LandingActivity.this, TutorialActivity.class);
                startActivity(i);
                break;
            case R.id.action_about_us:
                i = new Intent(LandingActivity.this, AboutUsActivity.class);
                startActivity(i);
                break;
            case R.id.action_share_the_app: shareTheApp();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        counter++;
        if(counter %2 == 0)
        {

            finish();

        }
        else{

            Toast.makeText(LandingActivity.this, " Press Back Again to exit", Toast.LENGTH_SHORT).show();
        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            handler.removeCallbacks(runnable);

            String tagDetails[] = v.getTag().toString().split("_");
            int position = Integer.parseInt(tagDetails[1])+1;

            Intent i = new Intent(getApplicationContext(), MoodDetailActivity.class);
            i.putExtra("moodId", String.valueOf(position));
            startActivity(i);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        current = position;
        if (position < Constants.quotesData.size() - 1) {
            pointer.setVisibility(View.GONE);
            moodText.setVisibility(View.GONE);
            promptText.setVisibility(View.GONE);
            pointer.clearAnimation();
        } else {
            animation = AnimationUtils.loadAnimation(LandingActivity.this, R.anim.blink);
            pointer.setVisibility(View.VISIBLE);
            moodText.setVisibility(View.VISIBLE);
            promptText.setVisibility(View.VISIBLE);
            pointer.startAnimation(animation);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//    View.OnClickListener listener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            Log.d(Constants.LOG_TAG," Calling the PreviousMoodDetailActivity");
//
//            Intent i = new Intent(LandingActivity.this, PreviousMoodDetailActivity.class);
//            startActivity(i);
//
////            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(LandingActivity.this, view, "abc");
////            startActivity(i, transitionActivityOptions.toBundle());
//
//
//
//        }
//    };

}
