package com.example.splashdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splashdemo.FmPagerAdapter;
import com.example.splashdemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RankFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FmPagerAdapter pagerAdapter;
    private View view;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"热门", "最新", "预约"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank, container, false);
        init();
        return view;
    }

    public void init(){
        tabLayout = (TabLayout) view.findViewById(R.id.tab_rank);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        for (int i = 0; i < titles.length; i++){
            //这行填充fragment
            fragments.add(new ListFragment());
            tabLayout.addTab(tabLayout.newTab());
        }

        tabLayout.setupWithViewPager(viewPager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        for (int i = 0; i < titles.length; i++){
            tabLayout.getTabAt(i).setText(titles[i]);
        }
    }
}