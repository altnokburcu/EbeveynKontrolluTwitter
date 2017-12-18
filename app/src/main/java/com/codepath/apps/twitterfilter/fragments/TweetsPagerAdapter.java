package com.codepath.apps.twitterfilter.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by tanvigupta on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[] {"Home", "Mentions"};
    Context context;

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // return the total number of fragments
    @Override
    public int getCount() {
        return 2;
    }

    // return fragment to use depending on position
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeTimelineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        } else {
            return null;
        }
    }

    // return page title
    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on position
        return tabTitles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
