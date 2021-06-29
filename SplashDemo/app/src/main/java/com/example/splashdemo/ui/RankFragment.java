package com.example.splashdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splashdemo.FmPagerAdapter;
import com.example.splashdemo.Game;
import com.example.splashdemo.R;
import com.example.splashdemo.ViewPagerAdapter;
import com.example.splashdemo.databinding.FragmentRankBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import WebKit.DataServer;
import me.yokeyword.fragmentation.SupportFragment;

public class RankFragment extends SupportFragment {
    private FragmentRankBinding mBinding;
    private List<Fragment> fragments;
    private ArrayList<Game> games;
    private View mRootView;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = FragmentRankBinding.inflate(inflater);
        mRootView = mBinding.getRoot();
        onBindView(savedInstanceState,mRootView);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }


    private void loadData() {
        games = DataServer.getRankData();
    }


    /**
     * 初始化ViewPager2
     */
    private void initViewPager2() {
        initViewPagerFragments();

        mBinding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mBinding.viewPager.setAdapter(new ViewPagerAdapter(RankFragment.this,fragments));
        mBinding.viewPager.setOffscreenPageLimit(2);

        mediateTabLayoutAndViewPager2();
    }


    /**
     * 创建ViewPager2中要加载的fragment列表
     */
    private void initViewPagerFragments() {
        fragments = new ArrayList<>();
        fragments.add(ViewPager2ContentFragment.create(games));
        fragments.add(ViewPager2ContentFragment.create(games));
        fragments.add(ViewPager2ContentFragment.create(games));
    }

    /**
     * 联动ViewPager2与TabLayout
     */
    private void mediateTabLayoutAndViewPager2(){
        List<String> tabNameList = new ArrayList<>();
        tabNameList.add("热门榜");
        tabNameList.add("新品榜");
        tabNameList.add("预约榜");
        new TabLayoutMediator(mBinding.tabRank, mBinding.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NotNull TabLayout.Tab tab, int position) {
                tab.setText(tabNameList.get(position));
            }
        }).attach();
    }
    protected Object setLayout() {
        return mBinding.getRoot();
    }

    protected void onBindView(Bundle savedInstanceState, View rootView) {
        loadData();
        initViewPager2();
    }
}