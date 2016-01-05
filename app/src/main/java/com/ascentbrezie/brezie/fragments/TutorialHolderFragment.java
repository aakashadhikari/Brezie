package com.ascentbrezie.brezie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 12/30/2015.
 */
public class TutorialHolderFragment extends Fragment {

    private CustomTextView tutorialExplanation;
    private ImageView tutorialImage;
    private int position;

    private LinearLayout.LayoutParams layoutParams;

    public static TutorialHolderFragment newInstance(int position,int viewPagerHeight) {

        Bundle args = new Bundle();
        args.putInt("position",position);
        args.putInt("viewPagerHeight",viewPagerHeight);

        TutorialHolderFragment fragment = new TutorialHolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tutorial_screen_holder,container,false);

        Log.d(Constants.LOG_TAG,Constants.TUTORIAL_HOLDER_FRAGMENT);

        position = this.getArguments().getInt("position");

        findViews(v);
        defineLayouts(v);
        setViews();


        return v;
    }

    public void findViews(View v){

        tutorialImage = (ImageView) v.findViewById(R.id.tutorial_image_tutorial_holder_fragment);
        tutorialExplanation = (CustomTextView) v.findViewById(R.id.tutorial_explanation_text_tutorial_holder_fragment);
    }

    public void defineLayouts(View v){

        int temp = this.getArguments().getInt("viewPagerHeight");

        int height = (temp*90)/100;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,height);
        tutorialImage.setLayoutParams(layoutParams);

    }

    public void setViews(){

        tutorialImage.setImageResource(getResources().obtainTypedArray(R.array.tutorial_images).getResourceId(position,-1));
        tutorialExplanation.setText((getResources().getStringArray(R.array.tutorial_text))[position]);

    }

}
