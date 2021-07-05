package com.example.splashdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.splashdemo.Game;
import com.example.splashdemo.GameActivity;
import com.example.splashdemo.GameAdapter;
import com.example.splashdemo.R;
import com.example.splashdemo.databinding.FragmentListBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import WebKit.Bean.AllBean;
import WebKit.RetrofitFactory;
import WebKit.Service.GetGameService;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用于承载游戏信息列表
 */
public class ViewPager2ContentFragment extends SupportFragment {
    private FragmentListBinding mBinding;
    private List<AllBean.GameBean> games;

    public void addItem(int position, AllBean.GameBean game) {
        games.add(position, game);
    }

    public static ViewPager2ContentFragment create(ArrayList<AllBean.GameBean> games, Boolean canLoad){
        ViewPager2ContentFragment viewPager2ContentFragment = new ViewPager2ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("games",games);
        bundle.putBoolean("canLoad", canLoad);
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
        Boolean canLoad = false;
        Bundle bundle = getArguments();
        if (bundle !=null) {
            games=bundle.getParcelableArrayList("games");
            canLoad = bundle.getBoolean("canLoad");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.gameRecyclerview.setLayoutManager(linearLayoutManager);
        GameAdapter adapter = new GameAdapter(R.layout.game_display_cardview,games,_mActivity);
        mBinding.gameRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //游戏卡点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                TextView textView = (TextView) adapter.getViewByPosition(position, R.id.rv_item_game_id);
                String i = textView.getText().toString();
                intent.putExtra("gameId", i);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });
        //触底刷新判定
        if (canLoad) {
            mBinding.gameRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1) && newState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                        Log.i("test", "到底部！");
                        GetGameService service = RetrofitFactory.getGetGameService(getContext());
                        Call<AllBean> call = service.getRandom(5);
                        call.enqueue(new Callback<AllBean>() {
                            @Override
                            public void onResponse(Call<AllBean> call, Response<AllBean> response) {
                                if (response.isSuccessful()) {
                                    AllBean result = response.body();
                                    if (result != null) {
                                        Log.i("test", "我在加数据！");
                                        Log.i("test", String.valueOf(games.size()));
                                        ArrayList<AllBean.GameBean> gameList = result.getData();
                                        for (int i = 0; i < 5; i++) {
                                            addItem(games.size(), gameList.get(i));
                                            Log.i("test", String.valueOf(games.size()));
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<AllBean> call, Throwable t) {
                                Toast.makeText(getContext(), "获取游戏列表失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
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