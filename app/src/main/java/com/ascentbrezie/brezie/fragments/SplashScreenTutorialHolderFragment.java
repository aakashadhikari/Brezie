package com.ascentbrezie.brezie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentbrezie.brezie.R;

/**
 * Created by SAGAR on 12/28/2015.
 */
public class SplashScreenTutorialHolderFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tutorial_screen_holder,container,false);
        return v;
    }
}
