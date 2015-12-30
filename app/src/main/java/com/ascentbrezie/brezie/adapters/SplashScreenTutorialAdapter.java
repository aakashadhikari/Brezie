package com.ascentbrezie.brezie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ascentbrezie.brezie.fragments.TutorialHolderFragment;

/**
 * Created by SAGAR on 12/30/2015.
 */
public class SplashScreenTutorialAdapter extends FragmentPagerAdapter {

    public SplashScreenTutorialAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialHolderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
