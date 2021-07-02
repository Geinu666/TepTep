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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import WebKit.Bean.AllCommentBean;
import WebKit.DataServer;
import WebKit.RetrofitFactory;
import WebKit.Service.CommentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListActivity extends AppCompatActivity {
    private String gameId;
    private String iconUrl;
    private String gameName;
    private ImageView showCommentBack;
    private CommentService service;
//    private List<Comment> commentList = new ArrayList<>();
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        gameId = getIntent().getExtras().getString("gameId", null);
        gameName = getIntent().getExtras().getString("gameName", null);
        iconUrl = getIntent().getExtras().getString("iconUrl", null);

        service = RetrofitFactory.getCommentService(this);

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
            Call<AllCommentBean> call = service.getAllComment(gameId);
            call.enqueue(new Callback<AllCommentBean>() {
                @Override
                public void onResponse(Call<AllCommentBean> call, Response<AllCommentBean> response) {
                    if (response.isSuccessful()) {
                        AllCommentBean result = response.body();
                        List<Comment> comments = result.getData();
                        CommentRecyclerView(comments);
                    }
                }

                @Override
                public void onFailure(Call<AllCommentBean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "获取评论失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

//        CommentRecyclerView();
    }

    private void CommentRecyclerView(List<Comment> commentList){
        RecyclerView recyclerView = findViewById(R.id.comment_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
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