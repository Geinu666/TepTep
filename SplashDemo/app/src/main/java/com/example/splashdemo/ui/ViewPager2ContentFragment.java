package com.example.splashdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.splashdemo.Game;
import com.example.splashdemo.GameActivity;
import com.example.splashdemo.GameAdapter;
import com.example.splashdemo.R;
import com.example.splashdemo.databinding.FragmentListBinding;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 用于承载游戏信息列表
 */
public class ViewPager2ContentFragment extends SupportFragment {
    private FragmentListBinding mBinding;
    private List<Game> games;

    public static ViewPager2ContentFragment create(ArrayList<Game> games){
        ViewPager2ContentFragment viewPager2ContentFragment = new ViewPager2ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("games",games);
        viewPager2ContentFragment.setArguments(bundle);
        return viewPager2ContentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    /**
     * 初始化RV
     */
    private void initRV(){
        Bundle bundle = getArguments();
        if (bundle !=null) {
            games=bundle.getParcelableArrayList("games");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.gameRecyclerview.setLayoutManager(linearLayoutManager);
        GameAdapter adapter = new GameAdapter(R.layout.game_display_cardview,games,_mActivity);
        mBinding.gameRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                TextView textView = (TextView) adapter.getViewByPosition(position, R.id.rv_item_game_id);
                String i = textView.getText().toString();
                intent.putExtra("gameId", i);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });
    }

    protected boolean setDoubleClickExit() {
        return false;
    }

    protected Object setLayout() {
        return R.layout.fragment_list;
    }

    protected void onBindView(Bundle savedInstanceState, View rootView) {
        //initRV();
    }

    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        initRV();
    }
}