package com.ascentbrezie.brezie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 07-12-2015.
 */
public class PlaceHolderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceHolderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceHolderFragment newInstance(int sectionNumber) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slider, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.list_item_text);
        textView.setText(Constants.quotesData.get(getArguments().getInt(ARG_SECTION_NUMBER)).getQuotes());
        return rootView;
    }
}

