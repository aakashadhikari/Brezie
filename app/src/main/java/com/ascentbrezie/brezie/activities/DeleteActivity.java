package com.ascentbrezie.brezie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.utils.Constants;

import java.util.Random;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class DeleteActivity extends Activity {

    View v;
    int images[] = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.j};
    int min = 0;
    int max = 8;
    ImageView like,share;
    Animation animationLike,animationShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        Log.d(Constants.LOG_TAG,Constants.MOOD_DETAIL_ACTIVITY);

        findViews();
        setViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                initializeAnimation();
            }
        },100);


    }

    public void findViews(){

        v = findViewById(R.id.display_quote_image_temp_activity);
        like = (ImageView) findViewById(R.id.like_included);
        share = (ImageView) findViewById(R.id.share_included);

    }

    public void setViews(){


        Random r = new Random();
        int quoteNumber = r.nextInt(max - min) + min;
        v.setBackgroundResource(images[quoteNumber]);


    }

    /**
     * This is used to initialize
     * all the animations that are used in this
     * activity
     * **/
    public void initializeAnimation(){

        like.setVisibility(View.VISIBLE);
        animationLike = AnimationUtils.loadAnimation(this,R.anim.views_zoom_out);
        like.startAnimation(animationLike);

        share.setVisibility(View.VISIBLE);
        animationShare = AnimationUtils.loadAnimation(this,R.anim.views_zoom_out);
        share.startAnimation(animationShare);


    }

}
