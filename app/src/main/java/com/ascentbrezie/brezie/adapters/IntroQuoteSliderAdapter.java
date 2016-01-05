package com.ascentbrezie.brezie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ascentbrezie.brezie.fragments.IntroQuoteHolderFragment;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 07-12-2015.
 */
public class IntroQuoteSliderAdapter extends FragmentPagerAdapter {

    public IntroQuoteSliderAdapter(FragmentManager fm) {
        super(fm);
        Log.d(Constants.LOG_TAG,Constants.INTRO_QUOTE_SLIDER_ADAPTER);
    }

    @Override
    public Fragment getItem(int position) {

        return IntroQuoteHolderFragment.newInstance(position);
    }

    @Override
    public int getCount() {

        return Constants.quotesData.size();
    }
}
