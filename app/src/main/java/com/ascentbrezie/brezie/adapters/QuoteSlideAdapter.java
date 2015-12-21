package com.ascentbrezie.brezie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ascentbrezie.brezie.fragments.QuoteHolderFragment;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by ADMIN on 07-12-2015.
 */
public class QuoteSlideAdapter extends FragmentPagerAdapter {

    public QuoteSlideAdapter(FragmentManager fm) {
        super(fm);
        Log.d(Constants.LOG_TAG,Constants.QUOTE_SLIDE_ADAPTER);
    }

    @Override
    public Fragment getItem(int position) {

        return QuoteHolderFragment.newInstance(position);
    }

    @Override
    public int getCount() {

        return Constants.quotesData.size();
    }
}
