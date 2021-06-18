package com.example.splashdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.splashdemo.R;
import com.example.splashdemo.TempActivity;

public class HomeFragment extends Fragment {

    private Button newActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newActivity = view.findViewById(R.id.newActivity);
        view.findViewById(R.id.newActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("press", "true");
                Intent intent = new Intent(getActivity(), TempActivity.class);
                startActivity(intent);
            }
        });
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}