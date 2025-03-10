package com.example.teptep.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teptep.adapter.ViewPagerAdapter;
import com.example.teptep.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import WebKit.Bean.AllBean;
import WebKit.RetrofitFactory;
import WebKit.Service.GetGameService;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends SupportFragment {

    private FragmentHomeBinding mBinding;
    private List<Fragment> fragments;
    private ArrayList<AllBean.GameBean> recommendGames;
    private ArrayList<AllBean.GameBean> fansGames;
    private ArrayList<AllBean.GameBean> hotGames;
    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = FragmentHomeBinding.inflate(inflater);
        mRootView = mBinding.getRoot();
        onBindView(savedInstanceState,mRootView);
        return mRootView;
    }

    /**
     * 获取推荐
     */
    private void getRecommend() {
        GetGameService service = RetrofitFactory.getGetGameService(getContext());
        Call<AllBean> call = service.getRandom(5);
        call.enqueue(new Callback<AllBean>() {
            @Override
            public void onResponse(Call<AllBean> call, Response<AllBean> response) {
                if (response.isSuccessful()) {
                    AllBean result = response.body();
                    if (result != null) {
                        ArrayList<AllBean.GameBean> gameList = result.getData();
                        recommendGames = gameList;
                        initViewPager2();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllBean> call, Throwable t) {
                Toast.makeText(getContext(), "获取游戏列表失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取粉丝榜
     */
    private void getFans() {
        GetGameService service = RetrofitFactory.getGetGameService(getContext());
        Call<AllBean> call = service.getFans();
        call.enqueue(new Callback<AllBean>() {
            @Override
            public void onResponse(Call<AllBean> call, Response<AllBean> response) {
                if (response.isSuccessful()) {
                    AllBean result = response.body();
                    if (result != null) {
                        ArrayList<AllBean.GameBean> gameList = result.getData();
                        fansGames = gameList;
                        initViewPager2();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllBean> call, Throwable t) {
                Toast.makeText(getContext(), "获取游戏列表失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取热门
     */
    private void getHot() {
        GetGameService service = RetrofitFactory.getGetGameService(getContext());
        Call<AllBean> call = service.getHot();
        call.enqueue(new Callback<AllBean>() {
            @Override
            public void onResponse(Call<AllBean> call, Response<AllBean> response) {
                if (response.isSuccessful()) {
                    AllBean result = response.body();
                    if (result != null) {
                        ArrayList<AllBean.GameBean> gameList = result.getData();
                        hotGames = gameList;
                        initViewPager2();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllBean> call, Throwable t) {
                Toast.makeText(getContext(), "获取游戏列表失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 初始化ViewPager2
     */
    private void initViewPager2() {
        initViewPagerFragments();

        mBinding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mBinding.viewPager.setAdapter(new ViewPagerAdapter(HomeFragment.this,fragments));
        mBinding.viewPager.setOffscreenPageLimit(2);

        mediateTabLayoutAndViewPager2();
    }


    /**
     * 创建ViewPager2中要加载的fragment列表
     */
    private void initViewPagerFragments() {
        fragments = new ArrayList<>();
        fragments.add(ViewPager2ContentFragment.create(recommendGames, true));
        fragments.add(ViewPager2ContentFragment.create(fansGames, false));
        fragments.add(ViewPager2ContentFragment.create(hotGames, false));
    }

    /**
     * 联动ViewPager2与TabLayout
     */
    private void mediateTabLayoutAndViewPager2(){
        List<String> tabNameList = new ArrayList<>();
        tabNameList.add("推荐");
        tabNameList.add("粉丝榜");
        tabNameList.add("热门榜");
        new TabLayoutMediator(mBinding.tabRank, mBinding.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNameList.get(position));
            }
        }).attach();
    }
    protected Object setLayout() {
        return mBinding.getRoot();
    }

    protected void onBindView(Bundle savedInstanceState, View rootView) {
        getRecommend();
        getFans();
        getHot();
    }
}