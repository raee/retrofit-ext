/*
 * Copyright (c) 2017.
 */

package com.rae.swift.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的Fragment 适配器
 * Created by ChenRui on 16/10/2 下午5:33.
 */
public class RaeFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragments;
    private final List<String> mTitles;

    public RaeFragmentAdapter(FragmentManager fm) {
        this(fm, new ArrayList<Fragment>());
    }

    public RaeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
        mTitles = new ArrayList<>();
    }

    public void add(String title, Fragment fm) {
        mTitles.add(title);
        add(fm);
    }

    public void add(Fragment fm) {
        mFragments.add(fm);
    }

    public void clear() {
        mFragments.clear();
        mTitles.clear();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.size() <= 0 ? null : mTitles.get(position % mTitles.size());
    }
}
