package com.ascentbrezie.brezie.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ascentbrezie.brezie.fragments.MoodDetailHolderFragment;
import com.ascentbrezie.brezie.utils.Constants;

/**
 * Created by SAGAR on 12/28/2015.
 */
public class MoodDetailFragmentAdapter extends FragmentPagerAdapter {

    private String moodId;

    public MoodDetailFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public MoodDetailFragmentAdapter(FragmentManager fm,String moodId) {
        super(fm);
        this.moodId = moodId;
    }

    @Override
    public Fragment getItem(int position) {

        return MoodDetailHolderFragment.newInstance(position,moodId);

    }

    @Override
    public int getCount() {
        return Constants.moodDetailData.size();
    }
}
