package com.study.soulhouse.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    String[] tabs;
    public MyPagerAdapter(@NonNull FragmentManager fm, List<Fragment> list, String[] tabs) {
        super(fm);
        this.list=list;
        this.tabs=tabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return tabs[position];
    }
}
