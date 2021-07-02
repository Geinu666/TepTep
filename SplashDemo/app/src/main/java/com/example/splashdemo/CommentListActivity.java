package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import WebKit.DataServer;

public class CommentListActivity extends AppCompatActivity {
    private String gameId;
    private String iconUrl;
    private String gameName;
    private ImageView showCommentBack;
    private List<Comment> commentList = new ArrayList<>();
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        gameId = getIntent().getExtras().getString("gameId", null);
        gameName = getIntent().getExtras().getString("gameName", null);
        iconUrl = getIntent().getExtras().getString("iconUrl", null);

        showCommentBack = findViewById(R.id.show_comment_back);
        button = findViewById(R.id.floating);
        button.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CommentListActivity.this, CommentActivity.class);
                intent.putExtra("gameId", gameId)
                        .putExtra("gameName", gameName)
                        .putExtra("iconUrl", iconUrl);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });
        showCommentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (gameId != null) {
            getCommentById(gameId);
        }

        CommentRecyclerView();
    }

    public void getCommentById(String id) {
        // TODO: 2021/7/2 以后写请求，现在先造假
        commentList = DataServer.getComment();
    }

    private void CommentRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.comment_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }
}