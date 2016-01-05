package com.ascentbrezie.brezie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ascentbrezie.brezie.fragments.TutorialHolderFragment;

/**
 * Created by SAGAR on 12/30/2015.
 */
public class TutorialAdapter extends FragmentPagerAdapter {

    private int viewPagerHeight;

    public TutorialAdapter(FragmentManager fm) {
        super(fm);
    }

    public TutorialAdapter(FragmentManager fm, int viewPagerHeight) {
        super(fm);
        this.viewPagerHeight = viewPagerHeight;
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialHolderFragment.newInstance(position,viewPagerHeight);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
