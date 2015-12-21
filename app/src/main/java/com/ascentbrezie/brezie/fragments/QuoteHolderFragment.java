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
public class QuoteHolderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public QuoteHolderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QuoteHolderFragment newInstance(int sectionNumber) {
        QuoteHolderFragment fragment = new QuoteHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slider, container, false);

        Log.d(Constants.LOG_TAG,Constants.QUOTE_HOLDER_FRAGMENT);

        TextView textView = (TextView) rootView.findViewById(R.id.list_item_text);
        textView.setText(Constants.quotesData.get(getArguments().getInt(ARG_SECTION_NUMBER)).getQuote());
        return rootView;

    }
}

