package com.ascentbrezie.brezie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.custom.CustomTextView;

/**
 * Created by SAGAR on 12/30/2015.
 */
public class TutorialHolderFragment extends Fragment {

    public static TutorialHolderFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position",position);

        TutorialHolderFragment fragment = new TutorialHolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tutorial_screen_holder,container,false);

        CustomTextView tv = (CustomTextView) v.findViewById(R.id.id);
        tv.setText(String.valueOf(this.getArguments().getInt("position")));

        return v;
    }
}
