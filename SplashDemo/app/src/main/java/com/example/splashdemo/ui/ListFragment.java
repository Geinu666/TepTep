package com.example.splashdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splashdemo.Game;
import com.example.splashdemo.GameAdapter;
import com.example.splashdemo.R;
import com.example.splashdemo.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class ListFragment extends SupportFragment {

    private FragmentListBinding mbinding;
    private List<Game> games;

    public static ListFragment create(ArrayList<Game> games){
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("games", games);
        listFragment.setArguments(bundle);
        return listFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mbinding = FragmentListBinding.inflate(inflater);
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        Bundle bundle = getArguments();
        if (bundle != null){
            games = bundle.getParcelableArrayList("games");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mbinding.gameRecyclerview.setLayoutManager(linearLayoutManager);
        GameAdapter adapter = new GameAdapter(R.layout.game_display_cardview, games, _mActivity);
        Log.i("recyclerview", "before attach");
        mbinding.gameRecyclerview.setAdapter(adapter);
        Log.i("recyclerview", "after attach");
        adapter.notifyDataSetChanged();
    }
}