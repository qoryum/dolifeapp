package com.example.hnf.dolifeapp.Adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.hnf.dolifeapp.Activity.HomeActivity;
import com.example.hnf.dolifeapp.Activity.PostActivity;
import com.example.hnf.dolifeapp.Fragment.ImageFragment;
import com.example.hnf.dolifeapp.R;

import com.example.hnf.mylibrary.adapter.FragmentStateAdapter;

public class ViewSwapperAdapter extends FragmentStateAdapter {

    private static final int INDEX_HOME = 0;
    private static final int INDEX_SEARCH = 1;
    private static final int INDEX_TIME = 2;
    private static final int INDEX_PERSON = 3;

    public ViewSwapperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INDEX_HOME:
                return ImageFragment.newInstance(R.color.colorAbu);
            case INDEX_SEARCH:
                return ImageFragment.newInstance(R.color.colorAbu);
            case INDEX_TIME:
                return ImageFragment.newInstance(R.color.colorAbu);
            case INDEX_PERSON:
                return ImageFragment.newInstance(R.color.colorAbu);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
