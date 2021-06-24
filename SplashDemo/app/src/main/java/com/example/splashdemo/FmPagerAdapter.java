package com.example.splashdemo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
//用于管理tablayout中的fragment
public class FmPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public FmPagerAdapter(List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount(){
        return fragmentList != null && !fragmentList.isEmpty() ? fragmentList.size() : 0;
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
}
