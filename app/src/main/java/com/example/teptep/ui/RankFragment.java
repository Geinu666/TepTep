package com.example.teptep.ui;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.teptep.adapter.RankAdapter;
import com.example.teptep.databinding.FragmentRankBinding;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import WebKit.Bean.AllBean;
import WebKit.RetrofitFactory;
import WebKit.Service.GetGameService;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankFragment extends SupportFragment {
    private FragmentRankBinding mBinding;
    private ArrayList<AllBean.GameBean> rankedGames;
    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentRankBinding.inflate(inflater);
        mRootView = mBinding.getRoot();
        onBindView(savedInstanceState,mRootView);
        return mRootView;
    }

    /**
     * 获取排行榜
     */
    private void getRanked() {
        GetGameService service = RetrofitFactory.getGetGameService(getContext());
        Call<AllBean> call = service.getNice();
        call.enqueue(new Callback<AllBean>() {
            @Override
            public void onResponse(Call<AllBean> call, Response<AllBean> response) {
                if (response.isSuccessful()) {
                    AllBean result = response.body();
                    if (result != null) {
                        ArrayList<AllBean.GameBean> gameList = result.getData();
                        rankedGames = gameList;
                        RankRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllBean> call, Throwable t) {
                Toast.makeText(getContext(), "获取游戏列表失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void RankRecyclerView(){
        RecyclerView recyclerView = mBinding.rankCycleView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RankAdapter adapter = new RankAdapter(rankedGames);
        recyclerView.setAdapter(adapter);
    }

    protected Object setLayout() {
        return mBinding.getRoot();
    }

    protected void onBindView(Bundle savedInstanceState, View rootView) {
        getRanked();
    }
}